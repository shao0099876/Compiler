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
		if(right.size()==point) {
			return null;
		}
		return right.get(point);
	}
	public int length() {
		return right.size();
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(left);
		sb.append("->");
		for(String s:right) {
			sb.append(s+" ");
		}
		return sb.toString();
	}
	public boolean equals(Object obj) {
		if (!(obj instanceof Production))
            return false;   
        if (obj == this)
            return true;
        Production x=(Production)obj;
        if(!this.left.equals(x.left)) {
        	return false;
        }
        if(this.right.size()!=x.right.size()) {
        	return false;
        }
        for(int i=0;i<this.right.size();i++) {
        	if(!this.right.get(i).equals(x.right.get(i))) {
        		return false;
        	}
        }
        return true;
	}
	public boolean isLeft(String b) {
		return left.equals(b);
	}
}
