package zeropoint.core.shell;

/**
 * Used to indicate the type of shell: single-parser,
 * multi-parser, or unknown
 * 
 * @see SingleParserShell
 * @see MultiParserShell
 * @author Zero Point
 */
public enum ShellType {
	/**
	 * Indicates that a shell can only have one parser, and registration will overwrite the existing parser
	 */
	SINGLEPARSER,
	/**
	 * Indicates that a shell can have an infinite number of parsers, and registration will add more to the internal list
	 */
	MULTIPARSER,
	/**
	 * Indicates that the shell in question was not properly written, and the coder should be yelled at
	 */
	UNKNOWN
}
