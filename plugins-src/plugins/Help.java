package plugins;

import java.io.PrintWriter;

import hooverville.commands.plugins.Command;
import hooverville.commands.util.CommandController;
import hooverville.commands.util.CommandHandler;
import hooverville.server.User;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author nick
 * 
 */
public class Help implements Command {

	private final static String FOLLOWING_COMMANDS = "You may use the following commands: ";
	private final static String HELP_SYNTAX = "Type help <command> to get help for a given command.";
	private final static String COMMAND_TEXT = "Command: ";
	private final static String COMMAND_NOT_FOUND = "help does not recognize that command";

	@Override
	public String getCommand() {
		return "help";
	}

	@Override
	public String getHelp() {
		return HELP_SYNTAX;
	}

	@Override
	public void doAction(String input, PrintWriter out, CommandController cc, User user) {
		//out.println("This is GI hope this worksmust sleep reload test");
		// check to make sure we are calling the right command
		//		if (!input.trim().startsWith(getCommand())) {
		//			System.err.println("Something went wrong! The wrong command was called!");
		//			return;
		//		}
		// first check by seeing if its the generic help command with only
		// "help"
		if (input.equals(getCommand())) {
			// print out all the commands
			//			System.out.println("FOUND ONLY HELP COMMAND");
			out.println(FOLLOWING_COMMANDS);
			Enumeration<String> keys = cc.getCommandMap().keys();
			while (keys.hasMoreElements())
				out.println("'" + keys.nextElement() + "' ");
			//out.print("\n");
			out.println(getHelp());
			out.flush();
			return;
		}
		// get the command from the commands map and print out its getHelp()
		// method
		ArrayList<String> inputList = CommandHandler.getCommandArrayList(input);
		Command command = null;
		StringBuilder currentCommand = new StringBuilder();
		// start at 1 because we don't need the help command as part of it
		for (int i = 1; i < inputList.size(); i++) {
			currentCommand.append(inputList.get(i));
			currentCommand.append(" ");
			if ((command = cc.getCommandMap().get(currentCommand.toString().trim())) != null) {
				// print out the getHelp() for that command
				// //////////////////////////////////////////////////////////
				// EX:
				// "Command: help \n
				// Type help <command> to get help for a given command."
				// /////////////////////////////////////////////////////////
				out.println(COMMAND_TEXT + currentCommand.toString().trim() + "\n" + command.getHelp());
				out.flush();
				return;
			}
		}
		// no command was found for the given help <command>
		out.println(COMMAND_NOT_FOUND);
		out.println(FOLLOWING_COMMANDS);
		Enumeration<String> keys = cc.getCommandMap().keys();
		while (keys.hasMoreElements())
			out.println("'" + keys.nextElement() + "' ");
		//out.println("\n");
		out.println(getHelp());
		out.flush();
	}

}
