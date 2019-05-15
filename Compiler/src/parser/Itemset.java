package parser;

import java.util.ArrayList;

public class Itemset extends ArrayList<Item>{
	public Itemset() {
		super();
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("{\n");
		for(Item i:this) {
			sb.append(i.toString());
		}
		sb.append("}\n");
		return sb.toString();
	}
}
