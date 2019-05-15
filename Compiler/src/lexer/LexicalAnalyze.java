package lexer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lexer.token.IdToken;
import lexer.token.KeywordToken;
import lexer.token.NumToken;
import lexer.token.OpToken;
import lexer.token.Token;
import ui.UserInterface;

public class LexicalAnalyze {
	//private static boolean DEBUG=true;//DEBUG�����־
	private static int lexemeBegin;//��ʼ���
	private static int forward;//ǰ�Ʊ��
	private static String code;//�������ַ���
	private static final int NUMBER=1;//��ֵ��ʶ
	private static final int ID=2;//��ʶ����ʶ
	private static final int OP=3;//���ű�ʶ
	private static char opList[]= {'+','-','*','/','>','<','=','(',')',';','\'','!'};//���ű�
	private static String keywordList[]= {"if","then","else","while","do"};//�ؼ��ֱ�
	private static SymbolList symbolList;//���ű�
	private static ArrayList<Token> tokenList;//token����
	private static ArrayList<LexicalError> errorList;
	private static void initance() {
		code=UserInterface.getCode();//��ȡ�������ַ���
		symbolList=new SymbolList();//���ű���ʼ��
		tokenList=new ArrayList<Token>();//������ʼ��
		errorList=new ArrayList<LexicalError>();
		for(int i=0;i<keywordList.length;i++) {//symbolList����ؼ��ʷ���
			symbolList.add(new KeywordToken(keywordList[i]));
		}
		lexemeBegin=0;//��ʼ��ָ��
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
	public static void run() {//�ʷ�����������
		initance();
		long time1=System.currentTimeMillis();
		match_start();//��ʼ����
		long time2=System.currentTimeMillis();
		long diff=time2-time1;
		output(diff);
	}
	private static void match_start() {//�����Զ�����ʼ״̬
		while(forward<code.length()) {//��ֹԽ��
			if(isNumber(code.charAt(forward))) {//����ƥ��
				lexemeBegin=forward;
				match_numToken();
			}
			else if(isCharacter(code.charAt(forward))) {//��ʶ��ƥ��
				lexemeBegin=forward;
				match_idToken();
			}
			else if(isOp(code.charAt(forward))) {//����ƥ��
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
	private static void match_opToken() {//����ƥ��
		char c=code.charAt(forward);
		forward+=1;//��ǰ����forwardָ��ָ��Ŀ�괮����һλ
		if(c=='='||c=='>'|c=='<'|c=='!') {//�ж��Ƿ����������λ����
			if(forward<code.length()&&code.charAt(forward)=='=') {
				forward+=1;
			}
		}
		matched(OP);
		return;
	}
	private static void match_idToken() {//��ʶ��ƥ��
		while(forward<=code.length()) {
			if(forward==code.length()) {//�յ����ƥ��
				matched(ID);
				return;
			}
			if(isNumber(code.charAt(forward))||isCharacter(code.charAt(forward))) {
				forward+=1;
			}
			else if(isBlank(code.charAt(forward))||isOp(code.charAt(forward))) {//�հ��ַ�/�������ƥ��
				matched(ID);
				lexemeBegin=forward;
				return;
			}
			else {
				recovery(0);//�ֻ�ģʽ�ָ�
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
		String name=code.substring(lexemeBegin, forward);//��������
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
	public static void outputToFile() throws IOException {
		BufferedWriter writer=new BufferedWriter(new FileWriter("symbolList.txt"));
		String[][] tmp=symbolListToTable();
		for(int i=0;i<tmp.length;i++) {
			for(int j=0;j<3;j++) {
				writer.write(tmp[i][j]+" ");
			}
			writer.write("\r\n");
		}
		writer.close();
		
		writer=new BufferedWriter(new FileWriter("tokenList.txt"));
		String[][] tmp1=tokenListToTable();
		for(int i=0;i<tmp.length;i++) {
			for(int j=0;j<3;j++) {
				writer.write(tmp1[i][j]+" ");
			}
			writer.write("\r\n");
		}
		writer.close();
	}
}