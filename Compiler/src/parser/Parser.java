package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lexer.LexicalAnalyze;
import lexer.token.Token;
import midcode.Midcode;
import ui.UserInterface;

public class Parser {
	public static GrammerSet G;
	
	private static ArrayList<String> nonterminalSet;
	private static ArrayList<String> terminalSet;
	private static ArrayList<String> symbolSet;
	
	private static Map<String,Set<String> > FIRST;
	private static Map<String,Set<String> > FOLLOW;
	
	private static ArrayList<Itemset> C;
	
	private static ArrayList<Status> analyselist;
	
	public static int offset;
	
	public static boolean isNonterminal(String s) {
		return nonterminalSet.contains(s);
	}
	public static boolean isTerminal(String s) {
		return terminalSet.contains(s);
	}
	public static Production getProduction(int x) {
		return G.get(x);
	}
	private static void init() throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader("LR.txt"));
		String s=reader.readLine();
		String[] tmp=s.split(" ");
		nonterminalSet=new ArrayList<String>(Arrays.asList(tmp));
		s=reader.readLine();
		tmp=s.split(" ");
		terminalSet=new ArrayList<String>(Arrays.asList(tmp));
		symbolSet=new ArrayList<String>();
		symbolSet.addAll(nonterminalSet);
		symbolSet.addAll(terminalSet);
		G=new GrammerSet();
		for(int i=0;i<26;i++) {
			s=reader.readLine();
			tmp=s.split(" ");
			Production p=new Production();
			p.left=tmp[0];
			for(int j=1;j<tmp.length;j++) {
				if(!tmp[j].equals("¦Å")) {
					p.right.add(tmp[j]);
				}
			}
			G.add(p);
		}
		FIRST=new HashMap<String,Set<String> >();
		for(int i=1;i<=12;i++) {
			s=reader.readLine();
			tmp=s.split(" ");
			String left=tmp[0];
			Set<String>right=new HashSet<String>();
			for(int j=1;j<tmp.length;j++) {
				right.add(tmp[j]);
			}
			FIRST.put(left, right);
		}
		FOLLOW=new HashMap<String,Set<String> >();
		for(int i=1;i<=12;i++) {
			s=reader.readLine();
			tmp=s.split(" ");
			String left=tmp[0];
			Set<String>right=new HashSet<String>();
			for(int j=1;j<tmp.length;j++) {
				right.add(tmp[j]);
			}
			FOLLOW.put(left,right);
		}
		reader.close();
		C=new ArrayList<Itemset>();
		analyselist=new ArrayList<Status>();
	}
	
	
	private static Itemset GOTO(Itemset I, String X) {
		Itemset tmp=new Itemset();
		for(Item i:I) {
			String next=i.getSign();
			if(X.equals(next)) {
				Item t=new Item(i.production,i.point+1);
				tmp.add(t);
			}
		}
		return CLOSURE(tmp);
	}
	public static Itemset CLOSURE(Itemset Y) {
		Itemset X=new Itemset();
		for(Item i:Y) {
			Item tmp=new Item(i.production,i.point);
			X.add(tmp);
		}
		int tip=0;
		while(tip<X.size()) {
			Item i=X.get(tip);
			String B=i.getSign();
			if(isNonterminal(B)) {
				for(Production j:G) {
					if(j.isLeft(B)) {
						Item tmp=new Item(G.indexOf(j));
						if(!X.contains(tmp)) {
							X.add(tmp);
						}
					}
				}
			}
			tip+=1;
		}
		return X;
	}
	
	private static void getLR0() {
		Itemset I0=new Itemset();
		I0.add(new Item(0));
		C.add(CLOSURE(I0));
		int i=0;
		while(i<C.size()) {
			Itemset I=C.get(i);
			for(String X:symbolSet) {
				Itemset tmp=GOTO(I,X);
				if(!tmp.isEmpty()&&!C.contains(tmp)) {
					C.add(tmp);
				}
			}
			i+=1;
		}
		return;
	}
	
	private static void makeGOTO() {
		for(int i=0;i<C.size();i++) {
			Itemset I=C.get(i);
			Status status=new Status();
			
			for(Item j:I) {
				if(j.production==0&&j.point==1) {
					status.ACTION.put("$", new OPMode(0));
					continue;
				}
				String A=G.getLeft(j.production);
				String a=j.getSign();
				if(a!=null&&isTerminal(a)) {
					Itemset tmp=GOTO(I,a);
					if(C.contains(tmp)) {
						status.ACTION.put(a, new OPMode(1,C.indexOf(tmp)));
					}
				}
				else if(a==null) {
					for(String t:FOLLOW.get(A)) {
						status.ACTION.put(t, new OPMode(2,j.production));
					}
				}
			}
			
			for(String T:nonterminalSet) {
				if(!GOTO(I,T).isEmpty()) {
					status.GOTO.put(T, C.indexOf(GOTO(I,T)));
				}
			}
			analyselist.add(status);
		}
	}
	
	public static void compileLR() throws IOException {
		init();
		getLR0();
		makeGOTO();
	}
	private static String tokenToString(Token token) {
		String a="";
		if(token.getType()=="KeywordToken") {
			a=token.getName();
		}
		if(token.getType()=="IdToken") {
			a="id";
		}
		if(token.getType()=="NumToken") {
			a="int10";
		}
		if(token.getType()=="OpToken") {
			a=token.getName();
		}
		return a;
	}
	public static void LR(ArrayList<Token> w) {
		int tip=0;
		Token token=w.get(tip++);
		String a=tokenToString(token);
		StatuStack st=new StatuStack();
		st.push(0,null);
		while(true) {
			int s=st.top().statusNumber;
			Status status=analyselist.get(s);
			OPMode record=status.ACTION.get(a);
			if(record==null) {
				System.out.println("ERROR");
			}
			else if(record.op==1) {
				st.push(record.data,w.get(tip-1));
				a=(tip!=w.size())?tokenToString(w.get(tip++)):"$";
			}
			else if(record.op==2){
				int p=record.data;
				int len=G.get(p).length();
				Midcode.action(p,st);
				st.pop(len);
				int t=st.top().statusNumber;
				st.push(analyselist.get(t).GOTO.get(G.get(p).left),w.get(tip-1));
				System.out.println(G.get(p).toString());
			}
			else if(record.op==0){
				System.out.println("Accept!");
				break;
			}
		}
	}
	public static void GrammerAnalyse() {
		String code=UserInterface.getCode();
		String[] codeArray=code.split("\n");
		for(String i:codeArray) {
			LR(LexicalAnalyze.call(i));
		}
	}
}
