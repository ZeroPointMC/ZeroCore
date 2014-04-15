package zeropoint.core.io.file;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * InputFile allows easier reading of a file.
 * 
 * @see FileBase
 * @author Zero Point
 */
public class InputFile extends FileBase {
	/**
	 * Create a new InputFile pointing to the given path
	 * 
	 * @param fpath
	 *            - the path of the file
	 */
	public InputFile(String fpath) {
		super(fpath);
	}
	/**
	 * Read the entire contents of the file
	 * 
	 * @return the contents of the entire file
	 * @throws IOException
	 *             if the file cannot be read
	 */
	@SuppressWarnings("deprecation")
	public String readAll() throws IOException {
		lock();
		try {
			return new String(Files.readAllBytes(Paths.get(path())));
		}
		catch (IOException e) {
			this.isLocked = false;
			throw e;
		}
	}
	/**
	 * Reads the individual lines of the file - doesn't work yet
	 * 
	 * @return an array of the lines in the file
	 * @throws IOException
	 *             if the file cannot be read
	 */
	@SuppressWarnings("deprecation")
	public String[] readLines() throws IOException {
		lock();
		try {
			List<String> lines = new ArrayList<String>();
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(path())));
			while (true) {
				String line = r.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);
			}
			r.close();
			return lines.toArray(new String[] {});
		}
		catch (IOException e) {
			this.isLocked = false;
			throw e;
		}
	}
}
