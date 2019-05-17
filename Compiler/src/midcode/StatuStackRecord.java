package midcode;

import java.util.HashMap;
import java.util.Map;

import lexer.token.Token;

public class StatuStackRecord {
	public Map<String,Object> map;
	public StatuStackRecord(int x, Token token2) {
		statusNumber=x;
		token=token2;
		map=new HashMap<String,Object>();
	}
	public StatuStackRecord() {
		map=new HashMap<String,Object>();
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
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(statusNumber);
		sb.append(" ");
		sb.append(token==null?"null":token.getName());
		sb.append(" ");
		sb.append("{");
		for(String i:map.keySet()) {
			sb.append(i);
			sb.append(":");
			sb.append(map.get(i));
			sb.append(" ");
		}
		sb.append("}");
		return sb.toString();
	}
	
}
