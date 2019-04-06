package ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lexicalanalyze.LexicalAnalyze;

public class SymbolListDialog extends JDialog {
	public SymbolListDialog(UserInterface ui) {
		super(ui,"���ű�",false);
		setSize(500,500);
		
		JPanel panel=new JPanel(new BorderLayout());
		
		JScrollPane scrollPane=new JScrollPane();
		String[] head= {"������","��������","����ֵ"};
		String[][] data=LexicalAnalyze.symbolListToTable();
		JTable table=new JTable(data,head);
		scrollPane.add(table);
		panel.add(BorderLayout.CENTER,scrollPane);
	}
}
