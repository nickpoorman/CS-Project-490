package hooverville.commands.util;

import hooverville.commands.plugins.Command;
import hooverville.server.ClientManager;
import hooverville.server.DatabaseInterface;
import hooverville.worlds.World;
import hooverville.worlds.WorldGenerator;

import java.util.concurrent.ConcurrentHashMap;

public class CommandController {

	private ConcurrentHashMap<String, Command> commandMap;
	private CommandHandler commandHandler;
	private CommandLoader commandLoader;
	private ClientManager clientManager;
	private DatabaseInterface db;
	//keep track of the world
	private World world;

	public CommandController() {
		commandMap = new ConcurrentHashMap<String, Command>();
		commandLoader = new CommandLoader(this);
		clientManager = new ClientManager();
		world = WorldGenerator.generate();
		setDb(new DatabaseInterface(world));
	}

	/**
	 * @param s
	 *            the String command to be referenced
	 * @param c
	 *            the Command object
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key
	 */
	public Command putCommand(String s, Command c) {
		if (s == null) return null;
		if (s == "") return null;
		return commandMap.put(s, c);
	}

	/**
	 * @param s
	 *            the String command to be referenced
	 * @return the value to which the specified key is mapped, or null if this
	 *         map contains no mapping for the key
	 */
	public Command getCommand(String command) {
		return commandMap.get(command);
	}

	/**
	 * @return the commandHandler
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	/**
	 * @param commandHandler
	 *            the commandHandler to set
	 */
	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	/**
	 * @return the commandLoader
	 */
	public CommandLoader getCommandLoader() {
		return commandLoader;
	}

	/**
	 * @param commandLoader
	 *            the commandLoader to set
	 */
	public void setCommandLoader(CommandLoader commandLoader) {
		this.commandLoader = commandLoader;
	}

	/**
	 * @return the commandMap
	 */
	public ConcurrentHashMap<String, Command> getCommandMap() {
		return commandMap;
	}

	/**
	 * @param commandMap
	 *            the commandMap to set
	 */
	public void setCommandMap(ConcurrentHashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager() {
		return clientManager;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(DatabaseInterface db) {
		this.db = db;
	}

	/**
	 * @return the db
	 */
	public DatabaseInterface getDb() {
		return db;
	}
}
