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
	public InputFile(String fpath) {
		super(fpath);
	}
	public String readAll() throws IOException {
		lock();
		try {
			return new String(Files.readAllBytes(Paths.get(path())));
		}
		catch (IOException e) {
			isLocked = false;
			throw e;
		}
	}
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
			isLocked = false;
			throw e;
		}
	}
}
