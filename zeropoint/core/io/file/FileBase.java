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
public class FileBase implements ILockable {
	/**
	 * The <code>String</code> used to separate components of a file path on the current system
	 */
	public static final String FILE_SEP = System.getProperty("file.separator");
	/**
	 * The <code>String</code> pointing to the current system's temporary file directory
	 */
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	/**
	 * The <code>String</code> used to separate lines in a file on the current system
	 */
	public static final String LINE_SEP = System.getProperty("line.separator");
	/**
	 * The <code>String</code> used to separate components of the user's <code>PATH</code> on the current system
	 */
	public static final String PATH_SEP = System.getProperty("path.separator");
	/**
	 * The <code>String</code> path of the current user's home directory
	 */
	public static final String USER_HOME = System.getProperty("user.home");
	private String folder, basename, file, extension, path;
	/**
	 * The {@link File} object we represent
	 */
	protected File fobj;
	/**
	 * Whether this object is locked
	 * 
	 * @see ILockable
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected boolean isLocked = false;
	/**
	 * Create a blank FileBase object
	 */
	public FileBase() {
		this.folder("");
		this.basename("");
		this.file("");
		this.extension("");
		this.path("");
		this.reloadObjectFile();
	}
	/**
	 * Create a new FileBase object
	 * 
	 * @param filePath
	 *            - the <code>String</code> path to represent
	 */
	public FileBase(String filePath) {
		File tmp = (new File(filePath)).toPath().normalize().toFile(); // Paranoia.
		this.path(tmp.toPath().normalize().toString());
		this.folder(tmp.getParent());
		this.file(tmp.getName());
		if (this.file.indexOf(".") != -1) {
			this.basename(this.file.substring(0, this.file.lastIndexOf(".")));
			this.extension(this.file.substring(this.file.lastIndexOf(".") + 1));
		}
		else {
			this.basename(this.file);
			this.extension("");
		}
		this.reloadObjectFile();
	}
	/**
	 * Create a new FileBase object
	 * 
	 * @param file
	 *            - the {@link File} this object should represent
	 */
	public FileBase(File file) {
		this.path(file.toPath().normalize().toString());
		this.folder(file.getParent());
		this.file(file.getName());
		if (this.file.indexOf(".") != -1) {
			this.basename(this.file.substring(0, this.file.lastIndexOf(".")));
			this.extension(this.file.substring(this.file.lastIndexOf(".") + 1));
		}
		else {
			this.basename(this.file);
			this.extension("");
		}
		this.reloadObjectFile();
	}
	/**
	 * Create a new FileBase object
	 * 
	 * @param path
	 *            - the {@link Path} this object should represent
	 */
	public FileBase(Path path) {
		this.path(path.normalize().toString());
		this.folder(path.toFile().getParent());
		this.file(path.toFile().getName());
		if (this.file.indexOf(".") != -1) {
			this.basename(this.file.substring(0, this.file.lastIndexOf(".")));
			this.extension(this.file.substring(this.file.lastIndexOf(".") + 1));
		}
		else {
			this.basename(this.file);
			this.extension("");
		}
		this.reloadObjectFile();
	}
	/**
	 * Copy a FileBase object
	 * 
	 * @param init
	 *            - the FileBase object to copy
	 */
	public FileBase(FileBase init) {
		this.path(init.path());
		this.folder(init.folder());
		this.file(init.file());
		this.basename(init.basename());
		this.extension(init.extension());
		this.reloadObjectFile();
	}
	/**
	 * Reload the {@link File} object stored internally
	 */
	protected void reloadObjectFile() {
		if (this.locked()) {
			throw new LockedException();
		}
		this.fobj = new File(this.path());
	}
	@Override
	public String toString() {
		return this.path();
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
		if ( !(obj instanceof FileBase)) {
			return false;
		}
		FileBase other = (FileBase) obj;
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
	/**
	 * @return the file's folder
	 */
	public String folder() {
		return this.folder;
	}
	/**
	 * Changes the file this object represents
	 * 
	 * @param newFolder
	 *            - the new folder
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected void folder(String newFolder) {
		if (this.locked()) {
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
		this.reloadObjectFile();
	}
	/**
	 * @return the file's basename
	 */
	public String basename() {
		return this.basename;
	}
	/**
	 * Changes the file this object represents
	 * 
	 * @param newBasename
	 *            - the new file basename
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected void basename(String newBasename) {
		if (this.locked()) {
			throw new LockedException();
		}
		this.basename = newBasename;
		this.reloadObjectFile();
	}
	/**
	 * @return the file name
	 */
	public String file() {
		return this.file;
	}
	/**
	 * Changes the file this object represents
	 * 
	 * @param newFile
	 *            - the new file name
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected void file(String newFile) {
		if (this.locked()) {
			throw new LockedException();
		}
		this.file = newFile;
		this.reloadObjectFile();
	}
	/**
	 * @return the file extension
	 */
	public String extension() {
		return this.extension;
	}
	/**
	 * Changes the file this object represents
	 * 
	 * @param newExtension
	 *            - the new file extension
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected void extension(String newExtension) {
		if (this.locked()) {
			throw new LockedException();
		}
		this.extension = newExtension;
		this.reloadObjectFile();
	}
	/**
	 * Get the path this object represents
	 * 
	 * @return the path to the file this object represents
	 */
	public String path() {
		return this.path;
	}
	/**
	 * Changes the file this object represents
	 * 
	 * @param newPath
	 *            - the new path
	 * @deprecated FileBase objects will be made immutable in the near future.
	 */
	@Deprecated
	protected void path(String newPath) {
		if (this.locked()) {
			throw new LockedException();
		}
		this.path = (new File(newPath)).toPath().normalize().toString();
		this.reloadObjectFile();
	}
	/**
	 * Creates the file
	 * 
	 * @throws IOException
	 *             if the file could not be created
	 */
	public void create() throws IOException {
		this.fobj.createNewFile();
	}
	/**
	 * Deletes the file
	 * 
	 * @return <code>true</code> iff the file was successfully deleted
	 */
	public boolean delete() {
		return this.fobj.delete();
	}
	/**
	 * Specifies that the file should be deleted when the program exits
	 */
	public void deleteOnExit() {
		this.fobj.deleteOnExit();
	}
	/**
	 * @return whether or not the file can be read from
	 */
	public boolean readable() {
		return this.fobj.canRead();
	}
	/**
	 * @return whether or not the file can be executed
	 */
	public boolean executable() {
		return this.fobj.canExecute();
	}
	/**
	 * @return whether or not the file can be written to
	 */
	public boolean writable() {
		return this.fobj.canWrite();
	}
	/**
	 * @return whether or not the file exists
	 */
	public boolean exists() {
		return this.fobj.exists();
	}
	/**
	 * @return the size of the file in bytes
	 */
	public long size() {
		return this.fobj.length();
	}
	@Override
	public void lock() {
		this.isLocked = true;
	}
	@Override
	public boolean locked() {
		return this.isLocked;
	}
}
