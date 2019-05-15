package parser;

import java.util.ArrayList;

public class GrammerSet extends ArrayList<Production>{
	public GrammerSet() {
		super();
	}

	public String getLeft(int production) {
		return this.get(production).left;
	}
}
