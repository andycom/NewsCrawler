package cn.edu.zjicm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class OperateFile {

	/**
	 * Enhancement of java.io.File#createNewFile() Create the given file. If the
	 * parent directory don't exists, we will create them all.
	 * 
	 * @param file
	 *            the file to be created
	 * @return true if the named file does not exist and was successfully
	 *         created; false if the named file already exists
	 * @see java.io.File#createNewFile
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException {
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}

	/**
	 * Enhancement of java.io.File#mkdir() Create the given directory . If the
	 * parent folders don't exists, we will create them all.
	 * 
	 * @see java.io.File#mkdir()
	 * @param dir
	 *            the directory to be created
	 */
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	/**
	 * 
	 * @param arg
	 */
	public boolean htmlwrite(String filepath, String str) {
		String filePath = "Htmlcode\\" + filepath;
		boolean created = false;
		// public File file;
		// System.out.println(filePath);
		File file = new File(filePath);
		try {
			str = (new String(str.getBytes("utf-8"), "charset=gb2312")).trim();
		} catch (Exception ex) {
		}
		try {

			file.createNewFile();
			BufferedWriter bfWriter = new BufferedWriter(new FileWriter(file));
			String versionStr = "version:1.0\n";
			Date date = new Date();
			String dateStr = "date:" + date.toString() + "\n";

			bfWriter.append(versionStr);
			// bfWriter.append(URLStr);
			bfWriter.append(dateStr);

			bfWriter.append(str);
			bfWriter.newLine();
			bfWriter.newLine();
			bfWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return created;
	}

}
