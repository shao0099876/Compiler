package lexicalanalyze;

import lexicalanalyze.token.Token;

public class SymbolList {
	private static Trie trie;
	public SymbolList() {
		trie=new Trie();
	}
	public void add(Token token) {
		String name=token.getName();
		trie.insert(name,token);
	}
	public Token get(String name) {
		return trie.search(name);
	}
	public int size() {
		return trie.size();
	}
	public String[][] toTable() {
		return trie.toTable();
	}
	
}
