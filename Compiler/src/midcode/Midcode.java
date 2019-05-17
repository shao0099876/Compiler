package midcode;

import java.util.ArrayList;

import lexer.LexicalAnalyze;
import lexer.SymbolList;
import lexer.token.IdToken;
import lexer.token.TempToken;
import lexer.token.Token;
import parser.Parser;
import parser.Production;
import parser.StatuStack;

public class Midcode {
	public static ArrayList<String> code=new ArrayList<String>();
	public static StatuStackRecord action(int p_t, StatuStack st) {
		StatuStackRecord res=new StatuStackRecord();
		switch (p_t) {
		case 3:{//M1 ¦Å {offset=0;}
			//offset=0;
			Parser.offset=0;
			break;
		}
		case 5:{//M2 ¦Å {top.put(id.lexeme,L.type,offset);offset=offset+L.width}
			StatuStackRecord L=st.get(st.tip-3);
			StatuStackRecord id=st.get(st.tip-2);
			//top.put(id.lexeme,L.type,offset);
			IdToken t=(IdToken) LexicalAnalyze.symbolList.get(id.token.getName());
			t.type=(String) L.get("type");
			t.offset=Parser.offset;
			//offset=offset+L.width
			Parser.offset+=(int)L.get("width");
			break;
		}
		case 7:{//L int {L.type="integer",L.width=4}
			//L.type="integer"
			res.set("type", "integer");
			//L.width=4
			res.set("width", 4);
			break;
		}
		case 8:{//L float {L.type="float",L.width=8}
			//L.type="float"
			res.set("type", "float");
			//L.width=8
			res.set("width", 8);
			break;
		}
		case 10:{//S id = E ; {gen(top.get(id.lexeme)'='E.addr);S.nextlist=null}
			StatuStackRecord id=st.get(st.tip-4);
			StatuStackRecord E=st.get(st.tip-2);
			//gen(top.get(id.lexeme)'='E.addr)
			StringBuilder sb=new StringBuilder();
			sb.append(id.token.getName());
			sb.append(" ");
			sb.append("=");
			sb.append(" ");
			sb.append(E.get("addr"));
			code.add(sb.toString());
			//S.nextlist=null
			res.set("nextlist", new ArrayList<Integer>());
			break;
		}
		case 11:{//S if ( C ) MM S {backpatch(C.truelist,MM.instr);S.nextlist=merge(C.falselist,S(1).nextlist);backpatch(S.nextlist,nextinstr)}
			StatuStackRecord C=st.get(st.tip-4);
			StatuStackRecord MM=st.get(st.tip-2);
			StatuStackRecord S1=st.top();
			//backpatch(C.truelist,MM.instr);
			backpatch((ArrayList<Integer>)C.get("truelist"),(int)MM.get("instr"));
			//S.nextlist=merge(C.falselist,S(1).nextlist)
			res.set("nextlist",merge((ArrayList<Integer>)C.get("falselist"),(ArrayList<Integer>)S1.get("nextlist")));
			//backpatch(S.nextlist,nextinstr)
			backpatch((ArrayList<Integer>)res.get("nextlist"),code.size());
			break;
		}
		case 12:{//MM ¦Å {MM.instr=nextinstr}
			//MM.instr=nextinstr
			res.set("instr", code.size());
			break;
		}
		case 13:{//S while MM ( C ) MM S {backpatch(S(1).nextlist,MM(1).instr);backpatch(C.truelist,MM(2).instr);S.nextlist=C.falselist;gen("goto " MM(1).instr)}
			StatuStackRecord MM1=st.get(st.tip-6);
			StatuStackRecord C=st.get(st.tip-4);
			StatuStackRecord MM2=st.get(st.tip-2);
			StatuStackRecord S1=st.top();
			//backpatch(S(1).nextlist,MM(1).instr);
			backpatch((ArrayList<Integer>)S1.get("nextlist"),(int)MM1.get("instr"));
			//backpatch(C.truelist,MM(2).instr);
			backpatch((ArrayList<Integer>)C.get("truelist"),(int)MM2.get("instr"));
			//S.nextlist=C.falselist;
			res.set("nextlist", C.get("falselist"));
			//gen("goto " MM(1).instr)
			StringBuilder sb=new StringBuilder();
			sb.append("goto ");
			sb.append(MM1.get("instr"));
			code.add(sb.toString());
			break;
		}
		case 14:{//C E > E {C.truelist=makelist(nextinstr);C.falselist=makelist(nextinstr+1);gen("if" E(1).addr ">" E2.addr "goto");gen("goto")}
			StatuStackRecord E1=st.get(st.tip-3);
			StatuStackRecord E2=st.top();
			//C.truelist=makelist(nextinstr);
			res.set("truelist", makelist(code.size()));
			//C.falselist=makelist(nextinstr+1);
			res.set("falselist", makelist(code.size()+1));
			//gen("if" E(1).addr ">" E2.addr "goto");
			StringBuilder sb=new StringBuilder();
			sb.append("if ");
			sb.append(E1.get("addr"));
			sb.append(" > ");
			sb.append(E2.get("addr"));
			sb.append(" goto");
			code.add(sb.toString());
			//gen("goto")
			sb=new StringBuilder();
			sb.append("goto");
			code.add(sb.toString());
			break;
		}
		case 15:{//C E < E {C.truelist=makelist(nextinstr);C.falselist=makelist(nextinstr+1);gen("if" E(1).addr "<" E2.addr "goto");gen("goto")}
			StatuStackRecord E1=st.get(st.tip-3);
			StatuStackRecord E2=st.top();
			//C.truelist=makelist(nextinstr);
			res.set("truelist", makelist(code.size()));
			//C.falselist=makelist(nextinstr+1);
			res.set("falselist", makelist(code.size()+1));
			//gen("if" E(1).addr "<" E2.addr "goto");
			StringBuilder sb=new StringBuilder();
			sb.append("if ");
			sb.append(E1.get("addr"));
			sb.append(" < ");
			sb.append(E2.get("addr"));
			sb.append(" goto");
			code.add(sb.toString());
			//gen("goto")
			sb=new StringBuilder();
			sb.append("goto");
			code.add(sb.toString());
			break;
		}
		case 16:{//C E == E {C.truelist=makelist(nextinstr);C.falselist=makelist(nextinstr+1);gen("if" E(1).addr "==" E2.addr "goto");gen("goto")}
			StatuStackRecord E1=st.get(st.tip-3);
			StatuStackRecord E2=st.top();
			//C.truelist=makelist(nextinstr);
			res.set("truelist", makelist(code.size()));
			//C.falselist=makelist(nextinstr+1);
			res.set("falselist", makelist(code.size()+1));
			//gen("if" E(1).addr "==" E2.addr "goto");
			StringBuilder sb=new StringBuilder();
			sb.append("if ");
			sb.append(E1.get("addr"));
			sb.append(" == ");
			sb.append(E2.get("addr"));
			sb.append(" goto");
			code.add(sb.toString());
			//gen("goto")
			sb=new StringBuilder();
			sb.append("goto");
			code.add(sb.toString());
			break;
		}
		case 17:{//E E + T {E.addr=new Temp();gen(E.addr = E(1).addr + T.addr);}
			StatuStackRecord E=st.get(st.tip-3);
			StatuStackRecord T=st.top();
			//E.addr=new Temp();
			res.set("addr", new_Temp());
			//gen(E.addr = E(1).addr + T.addr);
			StringBuilder sb=new StringBuilder();
			sb.append(res.get("addr"));
			sb.append(" = ");
			sb.append(E.get("addr"));
			sb.append(" + ");
			sb.append(T.get("addr"));
			code.add(sb.toString());
			break;
		}
		case 18:{//E E - T {E.addr=new Temp();gen(E.addr = E(1).addr - T.addr);}
			StatuStackRecord E=st.get(st.tip-3);
			StatuStackRecord T=st.top();
			//E.addr=new Temp();
			res.set("addr", new_Temp());
			//gen(E.addr = E(1).addr - T.addr);
			StringBuilder sb=new StringBuilder();
			sb.append(res.get("addr"));
			sb.append(" = ");
			sb.append(E.get("addr"));
			sb.append(" - ");
			sb.append(T.get("addr"));
			code.add(sb.toString());
			break;
		}
		case 19:{//E T {E.addr=T.addr}
			StatuStackRecord T=st.top();
			res.set("addr",T.get("addr"));
			break;
		}
		case 20:{//T F {T.addr=F.addr}
			StatuStackRecord F=st.top();
			res.set("addr", F.get("addr"));
			break;
		}
		
		case 21:{//T T * F {T.addr=new Temp();gen(T.addr = T(1).addr * F.addr);}
			StatuStackRecord T=st.get(st.tip-3);
			StatuStackRecord F=st.top();
			//T.addr=new Temp();
			res.set("addr", new_Temp());
			//gen(T.addr = T(1).addr * F.addr);
			StringBuilder sb=new StringBuilder();
			sb.append(res.get("addr"));
			sb.append(" = ");
			sb.append(T.get("addr"));
			sb.append(" * ");
			sb.append(F.get("addr"));
			code.add(sb.toString());
			break;
		}
		case 22:{//T T / F {T.addr=new Temp();gen(T.addr = T(1).addr / F.addr);}
			StatuStackRecord T=st.get(st.tip-3);
			StatuStackRecord F=st.top();
			//T.addr=new Temp();
			res.set("addr", new_Temp());
			//gen(T.addr = T(1).addr / F.addr);
			StringBuilder sb=new StringBuilder();
			sb.append(res.get("addr"));
			sb.append(" = ");
			sb.append(T.get("addr"));
			sb.append(" / ");
			sb.append(F.get("addr"));
			code.add(sb.toString());
			break;
		}
		case 23:{//F ( E ) {F.addr=E.addr}
			StatuStackRecord E=st.get(st.tip-2);
			//F.addr=E.addr
			res.set("addr", E.get("addr"));
			break;
		}
		case 24:{//F id {F.addr=top.get(id.lexeme)}
			res.set("addr", st.top().token.getName());
			break;
		}
		case 25:{//F int10 {F.addr=top.get(int10.lexeme)}
			res.set("addr", st.top().token.getName());
			break;
		}	
		default:
			break;
		}
		return res;
	}
	private static String new_Temp() {
		int i=0;
		while(true) {
			StringBuilder sb=new StringBuilder();
			sb.append("temp");
			sb.append(i);
			if(LexicalAnalyze.symbolList.get(sb.toString())==null) {
				LexicalAnalyze.symbolList.add(new TempToken(sb.toString()));
				return sb.toString();
			}
		}		
	}
	private static ArrayList<Integer> makelist(int x){
		ArrayList<Integer> res=new ArrayList<Integer>();
		res.add(x);
		return res;
	}
	private static ArrayList<Integer> merge(ArrayList<Integer> x,ArrayList<Integer> y) {
		ArrayList<Integer> res=new ArrayList<Integer>();
		for(int i:x) {
			if(!res.contains(i)) {
				res.add(i);
			}
		}
		for(int i:y) {
			if(!res.contains(i)) {
				res.add(i);
			}
		}
		return res;
	}
	private static void backpatch(ArrayList<Integer> x,int y) {
		for(int i:x) {
			String s=code.get(i);
			StringBuilder sb=new StringBuilder();
			sb.append(s);
			sb.append(" ");
			sb.append(y);
			code.set(i, sb.toString());
		}
	}
	public static String[][] codeListToTable() {
		// TODO Auto-generated method stub
		String[][] result=new String[code.size()][1];
		for(int i=0;i<code.size();i++) {
			result[i][0]=code.get(i);
		}
		return result;
	}
}
