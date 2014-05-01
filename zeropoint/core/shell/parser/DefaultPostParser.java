package zeropoint.core.shell.parser;


import zeropoint.core.shell.Shell;


/**
 * DefaultPostParser is the postparser that is automatically used when a {@link Shell} object does not a different one set.
 * 
 * @author Zero Point
 */
public class DefaultPostParser implements ICommandParser {
	@Override
	public String name() {
		return "DefaultPostParser";
	}
	@Override
	public boolean canParse(String command) {
		return true;
	}
	@Override
	public void parse(String command, Shell shell) {
		command = command.toLowerCase();
		if (command.equals("quit") || command.equals("exit")) {
			shell.terminate();
		}
	}
}
