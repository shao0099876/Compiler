package parser;

public class Item {
	public int production;
	public int point;
	public Item(int x) {
		production=x;
		point=0;
	}
	public Item() {
		production=-1;
		point=0;
	}
	public Item(int production2, int point2) {
		production=production2;
		point=point2;
	}
	public String getSign() {
		return Parser.getProduction(production).get(point);
	}
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
            return false;   
        if (obj == this)
            return true;
        Item x=(Item)obj;
        return production==x.production&&point==x.point;
    }
	public String toString() {
		StringBuilder sb=new StringBuilder();
		Production X=Parser.getProduction(production);
		sb.append(X.left);
		sb.append("¡ú");
		for(int i=0;i<X.length();i++) {
			if(point==i) {
				sb.append("¡¤");
			}
			sb.append(X.get(i));
		}
		if(point==X.length()) {
			sb.append("¡¤");
		}
		sb.append("\n");
		return sb.toString();
	}
}
