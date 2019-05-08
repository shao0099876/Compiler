package Compiler;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import parser.Parser;
import ui.UserInterface;

public class Compiler {
	private static boolean DEBUG=true;
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		if(!DEBUG) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new UserInterface();
		}
		else {
			Parser.compileLR();
		}
	}
}
