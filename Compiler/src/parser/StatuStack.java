package parser;

import java.util.ArrayList;

public class StatuStack {
	private ArrayList<Integer> list;
	private int tip;
	public StatuStack() {
		list=new ArrayList<Integer>();
		tip=0;
	}
	public int top() {
		return (tip==0)?-1:list.get(tip-1);
	}
	public void push(int x) {
		if(tip==list.size()) {
			list.add(x);
		}
		else {
			list.set(tip,x);
		}
		tip+=1;
	}
	public void pop(int len) {
		tip-=len;
	}
}
