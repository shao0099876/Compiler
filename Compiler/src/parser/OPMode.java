package parser;

public class OPMode {
	public int op;
	public int data;
	public OPMode(int x,int y) {
		op=x;
		data=y;
	}
	public OPMode(int i) {
		// TODO Auto-generated constructor stub
		op=i;
		data=-1;
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		switch(op) {
		case 0:sb.append("acc");break;
		case 1:sb.append("s");sb.append(data);break;
		case 2:sb.append("r");sb.append(data);break;
		}
		return sb.toString();
	}
}
