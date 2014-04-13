package zeropoint.core;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import zeropoint.core.config.ConfigFile;
import zeropoint.core.config.Localization;
import zeropoint.core.config.LockableProperties;
import zeropoint.core.date.Datetime;
import zeropoint.core.io.file.InputFile;
import zeropoint.core.io.file.OutputFile;
import zeropoint.core.io.file.ZeroCoreFileBase;
import zeropoint.core.logger.LoggerFactory;
import zeropoint.core.logger.handler.LoggingConsoleHandler;
import zeropoint.core.logger.handler.LoggingFileHandler;
import zeropoint.core.shell.Shell;
import zeropoint.core.shell.SingleParserShell;
import zeropoint.core.shell.parser.ShellTestParser;
import zeropoint.core.string.StringUtil;
import zeropoint.core.string.hash.SHA;


/**
 * ZeroCore's self-test class. Includes a test for all functions in ZeroCore.
 * 
 * @author Zero Point
 */
public final class Test {
	public static final Reader commandStream = new StringReader("run test\nexit");
	public static final String configFile = "./ZeroCore.conf";
	public static final String line1 = "This is\\\na test.";
	public static final String line2 = "It was a\\\nsuccess.";
	public static final String multilineFile = "./ZeroCore.dat";
	public static final String postReverse = "tnemirepxe";
	public static final String preReverse = "experiment";
	public static final int testCount = 14;
	public static final String testFile = "./ZeroCore.tmp";
	public static final String testText = "Success";
	public static final String toHash = "pony";
	public static final String VERSION = "0.1.0";
	private static int errCount = 0;
	private static ArrayList<Handler> handlers = new ArrayList<Handler>();
	private static Logger LOGGER;
	private static boolean MODE_DEBUG = false;
	private static boolean RUNNING_TESTS = false;
	private static boolean testLogging = false;
	public static void incrementErrorCount() {
		errCount++ ;
	}
	public static boolean isDebugRun() {
		return MODE_DEBUG;
	}
	public static boolean isTestRun() {
		return RUNNING_TESTS;
	}
	public static Logger logger() {
		return LOGGER;
	}
	public static void main(String[] args) {
		RUNNING_TESTS = true;
		init(args);
		header();
		runTests();
		footer();
	}
	private static void footer() {
		System.out.println("\n\nZeroCore V" + VERSION + " Self Test complete - " + errCount + "/" + testCount + " tests failed.");
		if (errCount > 0) {
			System.err.println("Please copy this error log and send it to Zero Point at the following email address:");
			System.err.println("zeropointmcdev@gmail.com");
		}
		else {
			System.out.println("This copy of ZeroCore appears to working properly.");
		}
	}
	private static void header() {
		System.out.println("ZeroCore V" + VERSION + " Self Test\n\n");
		if (testLogging) {
			LOGGER.severe("Testing ZeroCore severe level message logging");
			LOGGER.warning("Testing ZeroCore warning level message logging");
			LOGGER.info("Testing ZeroCore info level message logging");
			LOGGER.config("Testing ZeroCore config level message logging");
			LOGGER.fine("Testing ZeroCore fine level message logging");
			LOGGER.finer("Testing ZeroCore finer level message logging");
			LOGGER.finest("Testing ZeroCore finest level message logging");
		}
	}
	private static void init(String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++ ) {
				if (args[i].equals("-f")) {
					handlers.add(new LoggingFileHandler("./ZeroCore.%u.log"));
				}
				else if (args[i].equals("-c")) {
					handlers.add(new LoggingConsoleHandler());
				}
				else if (args[i].equals("-t")) {
					testLogging = true;
				}
				else if (args[i].equals("-d")) {
					MODE_DEBUG = true;
				}
			}
		}
		LOGGER = LoggerFactory.create(handlers.toArray(new Handler[] {}), Level.ALL);
		try {
			LOGGER.setLevel(Level.ALL);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			new ZeroCoreFileBase(configFile).create();
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to create config file '" + configFile + "'", e);
			System.exit(1);
		}
		try {
			new ZeroCoreFileBase(configFile).deleteOnExit();
		}
		catch (Exception e) {}
	}
	private static void runTests() {
		// Be sure to set the int 'testCount' to the number of tests run here!
		test_Shell();
		test_Datetime();
		test_hash();
		test_OutputFile();
		test_InputFile();
		test_ConfigFile_write();
		test_ConfigFile_read();
		test_Localization();
		test_StringUtil_reverse();
		test_StringUtil_boolParse();
		test_StringUtil_split();
		test_StackTrace_trace();
		test_LockableProperties_clone();
		test_Localization_clone();
		try {
			ZeroCore.explode();
		}
		catch (Error e) {
			LOGGER.log(Level.INFO, "Successfully exploded!", e);
		}
	}
	private static void test_Localization_clone() {
		try {
			LockableProperties props = new LockableProperties();
			props.setProperty("section.key", "value");
			Localization loc = new Localization(props);
			loc.lock();
			Localization local = loc.clone();
			if (local.locked() || !local.localize("{section.key}").equals("value")) {
				throw new CloneNotSupportedException("Clone " + (local.locked() ? "" : "un") + "locked, properties " + (local.localize("{section.key}").equals("value") ? "" : "not ") + "copied");
			}
			LOGGER.info("Successfully cloned Localization object");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Could not clone Localization", e);
		}
	}
	private static void test_LockableProperties_clone() {
		try {
			LockableProperties prop = new LockableProperties();
			prop.setProperty("test", "value");
			prop.lock();
			LockableProperties cfg = prop.clone();
			if ( !cfg.locked() && cfg.getProperty("test", "").equals("value")) {
				LOGGER.info("Successfully cloned LockableProperties object");
			}
			else {
				throw new CloneNotSupportedException("Clone " + (cfg.locked() ? "" : "un") + "locked, properties " + (cfg.getProperty("test", "").equals("value") ? "" : "not ") + "copied");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Could not clone LockableProperties", e);
		}
	}
	private static void test_ConfigFile_read() {
		ConfigFile config = new ConfigFile(configFile);
		try {
			String val = config.get("core.version");
			if (val.equals(VERSION)) {
				LOGGER.info("Read back from config file, contents match expected text");
			}
			else {
				LOGGER.warning("Read back from config file, got unexpected result");
				errCount++ ;
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to retrieve key 'core.version' from config file, listing all keys", e);
			Iterator<String> iter = config.keys().iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
			errCount++ ;
		}
	}
	private static void test_ConfigFile_write() {
		ConfigFile config = new ConfigFile(configFile);
		config.set("core.version", VERSION);
		try {
			config.save();
			LOGGER.info("Wrote to config file '" + configFile + "'");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to save config file '" + configFile + "'", e);
			errCount++ ;
		}
		config.unloadProps();
		config = null;
	}
	private static void test_Datetime() {
		try {
			Datetime now = new Datetime();
			LOGGER.info("Current date/time in ISO format: " + now.isoDatetime());
		}
		catch (Exception e) {
			errCount++ ;
		}
	}
	private static void test_hash() {
		try {
			String expect = "0363f1a98b0c507a237977267358886724194e58198ae5efc58c659238ee8fc3";
			String result = SHA.hex256(toHash);
			if (result.equalsIgnoreCase(expect)) {
				LOGGER.info("SHA256 hashing of '" + toHash + "' gives expected result of '" + result + "'");
			}
			else {
				LOGGER.warning("SHA256 hashing of '" + toHash + "' gives unexpected result of '" + result + "'!");
				errCount++ ;
			}
		}
		catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.WARNING, "Unable to calculate SHA256 hash", e);
			errCount++ ;
		}
	}
	private static void test_InputFile() {
		InputFile in = new InputFile(testFile);
		try {
			String text = in.readAll();
			if (text.equals(testText)) {
				LOGGER.info("Successfully read expected string from file '" + testFile + "' ('" + testText + "')");
			}
			else {
				LOGGER.warning("Contents of " + testFile + " do not match expected data! Wanted '" + testText + "', got '" + text + "' instead!");
				errCount++ ;
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to read contents of " + testFile, e);
			errCount++ ;
		}
	}
	private static void test_Localization() {
		try {
			String base = "{core.version}";
			Localization local = new Localization(new ConfigFile(configFile));
			if (local.localize(base).equals("[null]")) {
				LOGGER.warning("Unable to properly localize string '{core.version}' using Localization");
				errCount++ ;
			}
			else {
				LOGGER.info("Successfully localized string '{core.version}' to '" + VERSION + "' using JLocalization");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to localize string using Localization", e);
			errCount++ ;
		}
	}
	private static void test_OutputFile() {
		try {
			OutputFile out = new OutputFile(testFile);
			out.deleteOnExit();
			out.print(testText);
			out.close();
			LOGGER.info("Created/wrote to file '" + testFile + "' with string '" + testText + "'");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to create/write to '" + testFile + "'", e);
			errCount++ ;
		}
	}
	private static void test_Shell() {
		try {
			LOGGER.fine("Constructing new Shell");
			Shell sh = new SingleParserShell(commandStream).setLogger(LOGGER);
			LOGGER.fine("Registering appropriate parser");
			sh.registerParser(new ShellTestParser());
			LOGGER.fine("Running shell's main loop");
			sh.run();
			LOGGER.info("Shell test complete");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Shell failed to parse commands", e);
		}
	}
	private static void test_StackTrace_trace() {
		try {
			LOGGER.info("Generating stack trace");
			StackTraceElement[] trace = StackTrace.trace();
			LOGGER.info("Printing stack trace");
			for (StackTraceElement elem : trace) {
				LOGGER.info(elem.toString());
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to generate stack trace", e);
		}
	}
	private static void test_StringUtil_boolParse() {
		try {
			for (String c : StringUtil.boolFalse) {
				if ( !StringUtil.isFalse(c)) {
					LOGGER.warning("String '" + c + "' not parsed as boolean false!");
					errCount++ ;
					return;
				}
			}
			for (String c : StringUtil.boolTrue) {
				if ( !StringUtil.isTrue(c)) {
					LOGGER.warning("String '" + c + "' not parsed as boolean true!");
					errCount++ ;
					return;
				}
			}
			LOGGER.info("Successfully parsed booleans from strings using StringUtil");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to parse booleans from strings using StringUtil", e);
		}
	}
	private static void test_StringUtil_reverse() {
		try {
			if (StringUtil.reverse(preReverse).equals(postReverse) && StringUtil.reverse(StringUtil.reverse(preReverse)).equals(preReverse)) {
				LOGGER.info("Successfully reversed strings using StringUtil");
			}
			else {
				LOGGER.warning("Failed to reverse strings using StringUtil");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to reverse strings using StringUtil", e);
		}
	}
	private static void test_StringUtil_split() {
		try {
			String[] ret = StringUtil.split("Testing\\; testing;failed", ";");
			if (ret[0].equals("Testing\\; testing") && ret[1].equals("failed")) {
				LOGGER.info("StringUtil.split(String, String) split string with escaped delimiter");
			}
			else {
				errCount++ ;
				LOGGER.warning("StringUtil.split(String, String) failed to split string with escaped delimiter");
				if (MODE_DEBUG) {
					for (int i = 0; i < ret.length; i++ ) {
						LOGGER.fine((i + 1) + ": " + ret[i]);
					}
				}
			}
		}
		catch (Exception e) {
			errCount++ ;
			LOGGER.log(Level.WARNING, "Unable to split string using StringUtil", e);
		}
	}
}
