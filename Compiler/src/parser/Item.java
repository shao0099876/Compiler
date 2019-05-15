package parser;

public class Item {
	public int production;
	public int point;
	public Item(int x) {
		production=x;
		point=0;
	}
	public Item() {
		// TODO Auto-generated constructor stub
		production=-1;
		point=0;
	}
	public String getSign() {
		// TODO Auto-generated method stub
		return Parser.G.get(production).get(point);
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
		Production X=Parser.G.get(production);
		sb.append(X.left);
		sb.append("��");
		for(int i=0;i<X.length();i++) {
			if(point==i) {
				sb.append("��");
			}
			sb.append(X.get(i));
		}
		if(point==X.length()) {
			sb.append("��");
		}
		sb.append("\n");
		return sb.toString();
	}
}
