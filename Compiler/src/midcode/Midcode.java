package midcode;

import java.util.ArrayList;

import parser.Parser;
import parser.Production;
import parser.StatuStack;

public class Midcode {

	public static void action(int p_t, StatuStack st) {
		switch (p_t) {
		case 2:
			Parser.setOffset(0);
			break;

		default:
			break;
		}
	}
}
