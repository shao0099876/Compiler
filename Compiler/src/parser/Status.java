package parser;

import java.util.HashMap;
import java.util.Map;

public class Status {
	public Map<String,OPMode> ACTION;
	public Map<String,Integer> GOTO;
	public Status() {
		ACTION=new HashMap<String,OPMode>();
		GOTO=new HashMap<String,Integer>();
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(String left:ACTION.keySet()) {
			sb.append(left);
			sb.append(":");
			sb.append(ACTION.get(left).toString());
			sb.append(";;");
		}
		for(String left:GOTO.keySet()) {
			sb.append(left);
			sb.append("->");
			sb.append(GOTO.get(left));
			sb.append(";;");
		}
		return sb.toString();
	}
}
