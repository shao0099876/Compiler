package parser;

import java.util.ArrayList;

import lexer.token.Token;
import midcode.StatuStackRecord;

public class StatuStack {
	private ArrayList<StatuStackRecord> list;
	private int tip;
	public StatuStack() {
		list=new ArrayList<StatuStackRecord>();
		tip=0;
	}
	public StatuStackRecord top() {
		return (tip==0)?null:list.get(tip-1);
	}
	public void push(int x,Token token) {
		if(tip==list.size()) {
			list.add(new StatuStackRecord(x,token));
		}
		else {
			list.set(tip,new StatuStackRecord(x,token));
		}
		tip+=1;
	}
	public void pop(int len) {
		tip-=len;
	}
}
