package lexicalanalyze;

import java.util.HashMap;
import java.util.Map;

import lexicalanalyze.token.Token;

class TrieNode{
	private Token token;
	private Map<Character,TrieNode> next=new HashMap<Character,TrieNode>();
	public TrieNode() {
		token=null;
	}
	public TrieNode setNext(char c) {
		if(next.containsKey(c)) {
			return next.get(c);
		}
		TrieNode res=new TrieNode();
		next.put(c, res);
		return res;
	}
	public void setToken(Token t) {
		token=t;
	}
	public TrieNode getNext(char c) {
		if(next.containsKey(c)) {
			return next.get(c);
		}
		else {
			return null;
		}
	}
	public Token getToken() {
		return token;
	}
}
public class Trie{
	private TrieNode root;
	public Trie() {
		root=new TrieNode();
	}
	public void insert(String name, Token token) {
		TrieNode now=root;
		for(int i=0;i<name.length();i++) {
			now=now.setNext(name.charAt(i));
		}
		now.setToken(token);
		return;
	}
	public Token search(String name) {
		TrieNode now=root;
		for(int i=0;i<name.length();i++) {
			now=now.getNext(name.charAt(i));
			if(now==null) {
				return null;
			}
		}
		return now.getToken();
	}
}