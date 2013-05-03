package plugins;

import java.io.File;
import java.io.PrintWriter;

import hooverville.commands.plugins.Command;
import hooverville.commands.util.CommandController;
import hooverville.server.User;

public class ReloadCommands implements Command {

	private final String COMMAND = "reload";

	@Override
	public void doAction(String input, PrintWriter out, CommandController cc, User user) {
		if (input.equals("reload commands")) {
			out.println("Reloading commands now");
			while (cc.getCommandMap().size() != 0) {
				cc.getCommandMap().clear();
			}
//			for (int i = 0; i < 3; i++) {
//				Runtime.getRuntime().gc();
//			}
			cc.getCommandLoader().loadAllCommandsInDir(new File("commands\\"), null);
			out.println("Done reloading commands");
		} else {
			// redirect to help
			out.println(getHelp());
		}
		return;
	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "reload";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Type 'reload [commands]' to run the command loader.";
	}

}
