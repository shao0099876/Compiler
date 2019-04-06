package lexicalanalyze;

import lexicalanalyze.token.Token;

public class SymbolList {
	private static Trie trie;//内置Trie树，用于快速查找
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
	
}
