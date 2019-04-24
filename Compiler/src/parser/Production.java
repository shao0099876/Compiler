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
		return right.get(point);
	}
	public int length() {
		// TODO Auto-generated method stub
		return right.size();
	}
}
