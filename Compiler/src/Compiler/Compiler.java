package Compiler;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.UserInterface;

public class Compiler {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new UserInterface();
	}
}
