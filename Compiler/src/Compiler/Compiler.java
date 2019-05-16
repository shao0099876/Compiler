package Compiler;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import parser.Parser;
import ui.UserInterface;

public class Compiler {
	private static boolean DEBUG=false;
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		Parser.compileLR();
		if(!DEBUG) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new UserInterface();
		}
		else {
			
		}
	}
}
