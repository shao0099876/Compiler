package ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lexicalanalyze.LexicalAnalyze;
import tools.PublicConst;
public class UserInterface extends JFrame{
	private static JFrame window;
	private static JTextArea codeText=new JTextArea() {{
		setFont(PublicConst.font);
	}};
	private static OutputArea outputText=new OutputArea();
	public static UserInterface self;
	
	public UserInterface() {
		self=this;
		show();
	}
	public void show() {
		window=new JFrame("Compiler Powered by Balmy");
		window.setSize(1000,1000);
		
		JMenuBar menuBar=new JMenuBar();
		
		JMenu lexicalAnalyzeMenu=new JMenu("词法分析");
		JMenuItem lexicalAnalyzeMenuRunItem=new JMenuItem("运行");
		lexicalAnalyzeMenuRunItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LexicalAnalyze.run();
			}
			
		});
		lexicalAnalyzeMenu.add(lexicalAnalyzeMenuRunItem);
		
		JMenuItem lexicalAnalyzeMenuShowSymbolListItem=new JMenuItem("显示符号表");
		lexicalAnalyzeMenuShowSymbolListItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SymbolListDialog symbolListDialog=new SymbolListDialog(self);
			}
			
		});
		lexicalAnalyzeMenu.add(lexicalAnalyzeMenuShowSymbolListItem);
		
		JMenuItem lexicalAnalyzeMenuShowTokenListItem=new JMenuItem("显示Token串表");
		lexicalAnalyzeMenuShowTokenListItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TokenListDialog tokenListDialog=new TokenListDialog();
			}
			
		});
		lexicalAnalyzeMenu.add(lexicalAnalyzeMenuShowTokenListItem);
		
		JMenuItem lexicalAnalyzeMenuSaveToFileItem=new JMenuItem("保存到文件");
		lexicalAnalyzeMenuSaveToFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		menuBar.add(lexicalAnalyzeMenu);
		
		JPanel mainFrame=new JPanel(new BorderLayout());
		mainFrame.add(BorderLayout.NORTH,menuBar);
		mainFrame.add(BorderLayout.CENTER,new JScrollPane(codeText));
		System.setOut(new GUIPrintStream(System.out,outputText));
		mainFrame.add(BorderLayout.SOUTH, new JScrollPane(outputText));
		window.setContentPane(mainFrame);
		window.setVisible(true);
	}
	public static JTextArea getTextArea() {
		return codeText;
	}
}
