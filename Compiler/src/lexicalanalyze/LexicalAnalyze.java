package lexicalanalyze;

import java.util.ArrayList;

import lexicalanalyze.token.IdToken;
import lexicalanalyze.token.KeywordToken;
import lexicalanalyze.token.NumToken;
import lexicalanalyze.token.OpToken;
import lexicalanalyze.token.Token;
import ui.UserInterface;

public class LexicalAnalyze {
	private static boolean DEBUG=true;//DEBUG�����־
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
	public static void run() {//�ʷ�����������
		code=UserInterface.getTextArea().getText();//��ȡ�������ַ���
		symbolList=new SymbolList();//���ű���ʼ��
		tokenList=new ArrayList<Token>();//������ʼ��
		for(int i=0;i<keywordList.length;i++) {//symbolList����ؼ��ʷ���
			symbolList.add(new KeywordToken(keywordList[i]));
		}
		lexemeBegin=0;//��ʼ��ָ��
		forward=0;
		match_start();//��ʼ����
		if(DEBUG) {//���������Ϣ
			System.out.print(symbolList.toString());
		}
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
			else {//�����հ׷�
				forward+=1;
			}
		};
	}
	private static void match_opToken() {//����ƥ��
		char c=code.charAt(forward);
		forward+=1;//��ǰ����forwardָ��ָ��Ŀ�괮����һλ
		if(c=='='||c=='>'|c=='<'|c=='!') {//�ж��Ƿ����������λ����
			if(forward<code.length()&&code.charAt(forward)=='=') {
				forward+=1;
				matched(OP);
				return;
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
				recovery();//�ֻ�ģʽ�ָ�
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
				recovery();
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
	private static void recovery() {
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
		System.out.println("Lexical analyze EXCEPTION occured AT Line:"+(Integer.toString(line)+1)+",Column:"+Integer.toString(column));
		while(forward<code.length()&&!isBlank(code.charAt(forward))&&!isOp(code.charAt(forward))){
			forward+=1;
		}
		lexemeBegin=forward;
		return;
	}
}