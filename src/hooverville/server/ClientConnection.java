package hooverville.server;

import hooverville.commands.util.CommandController;
import hooverville.commands.util.CommandHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection extends Thread {

	private Socket socket = null;
	private ClientManager clientManager = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String currentUser = null;
	private final static boolean DEBUG = true;
	private boolean forceLogOut = false;
	private CommandController cc;

	// private CommandHandler commandHandler;

	public ClientConnection(Socket socket, ClientManager manager, CommandController cc) {
		super("ClientConnectionThread");
		this.cc = cc;
		this.socket = socket;
		this.clientManager = manager;
		if (DEBUG) System.out.println("New user has connected");
	}

	public void setUsername(String user) {
		this.currentUser = user;
	}

	public void run() {
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream())), true);
			in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

			CommandHandler commandHandler = new CommandHandler(cc, out);

			String inputLine, outputLine;
			GameProtocol gp = new GameProtocol(clientManager, this, commandHandler);
			outputLine = gp.processInput("EXECLOGIN0001");
			getOut().println(outputLine);

			while ((inputLine = getIn().readLine()) != null) {
				if (inputLine.equals("")) {
					continue;
				}
				if (DEBUG) System.out.println("Debug (from client): " + inputLine);
				outputLine = gp.processInput(inputLine);
				if (outputLine != null) getOut().println(outputLine);
				getOut().flush();

			}
			logOutUser();
		} catch (SocketException e) {
			/*
			 * worst case the user is logged in and the logOutUser() method gets
			 * called twice, wont matter because the other user isnt logged in
			 * yet
			 */
			System.out.println("THEY CLOSED THEIR GAME");
			if (!forceLogOut) {
				logOutUser();
			}
		} catch (IOException e) {
			if (DEBUG) e.printStackTrace();
		}
	}

	public void forceLogOut() {
		this.forceLogOut = true;
		logOutUser();
	}

	private void logOutUser() {
		try {
			getOut().close();
			getIn().close();
			getSocket().close();
		} catch (IOException ex) {
			if (DEBUG) ex.printStackTrace();
		} finally {
			if (DEBUG) System.out.println("User has quit: " + getCurrentUser());
			if (!getCurrentUser().equals("")) {
				//save the user info into the database
				if (DEBUG) System.out.println("Persisting user character for: " + getCurrentUser());
				cc.getDb().saveCharacter(cc.getClientManager().getUser(getCurrentUser()).getHoovervilleCharacter());
				boolean b = clientManager.removeClient(getCurrentUser());
				if (DEBUG) System.out.println("Removed user: " + b);
			}
		}
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * @return the currentUser
	 */
	public String getCurrentUser() {
		if (currentUser != null) {
			return currentUser;
		}
		return "";
	}

	/**
	 * @return the cc
	 */
	public CommandController getCommandController() {
		return cc;
	}
}
