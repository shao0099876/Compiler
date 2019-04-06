package ui;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
public class GUIPrintStream extends PrintStream{  
	private JTextArea component;
	private StringBuffer sb=new StringBuffer();
	public GUIPrintStream(OutputStream out,JTextArea text) {
		super(out);
		this.component=text;
	}
	@Override
	public void write(byte[] buf,int off,int len) {
		final String message=new String(buf,off,len);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sb.append(message);
				component.setText(sb.toString());
			}
		});
	}
}