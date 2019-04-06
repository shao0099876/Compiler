package lexicalanalyze;

public class LexicalError {
	private int x;
	private int y;
	private int code;
	public LexicalError(int x,int y,int code) {
		this.x=x;
		this.y=y;
		this.code=code;
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Line:");
		sb.append(x);
		sb.append(",Column:");
		sb.append(y);
		sb.append(":");
		switch(code) {
		case 0:sb.append("未定义的符号");break;
		case 1:sb.append("标识符格式错误");break;
		}
		return sb.toString();
	}
}
