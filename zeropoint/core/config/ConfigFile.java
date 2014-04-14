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
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import zeropoint.core.io.file.FileBase;
import zeropoint.core.logger.LoggerConfig;
import zeropoint.core.logger.LoggingFormatter;


/**
 * ConfigFile is a convenient class for handling
 * configuration files. It delays loading of the
 * configuration properties until such data is
 * requested.
 * 
 * @author Zero Point
 */
public class ConfigFile {
	protected LockableProperties conf = new LockableProperties();
	protected String path = "";
	protected boolean loaded = false;
	protected static final Logger LOGGER = LoggerConfig.config(Logger.getLogger("ConfigFile"), 0, new LoggingFormatter(LoggingFormatter.FLAG_TIME_FULL), Level.ALL, new ConsoleHandler());
	public ConfigFile(String file) {
		Handler h = new ConsoleHandler();
		h.setFormatter(new LoggingFormatter(LoggingFormatter.FLAG_TIME_FULL));
		LOGGER.addHandler(h);
		LOGGER.setLevel(Level.ALL);
		path = file;
		try {
			new FileBase(file).create();
		}
		catch (IOException e) {}
	}
	public void loadProps() {
		unloadProps();
		try {
			conf.load(new FileInputStream(path));
			loaded = true;
		}
		catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to load configuration properties from file '" + path + "'", e);
		}
	}
	public void unloadProps() {
		conf = new LockableProperties();
		loaded = false;
	}
	public LockableProperties getProperties() {
		if ( !loaded) { // Delay load (if necessary) until we need the config
			loadProps();
		}
		return conf;
	}
	public void setProperties(Properties props) {
		unloadProps();
		conf = (LockableProperties) props;
		loaded = true;
	}
	public String get(String key) {
		return get(key, null);
	}
	public String get(String key, String def) {
		if ( !loaded) {
			loadProps();
		}
		return conf.getProperty(key, def);
	}
	public void set(String key, String val) {
		if ( !loaded) {
			loadProps();
		}
		conf.setProperty(key, val);
	}
	public void set(String key) {
		if ( !loaded) {
			loadProps();
		}
		conf.remove(key);
	}
	public void unset(String key) {
		set(key);
	}
	public void remove(String key) {
		set(key);
	}
	public void delete(String key) {
		set(key);
	}
	public Set<String> keys() {
		if ( !loaded) {
			loadProps();
		}
		return conf.stringPropertyNames();
	}
	public void save() throws IOException {
		if ( !loaded) {
			return;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(path);
			Set<String> keys = conf.stringPropertyNames();
			String[] keyArray = keys.toArray(new String[] {});
			Arrays.sort(keyArray);
			out.write((new SimpleDateFormat("# EE MMM dd yyyy kk:mm:ss.SSS zz ('GMT'Z)").format(new Date()) + "\n").getBytes());
			for (String key : keyArray) {
				String val = conf.getProperty(key);
				out.write((key + "=" + val + "\n").getBytes());
			}
			out.close();
		}
		catch (FileNotFoundException e) {
			new FileBase(path).create();
			save();
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
