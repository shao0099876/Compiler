package tools;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
public class FileChooser extends JFileChooser{
	public FileChooser() {
		super();
		setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if(f.getName().endsWith(".balmy")||f.isDirectory())
					return true;
				return false;
			}
			@Override
			public String getDescription() {
				return ".balmy";
			}
		});
	}
}
