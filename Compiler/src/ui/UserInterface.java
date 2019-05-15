package ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lexer.LexicalAnalyze;
import parser.Parser;
import tools.PublicConst;
public class UserInterface extends JFrame{
	private static JTextArea codeText=new JTextArea() {{
		setFont(PublicConst.font);
	}};
	private static OutputArea outputText=new OutputArea();
	private static UserInterface self;
	private JMenuBar setJMenuBar() {
		JMenuBar menuBar=new JMenuBar();
		//词法分析菜单创建
		JMenu lexicalAnalyzeMenu=new JMenu("词法分析");
		
			//运行菜单项创建
			JMenuItem lexicalAnalyzeMenuRunItem=new JMenuItem("运行");
			lexicalAnalyzeMenuRunItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					LexicalAnalyze.run();
				}
				
			});
			lexicalAnalyzeMenu.add(lexicalAnalyzeMenuRunItem);
			
			//显示符号表菜单项创建
			JMenuItem lexicalAnalyzeMenuShowSymbolListItem=new JMenuItem("显示符号表");
			lexicalAnalyzeMenuShowSymbolListItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SymbolListDialog(self);
				}
				
			});
			lexicalAnalyzeMenu.add(lexicalAnalyzeMenuShowSymbolListItem);
			
			//显示Token串菜单项创建
			JMenuItem lexicalAnalyzeMenuShowTokenListItem=new JMenuItem("显示Token串表");
			lexicalAnalyzeMenuShowTokenListItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new TokenListDialog(self);
				}
				
			});
			lexicalAnalyzeMenu.add(lexicalAnalyzeMenuShowTokenListItem);
			
			//保存菜单项创建
			JMenuItem lexicalAnalyzeMenuSaveToFileItem=new JMenuItem("保存到文件");
			lexicalAnalyzeMenuSaveToFileItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						LexicalAnalyze.outputToFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			lexicalAnalyzeMenu.add(lexicalAnalyzeMenuSaveToFileItem);
			
		menuBar.add(lexicalAnalyzeMenu);
		
		//语法分析菜单创建
		JMenu ParserMenu=new JMenu("语法分析");
			
			//编译LR(0)文法
			JMenuItem compileLR=new JMenuItem("编译LR(0)文法");
			compileLR.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						Parser.compileLR();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			
		
		
		return menuBar;
	}
	public UserInterface() {
		super("Compiler Powered by Balmy");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		self=this;
		setSize(1000,1000);
		JPanel mainFrame=new JPanel(new BorderLayout());
			mainFrame.add(BorderLayout.NORTH,setJMenuBar());
			mainFrame.add(BorderLayout.CENTER,new JScrollPane(codeText));
			mainFrame.add(BorderLayout.SOUTH, new JScrollPane(outputText));
				System.setOut(new GUIPrintStream(System.out,outputText));
		setContentPane(mainFrame);
		setVisible(true);
	}
	public static String getCode() {
		return codeText.getText();
	}
}
