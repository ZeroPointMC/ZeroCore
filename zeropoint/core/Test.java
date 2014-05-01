package zeropoint.core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import zeropoint.core.config.ConfigFile;
import zeropoint.core.config.Localization;
import zeropoint.core.config.LockableProperties;
import zeropoint.core.date.Datetime;
import zeropoint.core.io.IOStream;
import zeropoint.core.io.file.FileBase;
import zeropoint.core.io.file.InputFile;
import zeropoint.core.io.file.OutputFile;
import zeropoint.core.logger.LoggerConfig;
import zeropoint.core.logger.LoggingFormatter;
import zeropoint.core.shell.Shell;
import zeropoint.core.shell.SingleParserShell;
import zeropoint.core.shell.parser.ShellTestParser;
import zeropoint.core.string.StringUtil;
import zeropoint.core.string.hash.SHA;
import zeropoint.core.struct.Wheel;
import zeropoint.core.struct.WheelIterator;
import zeropoint.core.xml.XMLTag;


/**
 * ZeroCore's self-test class. Includes a test for all functions in ZeroCore.
 * 
 * @author Zero Point
 */
@SuppressWarnings("javadoc")
public final class Test {
	private static final String email = "zeropointmcdev@gmail.com";
	public static final Version VERSION = new Version("1.0.0");
	public static final Reader commandStream = new StringReader("run test\nexit");
	public static final String configFile = "./ZeroCore.conf";
	public static final String line1 = "This is\\\na test.";
	public static final String line2 = "It was a\\\nsuccess.";
	public static final String multilineFile = "./ZeroCore.dat";
	public static final String postReverse = "tnemirepxe";
	public static final String preReverse = "experiment";
	public static final int testCount = 20;
	public static final String testFile = "./ZeroCore.tmp";
	public static final String testText = "Success";
	public static final String toHash = "pony";
	private static Logger LOGGER;
	private static int errCount = 0;
	private static int warnCount = 0;
	private static ArrayList<Handler> handlers = new ArrayList<Handler>();
	private static boolean MODE_DEBUG = false;
	private static boolean RUNNING_TESTS = false;
	private static boolean testLogging = false;
	private static final void warn(String msg) {
		warn(msg, null);
	}
	private static final void warn(String msg, Throwable e) {
		LOGGER.log(Level.WARNING, msg, e);
		warnCount++ ;
	}
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
		System.out.println("\nZeroCore V" + VERSION + " Self Test complete");
		System.out.println(errCount + "/" + testCount + " tests failed - " + warnCount + " warning" + (warnCount != 1 ? "s" : ""));
		if (errCount > 0) {
			System.err.println("Please copy this error log and send it to Zero Point at the following email address:");
		}
		else if (warnCount > 0) {
			System.err.println("While nothing is broken, there are some things that should be checked.");
			System.err.println("Please copy this log and send it to Zero Point at the following email address:");
		}
		else {
			System.out.println("This copy of ZeroCore appears to working properly.\n");
			System.out.println("Have a nice day!");
		}
		if ((errCount + warnCount) > 0) {
			System.err.println(email);
		}
	}
	private static void header() {
		System.out.println("ZeroCore V" + VERSION + " Self Test\n");
		if (testLogging) {
			LOGGER.severe("Testing ZeroCore severe level message logging");
			LOGGER.severe("Testing ZeroCore warning level message logging");
			LOGGER.info("Testing ZeroCore info level message logging");
			LOGGER.config("Testing ZeroCore config level message logging");
			LOGGER.fine("Testing ZeroCore fine level message logging");
			LOGGER.finer("Testing ZeroCore finer level message logging");
			LOGGER.finest("Testing ZeroCore finest level message logging");
			System.err.println("");
		}
	}
	private static void init(String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++ ) {
				if (args[i].equals("-f")) {
					Handler handler = null;
					try {
						handler = new FileHandler("./ZeroCore.%u.log");
					}
					catch (Exception e) {
						e.printStackTrace();
						ZeroCore.explode(e);
						return;
					}
					handler.setFormatter(new LoggingFormatter(LoggingFormatter.FLAG_TIME_FULL));
					handlers.add(handler);
				}
				else if (args[i].equals("-c")) {
					Handler handler = new ConsoleHandler();
					handler.setFormatter(new LoggingFormatter(LoggingFormatter.FLAG_TIME_FULL));
					handlers.add(handler);
				}
				else if (args[i].equals("-t")) {
					testLogging = true;
				}
				else if (args[i].equals("-d")) {
					MODE_DEBUG = true;
				}
			}
		}
		LOGGER = LoggerConfig.config(Logger.getLogger("ZeroCore"), 0, new LoggingFormatter(), Level.ALL, handlers.toArray(new Handler[] {}));
		try {
			new FileBase(configFile).create();
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to create config file '" + configFile + "'", e);
			System.exit(1);
		}
		try {
			new FileBase(configFile).deleteOnExit();
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
		test_StackTrace();
		test_LockableProperties_clone();
		test_Localization_clone();
		test_Version();
		test_Wheel();
		test_WheelIterator();
		test_IOStream();
		test_XML();
		try {
			ZeroCore.explode();
			LOGGER.severe("Failed to trigger error!");
			errCount++ ;
		}
		catch (Error e) {
			LOGGER.info("Successfully exploded!");
		}
	}
	private static void test_XML() {
		XMLTag tag = new XMLTag("tag");
		tag.attr("key", "val");
		String res = tag.toString("test");
		if (res.equals("<tag key=\"val\">test</tag>")) {
			LOGGER.info("Dynamic XMLTag stringification is functional (" + res + ")");
		}
		else {
			errCount++ ;
			LOGGER.severe("XMLTag failed to stringify properly: " + res);
			return;
		}
		XMLTag inner = new XMLTag("inner");
		inner.attr("level", "1");
		String xml = tag.toString(inner);
		if (xml.equals("<tag key=\"val\"><inner level=\"1\" /></tag>")) {
			LOGGER.info("Successfully stringified nested XMLTag objcts in dynamic mode (" + xml + ")");
		}
		else {
			errCount++ ;
			LOGGER.severe("Nested XMLTags failed to stringify in dynamic mode : " + xml);
			return;
		}
		inner.cdata("testing");
		inner.addInner(new XMLTag("one"));
		XMLTag sub = new XMLTag("branch");
		sub.attr("test", "test");
		tag.addInner(sub);
		tag.addInner(inner);
		inner.addInner(sub);
		if (tag.toString().equals("<tag key=\"val\"><branch test=\"test\" /><inner level=\"1\">testing<one /><branch test=\"test\" /></inner></tag>")) {
			LOGGER.info("Static XMLTag stringification successful");
		}
		else {
			errCount++ ;
			LOGGER.severe("Static XMLTag stringification failed: " + tag.toString());
			return;
		}
	}
	private static void test_IOStream() {
		IOStream io = new IOStream();
		PrintStream out = new PrintStream(io.getWriteStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(io.getReadStream()));
		out.println("line 1\nline 2");
		out.print("line 3");
		try {
			if (in.readLine().equals("line 1")) {
				if (in.readLine().equals("line 2")) {
					if (in.readLine().equals("line 3")) {
						try {
							String line = in.readLine();
							if (line == null) {
								throw new IOException();
							}
							errCount++ ;
							LOGGER.severe("IOStream malfunctioned! (Missing exception where expected, line " + line + ")");
							return;
						}
						catch (IOException e) {
							LOGGER.info("IOStream is working");
							return;
						}
					}
				}
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "IOStream malfunctioned!", e);
			errCount++ ;
		}
	}
	private static void test_WheelIterator() {
		Wheel<Integer> wheel = new Wheel<Integer>();
		wheel.addAll(new Integer[] {
			1,
			2,
			3,
			4
		});
		WheelIterator<Integer> iter = (WheelIterator<Integer>) wheel.iterator();
		if (iter.next() == 1) {
			if (iter.next() == 2) {
				if (iter.next() == 3) {
					if (iter.next() == 4) {
						if ( !iter.hasNext()) {
							LOGGER.info("WheelIterator is functional!");
							return;
						}
					}
				}
			}
		}
		errCount++ ;
		LOGGER.severe("WheelIterator malfunctioned!");
		iter = (WheelIterator<Integer>) wheel.iterator();
		StringBuffer buf = new StringBuffer();
		buf.append(iter.next());
		while (iter.hasNext()) {
			buf.append(", " + iter.next());
		}
		LOGGER.fine(buf.toString());
	}
	private static void test_Wheel() {
		Wheel<Integer> wheel = new Wheel<Integer>();
		wheel.addAll(new Integer[] {
			1,
			2,
			3,
			4
		});
		if (wheel.read() == 1) {
			if (wheel.read() == 2) {
				if (wheel.read() == 3) {
					if (wheel.read() == 4) {
						if (wheel.read() == 1) {
							LOGGER.info("Wheel is functional!");
							return;
						}
					}
				}
			}
		}
		LOGGER.severe("Wheel malfunctioned!");
		wheel.reset();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 8; i++ ) {
			buf.append(wheel.read() + ", ");
		}
		buf.append(wheel.read());
		LOGGER.fine(buf.toString());
	}
	private static void test_Version() {
		Version a = new Version(1, 0, 0);
		Version b = new Version(1, 0, 1);
		Version c = new Version("1.0.0-alpha");
		Version d = new Version("1.0.0-rc1+001");
		Version e = new Version("1.0.0-rc1+002");
		Version f = new Version("1.0.3-rc2");
		Version g = new Version("1.0.3-rc4");
		Version h = new Version("1.0.3-1");
		Version i = new Version("1.0.3-2");
		Version j = new Version("1.0.3");
		Version[] vers = {
			a,
			b,
			c,
			d,
			e,
			f,
			g,
			h,
			i,
			j
		};
		Arrays.sort(vers);
		if (a.compareTo(b) != -1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + a + " should be below " + b + "!");
			return;
		}
		if (a.compareTo(c) != 1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + a + " should be above " + c + "!");
			return;
		}
		if (c.compareTo(d) != -1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + c + " should be below " + d + "!");
			return;
		}
		if (d.compareTo(e) != 0) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + d + " should equal " + e + "!");
			return;
		}
		if (f.compareTo(g) != -1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + f + " should be below " + g + "!");
			return;
		}
		if (h.compareTo(i) != -1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + h + " should be below " + i + "!");
			return;
		}
		if (g.compareTo(h) != 1) {
			errCount++ ;
			LOGGER.severe("Version comparison failed! " + g + " should be above " + h + "!");
			return;
		}
		LOGGER.info("Version comparisons succeeded");
		if (MODE_DEBUG) {
			LOGGER.fine(StringUtil.join(" < ", vers));
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
			LOGGER.log(Level.SEVERE, "Could not clone Localization", e);
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
			LOGGER.log(Level.SEVERE, "Could not clone LockableProperties", e);
		}
	}
	private static void test_ConfigFile_read() {
		ConfigFile config = new ConfigFile(configFile);
		try {
			String val = config.get("core.version");
			if (val.equals(VERSION.toString())) {
				LOGGER.info("Read back from config file, contents match expected text");
			}
			else {
				LOGGER.severe("Read back from config file, got unexpected result: " + val);
				errCount++ ;
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to retrieve key 'core.version' from config file, listing all keys", e);
			Iterator<String> iter = config.keys().iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
			errCount++ ;
		}
	}
	private static void test_ConfigFile_write() {
		ConfigFile config = new ConfigFile(configFile);
		config.set("core.version", VERSION.toString());
		try {
			config.save();
			LOGGER.info("Wrote to config file '" + configFile + "'");
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to save config file '" + configFile + "'", e);
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
				LOGGER.severe("SHA256 hashing of '" + toHash + "' gives unexpected result of '" + result + "'!");
				errCount++ ;
			}
		}
		catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "Unable to calculate SHA256 hash", e);
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
				LOGGER.severe("Contents of " + testFile + " do not match expected data! Wanted '" + testText + "', got '" + text + "' instead!");
				errCount++ ;
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to read contents of " + testFile, e);
			errCount++ ;
		}
	}
	private static void test_Localization() {
		try {
			String base = "{core.version}";
			Localization local = new Localization(new ConfigFile(configFile));
			if (local.localize(base).equals("[null]")) {
				LOGGER.severe("Unable to properly localize string '{core.version}' using Localization");
				errCount++ ;
			}
			else {
				LOGGER.info("Successfully localized string '{core.version}' to '" + VERSION + "' using Localization object");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to localize string using Localization", e);
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
			LOGGER.log(Level.SEVERE, "Unable to create/write to '" + testFile + "'", e);
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
			LOGGER.log(Level.SEVERE, "Shell failed to parse commands", e);
		}
	}
	private static void test_StackTrace() {
		try {
			StackTrace.trace();
			LOGGER.info("Successfully generated stack trace (at line " + (StackTrace.getLine() - 1) + ")");
			StackTrace trace = new StackTrace();
			if (trace.ownerClass().equals("zeropoint.core.Test")) {
				if (trace.ownerSimpleClass().equals("Test")) {
					if (trace.ownerMethod().equals("test_StackTrace")) {
						LOGGER.info("Successfully instantiated StackTrace");
						return;
					}
				}
			}
			throw new RuntimeException("Instantiated StackTrace malfunctioned!\n{\n\townerClass: " + trace.ownerClass() + "\n\townerSimpleClass: " + trace.ownerSimpleClass() + "\n\townerMethod: " + trace.ownerMethod() + "\n}");
		}
		catch (Exception e) {
			errCount++ ;
			LOGGER.log(Level.SEVERE, "StackTrace error", e);
		}
	}
	private static void test_StringUtil_boolParse() {
		try {
			for (String c : StringUtil.boolFalse) {
				if ( !StringUtil.isFalse(c)) {
					LOGGER.severe("String '" + c + "' not parsed as boolean false!");
					errCount++ ;
					return;
				}
			}
			for (String c : StringUtil.boolTrue) {
				if ( !StringUtil.isTrue(c)) {
					LOGGER.severe("String '" + c + "' not parsed as boolean true!");
					errCount++ ;
					return;
				}
			}
			LOGGER.info("Successfully parsed booleans from strings using StringUtil");
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to parse booleans from strings using StringUtil", e);
		}
	}
	private static void test_StringUtil_reverse() {
		try {
			if (StringUtil.reverse(preReverse).equals(postReverse) && StringUtil.reverse(StringUtil.reverse(preReverse)).equals(preReverse)) {
				LOGGER.info("Successfully reversed strings using StringUtil");
			}
			else {
				LOGGER.severe("Failed to reverse strings using StringUtil");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to reverse strings using StringUtil", e);
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
				LOGGER.severe("StringUtil.split(String, String) failed to split string with escaped delimiter");
				if (MODE_DEBUG) {
					for (int i = 0; i < ret.length; i++ ) {
						LOGGER.fine((i + 1) + ": " + ret[i]);
					}
				}
			}
		}
		catch (Exception e) {
			errCount++ ;
			LOGGER.log(Level.SEVERE, "Unable to split string using StringUtil", e);
		}
	}
}
