package hooverville.server;

import hooverville.characters.HoovervilleCharacter;

public class User {

    private String login;    
    private ClientConnection clientConnection;
    private HoovervilleCharacter hoovervilleCharacter;    
    private String password;

    public User(String username, ClientConnection c) {
        this.login = username;        
        this.clientConnection = c;       
    }   

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the clientConnection
     */
    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    /**
     * @param clientConnection the clientConnection to set
     */
    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

	/**
	 * @param hoovervilleCharacter the hoovervilleCharacter to set
	 */
	public void setHoovervilleCharacter(HoovervilleCharacter hoovervilleCharacter) {
		this.hoovervilleCharacter = hoovervilleCharacter;
	}

	/**
	 * @return the hoovervilleCharacter
	 */
	public HoovervilleCharacter getHoovervilleCharacter() {
		return hoovervilleCharacter;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

}
