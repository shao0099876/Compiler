package lexicalanalyze.token;

public abstract class Token {
	
	protected String name;
	public Token(String p_name) {
		name=p_name;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("name:");
		sb.append(name);
		sb.append(" ");
		sb.append("value:");
		sb.append(valueToString());
		sb.append(" ");
		sb.append("type:");
		sb.append(getType());
		sb.append("\n");
		return sb.toString();
	}
	public abstract String valueToString();
	public abstract String getType();
	public String[] toTable() {
		String[] res=new String[3];
		res[0]=name;
		res[1]=getType();
		res[2]=valueToString();
		return res;
	}
}
