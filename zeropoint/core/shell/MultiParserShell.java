package zeropoint.core.shell;


import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import zeropoint.core.exception.InvalidSyntaxException;
import zeropoint.core.shell.parser.ICommandParser;


/**
 * A MultiParserShell is just a {@linkplain Shell} that can use several parsers. Parsers are tried in order of registration.
 * 
 * @author Zero Point
 */
public class MultiParserShell extends Shell {
	/**
	 * All registered parsers for this MultiParserShell object.
	 */
	protected List<ICommandParser> parserList = new ArrayList<ICommandParser>();
	/**
	 * Construct a new MultiParserShell object, using the default InputStream.
	 */
	public MultiParserShell() {
		super();
	}
	/**
	 * Construct a new MultiParserShell object, using the given InputStream.
	 * 
	 * @param init
	 *            - the InputStream to read commands from
	 */
	public MultiParserShell(InputStream init) {
		super(init);
	}
	/**
	 * Construct a new MultiParserShell object, using the given Reader
	 * 
	 * @param init
	 *            - the Reader to read commands from
	 */
	public MultiParserShell(Reader init) {
		super(init);
	}
	/**
	 * Add a new parser to the list of registered parsers
	 */
	@Override
	public Shell registerParser(ICommandParser newParser) {
		parserList.add(newParser);
		return this;
	}
	/**
	 * Remove a given parser from the list of registered parsers
	 */
	@Override
	public Shell unregisterParser(ICommandParser parserToRemove) {
		parserList.remove(parserToRemove);
		return this;
	}
	/**
	 * Check to see if any of the registered parsers can
	 * handle the given command
	 */
	@Override
	protected boolean verifyCommand(String command) {
		for (ICommandParser testParser : parserList) {
			if (testParser.canParse(command)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Find the first registered parser than can handle
	 * the given command, and use it
	 */
	@Override
	protected void processCommand(String command) throws InvalidSyntaxException {
		for (ICommandParser testParser : parserList) {
			if (testParser.canParse(command)) {
				testParser.parse(command, this);
				return;
			}
		}
		throw new InvalidSyntaxException();
	}
}
