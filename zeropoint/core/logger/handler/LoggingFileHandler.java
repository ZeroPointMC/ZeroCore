// I'm too sober for this...
package zeropoint.core.logger.handler;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.LogRecord;

import zeropoint.core.io.file.ZeroCoreFileBase;


/*
 * Welcome to Hell.
 * 
 * Here's what needs to be done:
 ** TODO Code the ability write/append to unlimited rotating files
 *** TODO on output, check file size against max size; if file size too high: close, increment generation #, open new file
 ** TODO Code the handling of limited rotating log files
 *** TODO if log generation reaches limit: set generation to 0, continue as normal
 */
/**
 * LoggingFileHandler is a logging handler that writes to files, built on ZeroCore's LoggingStreamHandler class.
 * 
 * @author Zero Point
 */
// Let the madness begin...
public class LoggingFileHandler extends LoggingStreamHandler {
	private String pat = ""; // The pattern for the log file(s)
	protected boolean app = false; // Flag for appending
	private boolean isRotating = false; // Flag for rotating log files
	private long limit = 0; // Limit to size of log file
	protected int count = 0; // Limit to number of log files
	protected int currGen = 0; // Generation number for rotating log files
	protected String currPath = "";
	protected int uid = 0;
	protected boolean uidSet = false;
	public static final long MINLIM = 512;
	public LoggingFileHandler() {
		pat = "%t/%u.log"; // Initialize a simple default pattern
	}
	public LoggingFileHandler(String pattern) {
		pat = pattern;
	}
	public LoggingFileHandler(String pattern, boolean append) {
		pat = pattern;
		app = append;
	}
	public LoggingFileHandler(String pattern, int newLimit, int newCount) {
		pat = pattern;
		isRotating = true;
		this.limit = newLimit >= MINLIM ? newLimit : MINLIM;
		this.count = newCount;
		if ( !pat.contains("%g")) {
			pat = pat + ".%g";
		}
	}
	public LoggingFileHandler(String pattern, int newLimit, int newCount, boolean append) {
		pat = pattern;
		isRotating = true;
		this.limit = newLimit >= MINLIM ? newLimit : MINLIM;
		this.count = newCount;
		app = append;
		if ( !pat.contains("%g")) {
			pat = pat + ".%g";
		}
	}
	public long getLimit() {
		return limit;
	}
	protected void setLimit(long newLimit) {
		this.limit = newLimit >= MINLIM ? newLimit : MINLIM;
	}
	protected void unlimit() {
		limit = 0;
	}
	public long currFileSize() {
		return new File(currPath).length(); // Remember, currFileSize is private. No editing.
	}
	public String pattern() {
		return pat; // Pattern is private. No changey.
	}
	public boolean rotating() {
		return isRotating; // Private var. Don't touch.
	}
	protected String generation() {
		// Did you know that you can't cast from int to String?
		// I can't believe how stupid that is.
		return String.valueOf(currGen);
	}
	protected String getFileName() {
		return getFileName(0);
	}
	protected String getFileName(int i) {
		// You know those shirts/mugs/hats/etc that say
		// "You don't have to be insane to <x>, but it helps", right?
		// Yeah, you might actually have to be insane to read this.
		// I wrote the damn thing and I barely know what it's doing.
		if (i < 0) {
			i = -i;
		}
		currGen++ ;
		if (currGen > count) {
			currGen = 0;
		}
		String tmp = pat;
		tmp = tmp.replaceAll("/", System.getProperty("file.separator", "/"));
		tmp = tmp.replaceAll("%t", System.getProperty("java.io.tmpdir", "/tmp"));
		tmp = tmp.replaceAll("%h", System.getProperty("user.home"));
		tmp = tmp.replaceAll("%g", generation());
		if ( !pat.contains("%u")) {
			tmp = tmp + ".%u";
		}
		// Shut up.
		// Just shut up.
		// I don't want to hear a WORD about this bit.
		if ( !uidSet) {
			while (true) {
				ZeroCoreFileBase tf = new ZeroCoreFileBase(tmp.replaceAll("%u", String.valueOf(i)).replaceAll("%%", "%"));
				if ( !tf.exists()) {
					uid = i;
					uidSet = true;
					tmp = tf.path();
					break;
				}
				i++ ;
			}
		}
		else {
			tmp = tmp.replaceAll("%u", String.valueOf(uid));
			new ZeroCoreFileBase(tmp).delete();
		}
		return tmp;
	}
	protected String createFile() throws IOException {
		ZeroCoreFileBase t;
		do {
			t = new ZeroCoreFileBase(getFileName());
		} while (t.exists());
		t.create();
		currPath = t.path();
		return t.path();
	}
	protected void open() throws IOException {
		try {
			close();
		}
		catch (NullPointerException e) {}
		out = new FileOutputStream(createFile(), app);
	}
	@Override
	public void publish(LogRecord msg) {
		try {
			if (out == null) {
				open();
			}
			if (this.isLoggable(msg)) {
				long len = getByteLength(msg);
				// If the current message would go over the size limit,
				if ((limit >= MINLIM) && ((currFileSize() + len) > limit)) {
					// open a new file.
					open();
				}
				super.publish(msg);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
