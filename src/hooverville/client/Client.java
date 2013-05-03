package hooverville.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread {

	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String host = "127.0.0.1";
	private final static boolean DEBUG = true;
	GameGUI gui;	

	
	public Client() {

	}

	// public void login(String user, String pass){
	// this.username = user;
	// this.password = pass;
	// this.getOut().println(username);
	// }

	public void run() {
		try {

			try {
				socket = new Socket(getHost(), 31337);
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream())), true);
				in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host: " + getHost() + ".");
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for the connection to: " + getHost() + ".");
				System.exit(1);
			}

			/* Take the input stream from the system and use that
			 * TODO: Change the input stream to be the clients input field
			 */
//			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//						
//
//			InputStreamHandler ish = new InputStreamHandler(stdIn, this.getOut());
//			ish.start();			
			

			String fromServer;
			while ((fromServer = getIn().readLine()) != null) {
				// COMMUNICATE WITH THE GAME PROTOCOL HERE
				//if (DEBUG) System.out.println("Debug (from server): " + fromServer);
				//System.out.println(fromServer);
				//gui.console.append(fromServer + "\n");
				gui.addToScreen(fromServer);
			}
			
			/* clean up if for some reason the input is null */
			if (DEBUG) System.out.println("POINT 1");
			getOut().close();
			if (DEBUG) System.out.println("POINT 2");
			getIn().close();
			if (DEBUG) System.out.println("POINT 3");
			getSocket().close();
			if (DEBUG) System.out.println("POINT 4");
			System.exit(0);
			if (DEBUG) System.out.println("POINT 5");

		} catch (SocketException e) {
			if (DEBUG) System.err.println("Server Died");
			/* Put into the output of the client that the server disconnected */
			//if (DEBUG) e.printStackTrace();
		} catch (IOException e) {
			if (DEBUG) e.printStackTrace();
		}
	}

//	public void addToScreen(final String text) {
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//
//			public void run() {
//				//add the text
//				gui.console.append(text);
//				gui.addToScreen(text);
//				
//			}
//		});
//	}

	
	/**
	 * @param gui the gui to set
	 */
	public void setGui(GameGUI gui) {
		this.gui = gui;
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
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

}
