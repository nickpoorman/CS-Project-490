package hooverville.server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

	ConcurrentHashMap<String, User> clients;

	public ClientManager() {
		clients = new ConcurrentHashMap<String, User>();
	}

	public void addClient(User user, ClientConnection c) {
		user.setClientConnection(c);
		clients.put(user.getLogin(), user);
	}

	public boolean removeClient(String user) {
		if (!contains(user)) {
			return false;
		}
		clients.remove(user);
		return true;

	}

	public boolean contains(String user) {
		return clients.containsKey(user);

	}

	public Socket getClientSocket(String user) {
		return clients.get(user).getClientConnection().getSocket();

	}

	public boolean isLoggedIn(String user) {
		return clients.containsKey(user);

	}

	public ConcurrentHashMap<String, User> getClients() {
		return new ConcurrentHashMap<String, User>(clients);
	}

	public User getUser(String user) {
		return clients.get(user);

	}
}
