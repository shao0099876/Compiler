package midcode;

import lexer.token.Token;

public class StatuStackRecord {
	public StatuStackRecord(int x, Token token2) {
		statusNumber=x;
		token=token2;
	}
	private Token token;
	private int statusNumber;
	public int getStatus() {
		// TODO Auto-generated method stub
		return statusNumber;
	}
	
}
