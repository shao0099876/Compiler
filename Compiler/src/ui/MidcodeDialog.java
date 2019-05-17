package ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lexer.LexicalAnalyze;
import midcode.Midcode;

public class MidcodeDialog extends JDialog{
	public MidcodeDialog(UserInterface ui) {
		super(ui,"����ַ���",false);
		setSize(500,500);
		String[] head= {"����"};
		String[][] data=Midcode.codeListToTable();
		JTable table=new JTable(data,head);
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(BorderLayout.CENTER,new JScrollPane(table));
		setContentPane(panel);
		setVisible(true);
	}
}
