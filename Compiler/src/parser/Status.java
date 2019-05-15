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
}
