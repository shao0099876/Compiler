package parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Parser {
	private static Set<Production> G;
	private static Set<Production> G1;
	private static String[] nonterminalList= {"P1","P","D","L","S","E","C","S1","S2","E1","E2","T","F","T1"};
	private static String[] terminalList= {"id",";","null","int","float",
											"=","if","(",")","else","while",">","<","==","+","-","*","/","int10"};
	private static Set<String> nonterminalSet;
	private static Set<String> terminalSet;
	private static Set<String> symbolSet;
	private static boolean isNonterminal(String s) {
		return nonterminalSet.contains(s);
	}
	private static boolean isTerminal(String s) {
		return terminalSet.contains(s);
	}
	private static void init() {
		G=new HashSet<Production>();
		setArray();
		
		G1=new HashSet<Production>();
		G1.addAll(G);
		Production tmp=new Production();
		tmp.left="P1";
		tmp.add("P");
		G1.add(tmp);
		
		nonterminalSet=new HashSet<String>(Arrays.asList(nonterminalList));
		terminalSet=new HashSet<String>(Arrays.asList(terminalList));
		
		symbolSet=new HashSet<String>();
		symbolSet.addAll(nonterminalSet);
		symbolSet.addAll(terminalSet);
	}
	private static void setArray() {
		Production tmp;
		
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
		tmp.add("null");
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
		tmp.add("S1");
		G.add(tmp);
		
		//S → if ( C ) S1 else S2
		tmp=new Production();
		tmp.left="S";
		tmp.add("if");
		tmp.add("(");
		tmp.add("C");
		tmp.add(")");
		tmp.add("S1");
		tmp.add("else");
		tmp.add("S2");
		G.add(tmp);
		
		//S → while ( C ) S1
		tmp=new Production();
		tmp.left="S";
		tmp.add("while");
		tmp.add("(");
		tmp.add("C");
		tmp.add(")");
		tmp.add("S1");
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
		tmp.add("E1");
		tmp.add(">");
		tmp.add("E2");
		G.add(tmp);
		
		//C → E1 < E2
		tmp=new Production();
		tmp.left="C";
		tmp.add("E1");
		tmp.add("<");
		tmp.add("E2");
		G.add(tmp);
		
		//C → E1 == E2
		tmp=new Production();
		tmp.left="C";
		tmp.add("E1");
		tmp.add("==");
		tmp.add("E2");
		G.add(tmp);
		
		//E → E1 + T
		tmp=new Production();
		tmp.left="E";
		tmp.add("E1");
		tmp.add("+");
		tmp.add("T");
		G.add(tmp);
		
		//E → E1 – T
		tmp=new Production();
		tmp.left="E";
		tmp.add("E1");
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
		tmp.add("T1");
		tmp.add("*");
		tmp.add("F");
		G.add(tmp);
		
		//T → T1 / F
		tmp=new Production();
		tmp.left="T";
		tmp.add("T1");
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
	private static Set<Items> CLOSURE(Set<Items> I) {
		Set<Items> J=new HashSet<Items>();
		Set<Items> res=new HashSet<Items>();
		J.addAll(I);
		boolean flag=true;
		while(flag){
			flag=false;
			for(Items item:J) {
				J.remove(item);
				res.add(item);
				String s=item.getSign();
				if (isNonterminal(s)) {
					for(Production production:G) {
						
						if(!production.left.equals(s)) {
							continue;
						}
						Items tmp=new Items(production);
						if(!J.contains(tmp)) {
							J.add(tmp);
							flag=true;
							break;
						}
					}
				}
				if(flag) {
					break;
				}
			}
		}
		return res;
	}
	private static Set<Items> GOTO(Set<Items> I,String X){
		Set<Items> res=new HashSet<Items>();
		for(Items item:I) {
			String s=item.getSign();
			if(s.equals(X)) {
				Items new_item=new Items();
				new_item.point=item.point+1;
				new_item.production=item.production;
				if(new_item.point<=item.production.length()) {
					res.add(new_item);
				}
			}
		}
		res=CLOSURE(res);
		return res;
	}
	private static void items() {
		Production tmp=new Production();
		tmp.left="P1";
		tmp.add("P");
		Set<Items> tmp_set=new HashSet<Items>();
		tmp_set.add(new Items(tmp));
		
		Set<Set<Items> >C=new HashSet<Set<Items> >();
		C.add(CLOSURE(tmp_set));
		
		boolean flag=true;
		while(flag) {
			flag=false;
			for(Set<Items> I:C) {
				for(String X:symbolSet) {
					Set<Items> goto_tmp=GOTO(I,X);
					if(!goto_tmp.isEmpty()&&!C.contains(goto_tmp)) {
						C.add(goto_tmp);
						flag=true;
					}
				}
			}
		}
		int a=1+1;
	}
	public static void compileLR() {
		init();//初始化值
		items();//求规范LR(0)项集族
	}
}
