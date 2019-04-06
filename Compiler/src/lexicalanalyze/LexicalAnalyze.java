package lexicalanalyze;

import java.util.ArrayList;

import lexicalanalyze.token.IdToken;
import lexicalanalyze.token.KeywordToken;
import lexicalanalyze.token.NumToken;
import lexicalanalyze.token.OpToken;
import lexicalanalyze.token.Token;
import ui.UserInterface;

public class LexicalAnalyze {
	//private static boolean DEBUG=true;//DEBUG输出标志
	private static int lexemeBegin;//开始标记
	private static int forward;//前推标记
	private static String code;//待分析字符串
	private static final int NUMBER=1;//数值标识
	private static final int ID=2;//标识符标识
	private static final int OP=3;//符号标识
	private static char opList[]= {'+','-','*','/','>','<','=','(',')',';','\'','!'};//符号表
	private static String keywordList[]= {"if","then","else","while","do"};//关键字表
	private static SymbolList symbolList;//符号表
	private static ArrayList<Token> tokenList;//token串表
	private static ArrayList<LexicalError> errorList;
	private static void initance() {
		code=UserInterface.getCode();//获取待分析字符串
		symbolList=new SymbolList();//符号表初始化
		tokenList=new ArrayList<Token>();//串表初始化
		errorList=new ArrayList<LexicalError>();
		for(int i=0;i<keywordList.length;i++) {//symbolList加入关键词符号
			symbolList.add(new KeywordToken(keywordList[i]));
		}
		lexemeBegin=0;//初始化指针
		forward=0;
	}
	private static void output(long diff) {
		StringBuffer sb=new StringBuffer();
		sb.append("Lexical analyze completed in ");
		sb.append(diff);
		sb.append(" ms\n");
		sb.append("Catched ");
		sb.append(errorList.size());
		sb.append(" Lexical analyze EXCEPTIONs totally.\n");
		for(int i=0;i<errorList.size();i++) {
			sb.append(errorList.get(i).toString()+"\n");
		}
		System.out.println(sb.toString());
	}
	public static void run() {//词法分析主程序
		initance();
		long time1=System.currentTimeMillis();
		match_start();//开始分析
		long time2=System.currentTimeMillis();
		long diff=time2-time1;
		output(diff);
	}
	private static void match_start() {//分析自动机初始状态
		while(forward<code.length()) {//防止越界
			if(isNumber(code.charAt(forward))) {//数字匹配
				lexemeBegin=forward;
				match_numToken();
			}
			else if(isCharacter(code.charAt(forward))) {//标识符匹配
				lexemeBegin=forward;
				match_idToken();
			}
			else if(isOp(code.charAt(forward))) {//符号匹配
				lexemeBegin=forward;
				match_opToken();
			}
			else if(isBlank(code.charAt(forward))){
				forward+=1;
			}
			else {
				recovery(0);
			}
		};
	}
	private static void match_opToken() {//符号匹配
		char c=code.charAt(forward);
		forward+=1;//提前设置forward指针指向目标串后面一位
		if(c=='='||c=='>'|c=='<'|c=='!') {//判断是否是特殊的两位符号
			if(forward<code.length()&&code.charAt(forward)=='=') {
				forward+=1;
				matched(OP);
				return;
			}
		}
		matched(OP);
		return;
	}
	private static void match_idToken() {//标识符匹配
		while(forward<=code.length()) {
			if(forward==code.length()) {//终点完成匹配
				matched(ID);
				return;
			}
			if(isNumber(code.charAt(forward))||isCharacter(code.charAt(forward))) {
				forward+=1;
			}
			else if(isBlank(code.charAt(forward))||isOp(code.charAt(forward))) {//空白字符/符号完成匹配
				matched(ID);
				lexemeBegin=forward;
				return;
			}
			else {
				recovery(0);//恐慌模式恢复
				return;
			}
		}
	}
	private static void match_numToken() {
		while(forward<=code.length()) {
			if(forward==code.length()) {
				matched(NUMBER);
				return;
			}
			if(isNumber(code.charAt(forward))) {
				forward+=1;
			}
			else if(isBlank(code.charAt(forward))||isOp(code.charAt(forward))) {
				matched(NUMBER);
				lexemeBegin=forward;
				return;
			}
			else {
				recovery(1);
				return;
			}
		}
	}
	private static boolean isNumber(char c) {
		if(c>='0'&&c<='9') {
			return true;
		}
		return false;
	}
	private static boolean isBlank(char c) {
		if(c==' '||c=='\n') {
			return true;
		}
		return false;
	}
	private static boolean isOp(char c) {
		for(int i=0;i<opList.length;i++) {
			if(c==opList[i]) {
				return true;
			}
		}
		return false;
	}
	private static boolean isCharacter(char c) {
		if(c>='a'&&c<='z') {
			return true;
		}
		if(c>='A'&&c<='Z') {
			return true;
		}
		return false;
	}
	private static void matched(int info) {
		String name=code.substring(lexemeBegin, forward);//生成名字
		Token res=symbolList.get(name);
		if(res==null) {
			switch(info) {
				case NUMBER: res=new NumToken(name);break;
				case ID: res=new IdToken(name);break;
				case OP: res=new OpToken(name);break;
			}
			symbolList.add(res);
		}
		tokenList.add(res);
	}
	private static void recovery(int errorCode) {
		int line=0;
		int column=0;
		for(int i=0;i<=forward;i++) {
			if(code.charAt(i)=='\n') {
				line+=1;
				column=0;
			}
			else {
				column+=1;
			}
		}
		errorList.add(new LexicalError(line+1,column,errorCode));
		while(forward<code.length()&&!isBlank(code.charAt(forward))&&!isOp(code.charAt(forward))){
			forward+=1;
		}
		lexemeBegin=forward;
		return;
	}
	public static String[][] symbolListToTable() {
		return symbolList.toTable();
	}
	public static String[][] tokenListToTable() {
		String[][] res=new String[tokenList.size()][3];
		for(int i=0;i<tokenList.size();i++) {
			res[i]=tokenList.get(i).toTable();
		}
		return res;
	}
}
