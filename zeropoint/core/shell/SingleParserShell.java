package zeropoint.core.shell;


import java.io.InputStream;
import java.io.Reader;

import zeropoint.core.exception.InvalidSyntaxException;
import zeropoint.core.shell.parser.ICommandParser;


/**
 * A SingleParserShell is a {@linkplain Shell} that can only ever have one parser registered at a time, not counting pre and post parsers.
 * 
 * @author Zero Point
 */
public class SingleParserShell extends Shell {
	/**
	 * The parser that will be used to handle commands given to
	 * this Shell.
	 */
	protected ICommandParser parser;
	/**
	 * Construct a new SingleParserShell object, using the default InputStream.
	 */
	public SingleParserShell() {
		super();
	}
	/**
	 * Construct a new SingleParserShell object, using the given InputStream.
	 * 
	 * @param init
	 *            - the InputStream to read commands from
	 */
	public SingleParserShell(InputStream init) {
		super(init);
	}
	/**
	 * Construct a new SingleParserShell object, using the given Reader
	 * 
	 * @param init
	 *            - the Reader to read commands from
	 */
	public SingleParserShell(Reader init) {
		super(init);
	}
	/**
	 * Register the parser to be used by this Shell
	 */
	@Override
	public Shell registerParser(ICommandParser newParser) {
		this.parser = newParser;
		return this;
	}
	/**
	 * Unregister the parser used by this shell.
	 * 
	 * @param newParser
	 *            - ignored
	 */
	@Override
	public Shell unregisterParser(ICommandParser newParser) {
		this.parser = null;
		return this;
	}
	/**
	 * Check to see if there is a parser and it can
	 * handle the given command.
	 */
	@Override
	protected boolean verifyCommand(String command) {
		return (this.parser != null) && this.parser.canParse(command);
	}
	/**
	 * Handle the given command.
	 */
	@Override
	protected void processCommand(String com) throws InvalidSyntaxException {
		if (verifyCommand(com)) {
			this.parser.parse(com, this);
		}
		else {
			throw new InvalidSyntaxException();
		}
	}
}
