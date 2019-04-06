package lexicalanalyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	public Set<Character> getNextSet() {
		return next.keySet();
	}
}
public class Trie{
	private TrieNode root;
	private int size=0;
	public Trie() {
		root=new TrieNode();
	}
	public void insert(String name, Token token) {
		TrieNode now=root;
		for(int i=0;i<name.length();i++) {
			now=now.setNext(name.charAt(i));
		}
		now.setToken(token);
		size+=1;
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
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
	private ArrayList<String[]> ergodic(TrieNode now,int cnt) {
		Set<Character> next=now.getNextSet();
		Object[] nextArray=next.toArray();
		ArrayList<String[]> res=new ArrayList<String[]>();
		if(now.getToken()!=null) {
			res.add(now.getToken().toTable());
		}
		for(int i=0;i<nextArray.length;i++) {
			char c=((Character)nextArray[i]).charValue();
			res.addAll(ergodic(now.getNext(c),cnt));
		}
		return res;
	}
	public String[][] toTable() {
		// TODO Auto-generated method stub
		ArrayList<String[]> tmp=ergodic(root,0);
		String[][] res=new String[tmp.size()][3];
		for(int i=0;i<tmp.size();i++) {
			res[i]=tmp.get(i);
		}
		return res;
	}
}