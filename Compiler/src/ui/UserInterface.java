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
			
			JMenuItem analyse=new JMenuItem("分析");
			analyse.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Parser.GrammerAnalyse();
				}
				
			});
			ParserMenu.add(analyse);
			
			//显示符号表菜单项创建
			JMenuItem parserMenuShowSymbolListItem=new JMenuItem("显示符号表");
			parserMenuShowSymbolListItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SymbolListDialog(self);
				}
				
			});
			ParserMenu.add(parserMenuShowSymbolListItem);
			
			//显示三地址码表菜单项创建
			JMenuItem parserMenuShowMidcodeListItem=new JMenuItem("显示三地址码表");
			parserMenuShowMidcodeListItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new MidcodeDialog(self);
				}
				
			});
			ParserMenu.add(parserMenuShowMidcodeListItem);
			
		menuBar.add(ParserMenu);
		
		
		return menuBar;
	}
	public UserInterface() {
		super("Compiler Powered by Balmy");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		self=this;
		setSize(800,800);
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
