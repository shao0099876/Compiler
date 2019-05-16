package lexer.token;

public class IdToken extends Token{
	private int type;
	private int 
	public IdToken(String name) {
		super(name);
	}
	public IdToken(String code,int lexemeBegin,int forward) {
		super(code.substring(lexemeBegin, forward));
	}
	public String getType() {
		return "IdToken";
	}
	public String valueToString() {
		return null;
	}
}
