package ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lexer.LexicalAnalyze;

public class SymbolListDialog extends JDialog {
	public SymbolListDialog(UserInterface ui) {
		super(ui,"���ű�",false);
		setSize(500,500);
		String[] head= {"������","��������","����ֵ"};
		String[][] data=LexicalAnalyze.symbolListToTable();
		JTable table=new JTable(data,head);
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(BorderLayout.CENTER,new JScrollPane(table));
		setContentPane(panel);
		setVisible(true);
	}
}
