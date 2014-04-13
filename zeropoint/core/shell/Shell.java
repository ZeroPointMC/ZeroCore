package zeropoint.core.shell;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import zeropoint.core.Test;
import zeropoint.core.exception.InvalidSyntaxException;
import zeropoint.core.logger.LoggerFactory;
import zeropoint.core.logger.handler.LoggingConsoleHandler;
import zeropoint.core.shell.parser.DefaultPostParser;
import zeropoint.core.shell.parser.DefaultPreParser;
import zeropoint.core.shell.parser.ICommandParser;
import zeropoint.core.shell.parser.ICommandPreParser;


/**
 * A Shell object is designed to read commands from a text stream
 * and send them to handlers. Multiple parsers (handlers) can be
 * registered. There can also be a preparser, which can change
 * the command before it reaches the regular parsers. After trying
 * to parse a command, <i>regardless of success</i>, the command
 * will be sent to a postparser. You can write your own pre/plain/post
 * parser by implementing {@link ICommandPreParser} for preparsing
 * or {@link ICommandParser} for plain/post parsing.
 * 
 * @author Zero Point
 */
public abstract class Shell implements Runnable {
	/**
	 * Set in source code to true to enable verbose test
	 * logging output.
	 */
	public static final boolean allowTestLogging = false;
	/**
	 * Flag indication whether we are in a test run AND should
	 * output verbose logs.
	 */
	public static final boolean doTestLogging = Test.isTestRun() && Test.isDebugRun() && allowTestLogging;
	/**
	 * The maximum number of IOExceptions that can be
	 * detected during the run() method before aborting;
	 */
	public static final int maxErrCount = 3;
	/**
	 * The default InputStream, used when constructing
	 * a new Shell object without specifying an InputStream
	 * to read commands from.
	 */
	public static final InputStream DEFAULT_INPUTSTREAM = System.in;
	/**
	 * The default Logger used by new Shell objects.
	 */
	protected static final Logger DEFAULT_LOGGER = LoggerFactory.create(new LoggingConsoleHandler());
	/**
	 * The number of IOExceptions detected so far during
	 * the run() method's main loop.
	 */
	protected int errCount = 0;
	/**
	 * The current Logger for a Shell object.
	 */
	protected Logger LOGGER = DEFAULT_LOGGER;
	/**
	 * The postparser, called after normal parsing.
	 */
	protected ICommandParser postParser = new DefaultPostParser();
	/**
	 * The preparser, called before normal parsing.
	 */
	protected ICommandPreParser preParser = new DefaultPreParser();
	/**
	 * Termination flag, aborts processing if true.
	 */
	protected boolean shouldTerminate = false;
	/**
	 * The command stack.
	 */
	protected ArrayList<String> stack = new ArrayList<String>();
	/**
	 * The stream from which commands are read.
	 */
	private LineNumberReader commandReader;
	/**
	 * Construct a new Shell object, using the default InputStream.
	 */
	public Shell() {
		setInput(DEFAULT_INPUTSTREAM);
	}
	/**
	 * Construct a new Shell object, using the given InputStream.
	 * 
	 * @param init
	 *            - the InputStream to read commands from
	 */
	public Shell(InputStream init) {
		setInput(init);
	}
	/**
	 * Construct a new Shell object, using the given Reader
	 * 
	 * @param init
	 *            - the Reader to read commands from
	 */
	public Shell(Reader init) {
		setInput(init);
	}
	/**
	 * Adds a string to the bottom of the command stack,
	 * where it will be processed last.
	 * 
	 * @param cmd
	 *            - the string to add to the stack
	 * @return The current Shell object
	 */
	public Shell addCommandToBottomOfStack(String cmd) {
		stack.add(cmd);
		return this;
	}
	/**
	 * Adds a string to the top of the command stack,
	 * where it will be processed next.
	 * 
	 * @param cmd
	 *            - the string to add to the stack
	 * @return The current Shell object
	 */
	public Shell addCommandToStack(String cmd) {
		stack.add(0, cmd);
		return this;
	}
	/**
	 * Get the logger that the Shell object is using
	 * 
	 * @return The current Logger
	 */
	public Logger getLogger() {
		return LOGGER;
	}
	/**
	 * Get the current postparser (called after normal
	 * parsing, regardless of success)
	 * 
	 * @return The current postparser
	 */
	public ICommandParser getPostParser() {
		return postParser;
	}
	/**
	 * Get the current preparser (called before normal
	 * parsing, can change command or abort parsing)
	 * 
	 * @return The current preparser
	 */
	public ICommandPreParser getPreParser() {
		return preParser;
	}
	/**
	 * Register a parser object. Subclasses must provide an
	 * implementation of this method.
	 * 
	 * @param newParser
	 *            - the parser object that is being registered
	 * @return The current Shell object
	 */
	public abstract Shell registerParser(ICommandParser newParser);
	/**
	 * Register a postparser object. A postparser will be
	 * called after normal parsing, regardless of success.
	 * 
	 * @param newPostparser
	 *            - the postparser that is being registered
	 */
	public void registerPostParser(ICommandParser newPostparser) {
		postParser = newPostparser;
	}
	/**
	 * Register a preparser object. A preparser will be called
	 * before normal parsing, and can change the command to be
	 * parsed or abort parsing the current command.
	 * 
	 * @param newPreParser
	 *            - the preparser that is being registered
	 */
	public void registerPreParser(ICommandPreParser newPreParser) {
		preParser = newPreParser;
	}
	/**
	 * Runs the Shell. This consists of processing the
	 * command stack and pushing the next command from
	 * the stream onto the stack, until the end of the
	 * command stream is reached.
	 * 
	 * @throws InvalidSyntaxException
	 *             if the command cannot be parsed
	 */
	public void run() throws InvalidSyntaxException {
		commandReader.setLineNumber(1);
		shouldTerminate = false;
		while (true) {
			if (Thread.interrupted()) {
				return;
			}
			if (processCommandStack()) {
				return;
			}
			if (Thread.interrupted()) {
				return;
			}
			try {
				String nextCom = commandReader.readLine();
				if (nextCom == null) {
					break;
				}
				if (doTestLogging) {
					LOGGER.finest("Adding command to stack: " + nextCom);
				}
				addCommandToBottomOfStack(nextCom);
			}
			catch (ClosedByInterruptException e) {
				LOGGER.log(Level.SEVERE, "Shell thread interrupted", e);
				return;
			}
			catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Unable to get next command line", e);
				errCount++ ;
				if (errCount > maxErrCount) {
					if (Test.isTestRun()) {
						Test.incrementErrorCount();
					}
					return;
				}
				Thread.yield();
			}
		}
	}
	/**
	 * Change the logger used by this Shell.
	 * 
	 * @param newLogger
	 *            - the new Logger object to use
	 * @return The current Shell object
	 */
	public Shell setLogger(Logger newLogger) {
		if (newLogger != null) {
			LOGGER = newLogger;
		}
		return this;
	}
	/**
	 * Called to indicate to the shell that it should abort
	 * <i>all</i> command processing. Enables the use
	 * of <tt>exit</tt> or <tt>quit</tt> type commands.
	 */
	public void terminate() {
		shouldTerminate = true;
	}
	/**
	 * Unregister a parser object. Subclasses must provide
	 * their own implementation.
	 * 
	 * @param parserToRemove
	 *            - the parser object to be unregistered
	 * @return The current Shell object
	 */
	public abstract Shell unregisterParser(ICommandParser parserToRemove);
	/**
	 * Unregister a postparser object. Sets the postparser to
	 * a {@link DefaultPostParser} object constructed with no
	 * parameters.
	 * 
	 * @return The current Shell object
	 */
	public Shell unregisterPostParser() {
		postParser = new DefaultPostParser();
		return this;
	}
	/**
	 * Unregister a preparser object. Sets the preparser to
	 * a {@link DefaultPreParser} object constructed with no
	 * parameters.
	 * 
	 * @return The current Shell object
	 */
	public Shell unregisterPreParser() {
		preParser = new DefaultPreParser();
		return this;
	}
	/**
	 * Check whether or not <i>all</i> of the given commands
	 * can be parsed without actually parsing them.
	 * 
	 * @param commands
	 *            - the commands to check
	 * @return <tt>true</tt> if all of the given commands
	 *         can be parsed, <tt>false</tt> otherwise
	 */
	public boolean verifyAll(String... commands) {
		for (String cmd : commands) {
			if ( !verifyCommand(cmd)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Check whether or not <i>any</i> of the given commands
	 * can be parsed without actually parsing them.
	 * 
	 * @param commands
	 *            - the commands to check
	 * @return <tt>true</tt> if any of the given commands
	 *         can be parsed, <tt>false</tt> otherwise
	 */
	public boolean verifyAny(String... commands) {
		for (String cmd : commands) {
			if (verifyCommand(cmd)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Retrieves the LineNumberReader used to read the commands.
	 * 
	 * @deprecated
	 * @return The LineNumberReader from which commands are read.
	 */
	@Deprecated
	protected final LineNumberReader getCommandReader() {
		return commandReader;
	}
	/**
	 * Process a single command using <b>only</b> the normal
	 * parser(s). Subclasses must provide their own implementation.
	 * 
	 * @param commandToProcess
	 *            - the command to be parsed
	 * @throws InvalidSyntaxException
	 *             if the command cannot be parsed
	 */
	protected abstract void processCommand(String commandToProcess) throws InvalidSyntaxException;
	/**
	 * Set the command stream to the default InputStream
	 * 
	 * @deprecated
	 */
	@Deprecated
	protected final void setInput() {
		setInput(DEFAULT_INPUTSTREAM);
	}
	/**
	 * Set the command stream to the given InputStream
	 * 
	 * @param stream
	 *            - the {@link InputStream} to read commands from
	 */
	protected final void setInput(InputStream stream) {
		setInput(new InputStreamReader(stream));
	}
	/**
	 * Set the command stream to the given Reader
	 * 
	 * @param reader
	 *            - the {@link Reader} to read commands from
	 */
	protected final void setInput(Reader reader) {
		commandReader = new LineNumberReader(reader);
	}
	/**
	 * Check whether or not a single command can be parsed.
	 * Subclasses must provide their own implementation.
	 * 
	 * @param command
	 *            - the command to check
	 * @return <tt>true</tt> if the given command can be parsed,
	 *         <tt>false</tt> otherwise
	 */
	protected abstract boolean verifyCommand(String command);
	/**
	 * Processes the entire command stack. This consists of getting
	 * next command to parse, removing it from the stack, running
	 * it through the preparser (if present), calling the
	 * processCommand(String) method, and running it through the
	 * postparser (if present), until the command stack is empty.
	 * 
	 * @return <tt>true</tt> if the terminate flag is set,
	 *         <tt>false</tt> otherwise (reliance upon the
	 *         return value is deprecated, check the terminate flag
	 *         instead)
	 * @throws InvalidSyntaxException
	 *             if a command cannot be parsed
	 */
	protected boolean processCommandStack() throws InvalidSyntaxException {
		if (doTestLogging) {
			LOGGER.finest("Processing command stack");
		}
		Iterator<String> iter = stack.iterator();
		MAINLOOP: while (true) {
			if (Thread.interrupted() || !iter.hasNext()) {
				break MAINLOOP;
			}
			String line = iter.next();
			iter.remove();
			if (line == null) {
				continue MAINLOOP;
			}
			line = line.trim();
			if ((preParser != null) && preParser.canParse(line)) {
				if (doTestLogging) {
					LOGGER.finest("Preparsing command: " + line);
				}
				line = preParser.parse(line, this);
				if (shouldTerminate) {
					if (doTestLogging) {
						LOGGER.finest("Aborting, preparser set exit flag");
					}
					return true;
				}
				if (line == null) {
					if (doTestLogging) {
						LOGGER.finest("Aborting, preparser returned null");
					}
					continue MAINLOOP;
				}
				if (doTestLogging) {
					LOGGER.finest("New command: " + line);
				}
				line = line.trim();
			}
			else if ((preParser == null) && doTestLogging) {
				LOGGER.finest("No preparser found");
			}
			else if (doTestLogging) {
				LOGGER.finest("Preparser ignored command: " + line);
			}
			if (doTestLogging) {
				LOGGER.finest("Processing command: " + line);
			}
			processCommand(line);
			if ((postParser != null) && postParser.canParse(line)) {
				postParser.parse(line, this);
			}
			if (shouldTerminate) {
				return true;
			}
			else if ((postParser == null) && doTestLogging) {
				LOGGER.finest("No postparser found");
			}
			else if ((postParser != null) && doTestLogging) {
				LOGGER.finest("Post parser ignored command");
			}
		}
		return false;
	}
	/**
	 * Gets the type of parser. If you subclass Shell instead of {@link SingleParserShell} or {@link MultiParserShell}, this
	 * method will return ShellType.UNKNOWN
	 * 
	 * @return A value from the {@link ShellType} enum indicating
	 *         whether this Shell uses more than one parser.
	 */
	public final ShellType getShellType() {
		if (this instanceof SingleParserShell) {
			return ShellType.SINGLEPARSER;
		}
		if (this instanceof MultiParserShell) {
			return ShellType.MULTIPARSER;
		}
		return ShellType.UNKNOWN;
	}
	/**
	 * Convenience method for determining if this shell uses ONLY
	 * one parser.
	 * 
	 * @return <tt>true</tt> if this Shell object
	 *         subclasses {@linkplain SingleParserShell},
	 *         <tt>false</tt> otherwise.
	 */
	public final boolean isSingleParserShell() {
		return getShellType().equals(ShellType.SINGLEPARSER);
	}
	/**
	 * Convenience method for determining if this shell uses more
	 * than one parser.
	 * 
	 * @return <tt>true</tt> if this Shell object
	 *         subclasses {@linkplain MultiParserShell},
	 *         <tt>false</tt> otherwise.
	 */
	public final boolean isMultiParserShell() {
		return getShellType().equals(ShellType.MULTIPARSER);
	}
	/**
	 * Convenience method for determining if this shell is an
	 * unknown type.
	 * 
	 * @return <tt>true</tt> if this Shell object
	 *         subclasses Shell directly,
	 *         <tt>false</tt> otherwise.
	 */
	public final boolean isUnknownShellType() {
		return getShellType().equals(ShellType.UNKNOWN);
	}
}
