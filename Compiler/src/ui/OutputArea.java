package ui;

import javax.swing.JTextArea;

import tools.PublicConst;

public class OutputArea extends JTextArea{
	public OutputArea() {
		super(10,20);
		setFont(PublicConst.font);
		setEditable(false);
	}
}