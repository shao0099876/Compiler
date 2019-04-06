package lexicalanalyze.token;

public class NumToken extends Token {
	private int value;
	public NumToken(String p_name) {
		super(p_name);
		value=Integer.valueOf(p_name);
	}
	public NumToken(String code, int lexemeBegin, int forward) {
		// TODO Auto-generated constructor stub
		super(Integer.toString(lexemeBegin)+"_"+Integer.toString(forward));
		value=Integer.valueOf(code.substring(lexemeBegin,forward));
	}
	@Override
	public String getType() {
		return "NumToken";
	}
	public String valueToString() {
		return Integer.toString(value);
	}
	
}
