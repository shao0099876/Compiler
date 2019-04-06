package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import lexicalanalyze.LexicalAnalyze;
import ui.UserInterface;

public class ToolBar extends JToolBar{
	public ToolBar(JFrame window) {
		super();
		JButton lexicalAnalyzerButton=new JButton("´Ê·¨·ÖÎö");
		lexicalAnalyzerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LexicalAnalyze.run();
			}
			
		});
		add(lexicalAnalyzerButton);
	}
}