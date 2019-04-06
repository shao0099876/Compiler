package ui;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tools.PublicConst;
public class UserInterface extends JFrame{
	private static JFrame window;
	private static JTextArea codeText=new JTextArea() {{
		setFont(PublicConst.font);
	}};
	private static OutputArea outputText=new OutputArea();
	public UserInterface() {
		show();
	}
	public void show() {
		window=new JFrame("Compiler Powered by Balmy");
		window.setSize(1000,1000);
		JPanel mainFrame=new JPanel(new BorderLayout());
		
		JPanel mainFrame_north=new JPanel(new BorderLayout());
		mainFrame_north.add(BorderLayout.CENTER,new ToolBar(window));
		
		mainFrame.add(BorderLayout.NORTH,mainFrame_north);
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
