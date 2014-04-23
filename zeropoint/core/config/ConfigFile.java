package zeropoint.core.config;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import zeropoint.core.io.file.FileBase;


/**
 * ConfigFile is a convenient class for handling
 * configuration files. It delays loading of the
 * configuration properties until such data is
 * requested.
 * 
 * @author Zero Point
 */

public class ConfigFile {
	/**
	 * The configuration properties
	 */
	protected LockableProperties conf = new LockableProperties();
	/**
	 * The path to the file we are using
	 */
	protected String path = "";
	/**
	 * Whether or not we have loaded the properties yet
	 */
	protected boolean loaded = false;
	/**
	 * Initialize to a certain file
	 * 
	 * @param file
	 *            - the file path to use to store properties
	 */
	public ConfigFile(String file) {
		this.path = file;
		try {
			new FileBase(file).create();
		}
		catch (IOException e) {}
	}
	/**
	 * Attempts to load the config file as properties
	 */
	public void loadProps() {
		unloadProps();
		try {
			this.conf.load(new FileInputStream(this.path));
			this.loaded = true;
		}
		catch (IOException e) {}
	}
	/**
	 * Clear the current properties entirely
	 */
	public void unloadProps() {
		this.conf = new LockableProperties();
		this.loaded = false;
	}
	/**
	 * @return the {@link LockableProperties} used as the current config
	 */
	public LockableProperties getProperties() {
		if ( !this.loaded) { // Delay load (if necessary) until we need the config
			loadProps();
		}
		return this.conf;
	}
	/**
	 * Overwrite the current configuration
	 * 
	 * @param props
	 *            - the new {@link Properties} to use
	 */
	public void setProperties(Properties props) {
		unloadProps();
		this.conf = (LockableProperties) props;
		this.loaded = true;
	}
	/**
	 * Retrieve the value of a config key, returning <code>null</code> if it does not exist
	 * 
	 * @param key
	 *            - the key to get the value of
	 * @return the requested config value
	 */
	public String get(String key) {
		return get(key, null);
	}
	/**
	 * Retrieve the value of a config key, with a default value if the key does not exist
	 * 
	 * @param key
	 *            - the key to get the value of
	 * @param def
	 *            - the default value to return if the key does not exist
	 * @return the requested config value
	 */
	public String get(String key, String def) {
		if ( !this.loaded) {
			loadProps();
		}
		return this.conf.getProperty(key, def);
	}
	/**
	 * Set a configuration value
	 * 
	 * @param key
	 *            - the key to unset
	 * @param val
	 *            - the value to set the key to
	 */
	public void set(String key, String val) {
		if ( !this.loaded) {
			loadProps();
		}
		this.conf.setProperty(key, val);
	}
	/**
	 * Unset a configuration value
	 * 
	 * @param key
	 *            - the key to unset
	 */
	public void set(String key) {
		if ( !this.loaded) {
			loadProps();
		}
		this.conf.remove(key);
	}
	/**
	 * Unset a configuration value
	 * 
	 * @param key
	 *            - the key to unset
	 */
	public void unset(String key) {
		set(key);
	}
	/**
	 * Unset a configuration value
	 * 
	 * @param key
	 *            - the key to unset
	 */
	public void remove(String key) {
		set(key);
	}
	/**
	 * Unset a configuration value
	 * 
	 * @param key
	 *            - the key to unset
	 */
	public void delete(String key) {
		set(key);
	}
	/**
	 * @return a {@link Set} of the keys in the current configuration
	 */
	public Set<String> keys() {
		if ( !this.loaded) {
			loadProps();
		}
		return this.conf.stringPropertyNames();
	}
	/**
	 * Saves the current configuration to the file
	 * 
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	public void save() throws IOException {
		if ( !this.loaded) {
			return;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(this.path);
			Set<String> keys = this.conf.stringPropertyNames();
			String[] keyArray = keys.toArray(new String[] {});
			Arrays.sort(keyArray);
			out.write((new SimpleDateFormat("# EE MMM dd yyyy kk:mm:ss.SSS zz ('GMT'Z)").format(new Date()) + "\n").getBytes());
			for (String key : keyArray) {
				String val = this.conf.getProperty(key);
				out.write((key + "=" + val + "\n").getBytes());
			}
			out.close();
		}
		catch (FileNotFoundException e) {
			new FileBase(this.path).create();
			save();
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
