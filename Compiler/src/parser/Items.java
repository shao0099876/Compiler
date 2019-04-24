package parser;

public class Items {
	public Production production;
	public int point;
	public Items(Production x) {
		production=x;
		point=0;
	}
	public Items() {
		// TODO Auto-generated constructor stub
		production=null;
		point=0;
	}
	public String getSign() {
		// TODO Auto-generated method stub
		return production.get(point);
	}
}
