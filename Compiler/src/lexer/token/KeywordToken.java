package lexer.token;

public class KeywordToken extends Token{
	public KeywordToken(String p_name) {
		super(p_name);
	}

	public KeywordToken(String code, int lexemeBegin, int forward) {
		super(code.substring(lexemeBegin, forward));
	}

	@Override
	public String valueToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "KeywordToken";
	}

}
