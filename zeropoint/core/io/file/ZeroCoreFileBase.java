package zeropoint.core.io.file;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import zeropoint.core.ILockable;
import zeropoint.core.exception.LockedException;


/**
 * ZeroCoreFileBase keeps track of multiple parts of a file's path, and includes methods for controlling it.
 * 
 * @author Zero Point
 */
public class ZeroCoreFileBase implements ILockable {
	public static final String fileSep = System.getProperty("file.separator");
	public static final String tmpDir = System.getProperty("java.io.tmpdir");
	public static final String lineSep = System.getProperty("line.separator");
	public static final String pathSep = System.getProperty("path.separator");
	public static final String home = System.getProperty("user.home");
	private String folder, basename, file, extension, path;
	protected File fobj;
	protected boolean isLocked = false;
	public ZeroCoreFileBase() {
		folder("");
		basename("");
		file = "";
		extension("");
		path("");
		reloadObjectFile();
	}
	public ZeroCoreFileBase(String fpath) {
		File tmp = (new File(fpath)).toPath().normalize().toFile(); // Paranoia.
		path(tmp.toPath().normalize().toString());
		folder(tmp.getParent());
		file(tmp.getName());
		if (file.indexOf(".") != -1) {
			basename(file.substring(0, file.lastIndexOf(".")));
			extension(file.substring(file.lastIndexOf(".") + 1));
		}
		else {
			basename(file);
			extension("");
		}
		reloadObjectFile();
	}
	public ZeroCoreFileBase(File fpath) {
		path(fpath.toPath().normalize().toString());
		folder(fpath.getParent());
		file(fpath.getName());
		if (file.indexOf(".") != -1) {
			basename(file.substring(0, file.lastIndexOf(".")));
			extension(file.substring(file.lastIndexOf(".") + 1));
		}
		else {
			basename(file);
			extension("");
		}
		reloadObjectFile();
	}
	public ZeroCoreFileBase(Path fpath) {
		path(fpath.normalize().toString());
		folder(fpath.toFile().getParent());
		file(fpath.toFile().getName());
		if (file.indexOf(".") != -1) {
			basename(file.substring(0, file.lastIndexOf(".")));
			extension(file.substring(file.lastIndexOf(".") + 1));
		}
		else {
			basename(file);
			extension("");
		}
		reloadObjectFile();
	}
	public ZeroCoreFileBase(ZeroCoreFileBase fpath) {
		path(fpath.path());
		folder(fpath.folder());
		file(fpath.file());
		basename(fpath.basename());
		extension(fpath.extension());
		reloadObjectFile();
	}
	protected void reloadObjectFile() {
		if (locked()) {
			throw new LockedException();
		}
		fobj = new File(path());
	}
	@Override
	public String toString() {
		return path();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.basename == null) ? 0 : this.basename.hashCode());
		result = (prime * result) + ((this.extension == null) ? 0 : this.extension.hashCode());
		result = (prime * result) + ((this.file == null) ? 0 : this.file.hashCode());
		result = (prime * result) + ((this.folder == null) ? 0 : this.folder.hashCode());
		result = (prime * result) + ((this.path == null) ? 0 : this.path.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if ( !(obj instanceof ZeroCoreFileBase)) {
			return false;
		}
		ZeroCoreFileBase other = (ZeroCoreFileBase) obj;
		if (this.basename == null) {
			if (other.basename != null) {
				return false;
			}
		}
		else if ( !this.basename.equals(other.basename)) {
			return false;
		}
		if (this.extension == null) {
			if (other.extension != null) {
				return false;
			}
		}
		else if ( !this.extension.equals(other.extension)) {
			return false;
		}
		if (this.file == null) {
			if (other.file != null) {
				return false;
			}
		}
		else if ( !this.file.equals(other.file)) {
			return false;
		}
		if (this.folder == null) {
			if (other.folder != null) {
				return false;
			}
		}
		else if ( !this.folder.equals(other.folder)) {
			return false;
		}
		if (this.path == null) {
			if (other.path != null) {
				return false;
			}
		}
		else if ( !this.path.equals(other.path)) {
			return false;
		}
		return true;
	}
	public String folder() {
		return folder;
	}
	protected void folder(String newFolder) {
		if (locked()) {
			throw new LockedException();
		}
		if (newFolder == null) {
			return;
		}
		File tmp = new File(newFolder);
		try {
			this.folder = tmp.getCanonicalPath();
		}
		catch (IOException e) {
			this.folder = tmp.getAbsolutePath();
		}
		reloadObjectFile();
	}
	public String basename() {
		return basename;
	}
	protected void basename(String newBasename) {
		if (locked()) {
			throw new LockedException();
		}
		this.basename = newBasename;
		reloadObjectFile();
	}
	public String file() {
		return basename;
	}
	protected void file(String newFile) {
		if (locked()) {
			throw new LockedException();
		}
		this.file = newFile;
		reloadObjectFile();
	}
	public String extension() {
		return extension;
	}
	protected void extension(String newExtension) {
		if (locked()) {
			throw new LockedException();
		}
		this.extension = newExtension;
		reloadObjectFile();
	}
	public String path() {
		return path;
	}
	protected void path(String newPath) {
		if (locked()) {
			throw new LockedException();
		}
		this.path = (new File(newPath)).toPath().normalize().toString();
		reloadObjectFile();
	}
	public boolean create() throws IOException {
		fobj.createNewFile();
		return true;
	}
	public boolean delete() {
		return fobj.delete();
	}
	public void deleteOnExit() {
		fobj.deleteOnExit();
	}
	public boolean readable() {
		return fobj.canRead();
	}
	public boolean executable() {
		return fobj.canExecute();
	}
	public boolean writable() {
		return fobj.canWrite();
	}
	public boolean exists() {
		return fobj.exists();
	}
	public long size() {
		return fobj.length();
	}
	public void lock() {
		isLocked = true;
	}
	public boolean locked() {
		return isLocked;
	}
}
