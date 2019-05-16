package midcode;

import java.util.HashMap;
import java.util.Map;

import lexer.token.Token;

public class StatuStackRecord {
	private Map<String,Object> map;
	public StatuStackRecord(int x, Token token2) {
		statusNumber=x;
		token=token2;
		map=new HashMap<String,Object>();
	}
	public StatuStackRecord() {
	}
	public Token token;
	public int statusNumber;
	public Object get(String string) {
		// TODO Auto-generated method stub
		return map.get(string);
	}
	public void set(String string,Object x) {
		if(map.containsKey(string)) {
			map.remove(string);
		}
		map.put(string, x);
	}
	
}
