package parser;

import java.util.ArrayList;

public class Production {
	public String left;
	public ArrayList<String> right;
	public Production() {
		left=null;
		right=new ArrayList<String>();
	}
	public void add(String s) {
		right.add(s);
	}
	public String get(int point) {
		// TODO Auto-generated method stub
		if(right.size()==point) {
			return null;
		}
		return right.get(point);
	}
	public int length() {
		// TODO Auto-generated method stub
		return right.size();
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(left);
		sb.append(" ");
		sb.append(right.size());
		sb.append(" ");
		for(String s:right) {
			sb.append(s+" ");
		}
		return sb.toString();
	}
}
