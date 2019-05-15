package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
