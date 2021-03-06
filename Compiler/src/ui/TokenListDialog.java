package ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lexer.LexicalAnalyze;

public class TokenListDialog extends JDialog {
	public TokenListDialog(UserInterface ui) {
		super(ui,"Token串表",false);
		setSize(500,500);
		String[] head= {"token名","token类型","token值"};
		String[][] data=LexicalAnalyze.tokenListToTable();
		JTable table=new JTable(data,head);
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(BorderLayout.CENTER,new JScrollPane(table));
		setContentPane(panel);
		setVisible(true);
	}
}
