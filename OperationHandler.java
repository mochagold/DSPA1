import java.io.File;

public class OperationHandler {

	//1. Create directory
	public static String mkDir(String path) {
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}
	
	//2. Delete file or directory
	public String delete(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] listfile = file.listFiles();

			for (File ifile : listfile) {
				if (ifile.isDirectory()) {
					delete(ifile.getAbsolutePath());
				} else {
					ifile.delete();
				}
			}
		}

		file.delete();
		return file.getAbsolutePath();
	}
}