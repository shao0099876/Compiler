package parser;

import java.util.ArrayList;

import lexer.token.Token;
import midcode.StatuStackRecord;

public class StatuStack {
	private ArrayList<StatuStackRecord> list;
	public int tip;
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
	public StatuStackRecord get(int i) {
		return list.get(i);
	}
	public void push(StatuStackRecord record_tmp) {
		if(tip==list.size()) {
			list.add(record_tmp);
		}
		else {
			list.set(tip,record_tmp);
		}
		tip+=1;
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=tip-1;i>=0;i--) {
			sb.append(list.get(i).toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
