package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Parser {
	public static ArrayList<Production> G;
	
	public static ArrayList<String> nonterminalSet;
	private static ArrayList<String> terminalSet;
	private static ArrayList<String> symbolSet;
	private static Map<String,Set<String> > FIRST;
	private static Map<String,Set<String> > FOLLOW;
	
	private static ArrayList<Itemset> C;
	
	private static ArrayList<Status> analyselist;
	
	public static boolean isNonterminal(String s) {
		return nonterminalSet.contains(s);
	}
	private static boolean isTerminal(String s) {
		return terminalSet.contains(s);
	}
	private static void init() throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader("LR.txt"));
		//初始化非终结符号集
		String s=reader.readLine();
		String[] tmp=s.split(" ");
		nonterminalSet=new ArrayList<String>(Arrays.asList(tmp));
		//初始化终结符号集
		s=reader.readLine();
		tmp=s.split(" ");
		terminalSet=new ArrayList<String>(Arrays.asList(tmp));
		//初始化文法符号集
		symbolSet=new ArrayList<String>();
		symbolSet.addAll(nonterminalSet);
		symbolSet.addAll(terminalSet);
		//初始化产生式
		G=new ArrayList<Production>();
		for(int i=1;i<=22;i++) {
			s=reader.readLine();
			tmp=s.split(" ");
			Production p=new Production();
			p.left=tmp[0];
			for(int j=1;j<tmp.length;j++) {
				if(tmp[j]=="ε") {
					continue;
				}
				p.right.add(tmp[j]);
			}
			G.add(p);
		}
		//初始化FIRST集
		FIRST=new HashMap<String,Set<String> >();
		for(int i=1;i<=9;i++) {
			s=reader.readLine();
			tmp=s.split(" ");
			String left=tmp[0];
			Set<String>right=new HashSet<String>();
			for(int j=1;j<tmp.length;j++) {
				right.add(tmp[j]);
			}
			FIRST.put(left, right);
		}
		//初始化FOLLOW集
		FOLLOW=new HashMap<String,Set<String> >();
		for(int i=1;i<=9;i++) {
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
		//初始化GOTO自动机项集
		C=new ArrayList<Itemset>();
		analyselist=new ArrayList<Status>();
	}
	
	
	private static Itemset GOTO(Itemset I, String X) {
		Itemset tmp=new Itemset();
		for(Item i:I) {
			String next=i.getSign();
			if(X.equals(next)) {
				Item t=new Item();
				t.point=i.point+1;
				t.production=i.production;
				tmp.add(t);
			}
		}
		if(tmp.size()==0) {
			return null;
		}
		return CLOSURE(tmp);
	}
	public static Itemset CLOSURE(Itemset Y) {
		Itemset X=new Itemset();
		for(Item i:Y) {
			Item tmp=new Item();
			tmp.point=i.point;
			tmp.production=i.production;
			X.add(tmp);
		}
		
		boolean flag=false;
		do {
			flag=false;
			for(Item i:X) {
				String B=i.getSign();
				if(Parser.isNonterminal(B)) {
					for(Production j:Parser.G) {
						if(j.left.equals(B)) {
							Item tmp=new Item(Parser.G.indexOf(j));
							if(!X.contains(tmp)) {
								X.add(tmp);
								flag=true;
							}
						}
					}
				}
				if(flag) {
					break;
				}
			}
		}while(flag);
		return X;
	}
	
	private static void getLR0() {
		Itemset I0=new Itemset();
		I0.add(new Item(0));
		C.add(CLOSURE(I0));
		for(int i=0;i<C.size();i++) {
			Itemset I=C.get(i);
			for(String X:symbolSet) {
				Itemset tmp=GOTO(I,X);
				if(tmp!=null&&!C.contains(tmp)) {
					C.add(tmp);
				}
			}
		}
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
				String A=G.get(j.production).left;
				String a=G.get(j.production).get(j.point);
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
				if(GOTO(I,T)!=null) {
					status.GOTO.put(T, C.indexOf(GOTO(I,T)));
				}
			}
			analyselist.add(status);
		}
	}
	
	public static void compileLR() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//初始化值
		getLR0();
		makeGOTO();
	}
	public static void LR(String[] w) {
		int tip=0;
		String a=w[tip++];
		StatuStack st=new StatuStack();
		st.push(0);
		while(true) {
			int s=st.top();
			Status status=analyselist.get(s);
			if(status.ACTION.get(a).op==1) {
				st.push(status.ACTION.get(a).data);
				a=(tip!=w.length)?w[tip++]:"$";
			}
			else if(status.ACTION.get(a).op==2){
				int p=status.ACTION.get(a).data;
				int len=G.get(p).length();
				st.pop(len);
				int t=st.top();
				st.push(analyselist.get(t).GOTO.get(G.get(p).left));
				System.out.println(G.get(p).toString());
			}
			else if(status.ACTION.get(a).op==0){
				System.out.println("Accept!");
				break;
			}
		}
	}
}
