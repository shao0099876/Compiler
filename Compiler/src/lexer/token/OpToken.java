package lexer.token;

public class OpToken extends Token {

	public OpToken(String p_name) {
		super(p_name);
		// TODO Auto-generated constructor stub
	}
	

	public OpToken(String code, int lexemeBegin, int forward) {
		// TODO Auto-generated constructor stub
		super(code.substring(lexemeBegin,forward));
	}


	@Override
	public String valueToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "OpToken";
	}

}
