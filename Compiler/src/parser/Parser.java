package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Parser {
	public static ArrayList<Production> G;
	
	private static String[] nonterminalList= {"P1","P","D","L","S","E","C","T","F"};
	private static String[] terminalList= {"id",";","int","float",
											"=","if","(",")","while",">","<","==","+","-","*","/","int10"};
	public static ArrayList<String> nonterminalSet;
	private static ArrayList<String> terminalSet;
	private static ArrayList<String> symbolSet;
	
	private static ArrayList<Itemset> C;
	
	private static ArrayList<Status> analyselist;
	
	public static boolean isNonterminal(String s) {
		return nonterminalSet.contains(s);
	}
	private static boolean isTerminal(String s) {
		return terminalSet.contains(s);
	}
	private static void init() {
		//初始化产生式
		G=new ArrayList<Production>();
		setArray();
		//初始化符号集
		nonterminalSet=new ArrayList<String>(Arrays.asList(nonterminalList));
		terminalSet=new ArrayList<String>(Arrays.asList(terminalList));
		symbolSet=new ArrayList<String>();
		symbolSet.addAll(nonterminalSet);
		symbolSet.addAll(terminalSet);
		//初始化GOTO自动机项集
		C=new ArrayList<Itemset>();
		analyselist=new ArrayList<Status>();
	}
	private static void setArray() {
		Production tmp;
		
		//P1->P
		tmp=new Production();
		tmp.left="P1";
		tmp.add("P");
		G.add(tmp);
		
		//P → D S
		tmp=new Production();
		tmp.left="P";
		tmp.add("D");
		tmp.add("S");
		G.add(tmp);
		
		//D → L id ; D | ε
		tmp=new Production();
		tmp.left="D";
		tmp.add("L");
		tmp.add("id");
		tmp.add(";");
		tmp.add("D");
		G.add(tmp);
		
		tmp=new Production();
		tmp.left="D";
		G.add(tmp);
		
		//L → int | float
		tmp=new Production();
		tmp.left="L";
		tmp.add("int");
		G.add(tmp);
		
		tmp=new Production();
		tmp.left="L";
		tmp.add("float");
		G.add(tmp);
		
		//S → id = E ;
		tmp=new Production();
		tmp.left="S";
		tmp.add("id");
		tmp.add("=");
		tmp.add("E");
		tmp.add(";");
		G.add(tmp);
		
		//S → if ( C ) S1
		tmp=new Production();
		tmp.left="S";
		tmp.add("if");
		tmp.add("(");
		tmp.add("C");
		tmp.add(")");
		tmp.add("S");
		G.add(tmp);
		
		//S → while ( C ) S1
		tmp=new Production();
		tmp.left="S";
		tmp.add("while");
		tmp.add("(");
		tmp.add("C");
		tmp.add(")");
		tmp.add("S");
		G.add(tmp);
		
		//S → S ; S
		tmp=new Production();
		tmp.left="S";
		tmp.add("S");
		tmp.add(";");
		tmp.add("S");
		G.add(tmp);
		
		//C → E1 > E2
		tmp=new Production();
		tmp.left="C";
		tmp.add("E");
		tmp.add(">");
		tmp.add("E");
		G.add(tmp);
		
		//C → E1 < E2
		tmp=new Production();
		tmp.left="C";
		tmp.add("E");
		tmp.add("<");
		tmp.add("E");
		G.add(tmp);
		
		//C → E1 == E2
		tmp=new Production();
		tmp.left="C";
		tmp.add("E");
		tmp.add("==");
		tmp.add("E");
		G.add(tmp);
		
		//E → E1 + T
		tmp=new Production();
		tmp.left="E";
		tmp.add("E");
		tmp.add("+");
		tmp.add("T");
		G.add(tmp);
		
		//E → E1 – T
		tmp=new Production();
		tmp.left="E";
		tmp.add("E");
		tmp.add("-");
		tmp.add("T");
		G.add(tmp);
		
		//E → T
		tmp=new Production();
		tmp.left="E";
		tmp.add("T");
		G.add(tmp);
		
		//T → F
		tmp=new Production();
		tmp.left="T";
		tmp.add("F");
		G.add(tmp);
		
		//T → T1 * F
		tmp=new Production();
		tmp.left="T";
		tmp.add("T");
		tmp.add("*");
		tmp.add("F");
		G.add(tmp);
		
		//T → T1 / F
		tmp=new Production();
		tmp.left="T";
		tmp.add("T");
		tmp.add("/");
		tmp.add("F");
		G.add(tmp);
		
		//F → ( E )
		tmp=new Production();
		tmp.left="F";
		tmp.add("(");
		tmp.add("E");
		tmp.add(")");
		G.add(tmp);
		
		//F → id
		tmp=new Production();
		tmp.left="F";
		tmp.add("id");
		G.add(tmp);
		
		//F → int10
		tmp=new Production();
		tmp.left="F";
		tmp.add("int10");
		G.add(tmp);
		
		return;
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
				String A=G.get(j.production).left;
				String a=G.get(j.production).get(j.point);
				if(a!=null&&isTerminal(a)) {
					Itemset tmp=GOTO(I,a);
					if(C.contains(tmp)) {
						status.ACTION.put(a, new OPMode(1,C.indexOf(tmp)));
					}
				}
				else if(a==null) {
					for(String a:FOLLOW(A)) {
						
					}
				}
			}
			
			
			
			analyselist.add(status);
		}
	}
	
	public static void compileLR() {
		init();//初始化值
		getLR0();
		makeGOTO();
		
	}
}
