package hooverville.server;

import hooverville.characters.CareTaker;
import hooverville.characters.Educator;
import hooverville.characters.Harlot;
import hooverville.characters.Herbologist;
import hooverville.characters.HoovervilleCharacter;
import hooverville.characters.LightKeeper;
import hooverville.characters.ShadowSeeker;
import hooverville.commands.util.CommandHandler;

public class GameProtocol {

	private static final String PROMPT_MSG = "Please type 'login' to login OR type 'create' to create a new character.";
	private static final String LOGIN_USERNAME_MSG = "Please type your <username>";
	private static final String LOGIN_PASSWORD_MSG = "Please type your <password>";
	private static final String CREATE_ACCOUNT_P1_MSG = "Please enter a name for your new character.";
	private static final String CREATE_ACCOUNT_P1_TRY_AGAIN_MSG = "That name is already in use.";
	private static final String CREATE_ACCOUNT_P2_MSG = "Please enter a password for your character";
	private static final String CREATE_ACCOUNT_P3_MSG = "Please choose the type of character you would like to play: \n 1. Care Taker \n 2. Educator \n 3. Harlot \n 4. Herbologist \n 5. Light Keeper \n 6. Shadow Seeker";
	private static final String CREATE_ACCOUNT_P3_TRY_AGAIN_MSG = "You did not enter a valid character type.";
	private static final String CREATE_ACCOUNT_P3_DONE_MSG = "Your character has been created. You may now log in.";
	private static final String CREATE_ACCOUNT_P3_ERR_MSG = "Something went wrong with creating your character. Please try again.";
	private static final int WAITING = 0;
	private static final int PROMPT = 1;
	private static final int LOGIN_USERNAME = 2;
	private static final int LOGIN_PASSWORD = 3;
	private static final int NEXT = 4;
	private static final int CREATE_ACCOUNT_P1 = 5;
	//	private static final int CREATE_ACCOUNT_P1_TRY_AGAIN = 6;
	private static final int CREATE_ACCOUNT_P2 = 7;
	private static final int CREATE_ACCOUNT_P3 = 8;
	private int state = WAITING;
	private ClientManager clientManager = null;
	private ClientConnection clientConnection = null;
	private CommandHandler commandHandler;
	private User user;
	private HoovervilleCharacter hoovervilleCharacter;

	public GameProtocol(ClientManager manager, ClientConnection c, CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
		this.clientManager = manager;
		this.clientConnection = c;
	}

	public String processInput(String theInput) {
		String theOutput = null;
		if (state == WAITING) {
			theOutput = PROMPT_MSG;
			state = PROMPT;
		} else if (state == PROMPT) {
			if (theInput.equals("login")) {
				//go to the login state
				theOutput = LOGIN_USERNAME_MSG;
				state = LOGIN_USERNAME;
			} else if (theInput.equals("create")) {
				//go to the create state
				theOutput = CREATE_ACCOUNT_P1_MSG;
				state = CREATE_ACCOUNT_P1;
			}

		} else if (state == CREATE_ACCOUNT_P1) {
			//check to see if the name is taken in the database
			boolean alreadyExists = clientConnection.getCommandController().getDb().checkAndCreateUsername(theInput);
			if (!alreadyExists) {
				//if it is go to sub stage
				theOutput = CREATE_ACCOUNT_P1_TRY_AGAIN_MSG + "\n" + CREATE_ACCOUNT_P1_MSG;
				state = CREATE_ACCOUNT_P1;
			} else {
				//if not, set this user and the clientconnections username
				user = new User(theInput, this.clientConnection);
				clientConnection.setUsername(theInput);
				state = CREATE_ACCOUNT_P2;
				//prompt them to enter a new password
				theOutput = CREATE_ACCOUNT_P2_MSG;
			}
		} else if (state == CREATE_ACCOUNT_P2) {
			//get their password they entered and store it in the User object
			user.setPassword(theInput);
			state = CREATE_ACCOUNT_P3;
			theOutput = CREATE_ACCOUNT_P3_MSG;
		} else if (state == CREATE_ACCOUNT_P3) {
			if (theInput.equalsIgnoreCase("Care Taker")) {
				hoovervilleCharacter = new CareTaker("male", user.getLogin());
			} else if (theInput.equalsIgnoreCase("Educator")) {
				hoovervilleCharacter = new Educator("male", user.getLogin());
			} else if (theInput.equalsIgnoreCase("Harlot")) {
				hoovervilleCharacter = new Harlot("male", user.getLogin());
			} else if (theInput.equalsIgnoreCase("Herbologist")) {
				hoovervilleCharacter = new Herbologist("male", user.getLogin());
			} else if (theInput.equalsIgnoreCase("Light Keeper")) {
				hoovervilleCharacter = new LightKeeper("male", user.getLogin());
			} else if (theInput.equalsIgnoreCase("Shadow Seeker")) {
				hoovervilleCharacter = new ShadowSeeker("male", user.getLogin());
			} else if (theInput.equals("1")) {
				hoovervilleCharacter = new CareTaker("male", user.getLogin());
			} else if (theInput.equals("2")) {
				hoovervilleCharacter = new Educator("male", user.getLogin());
			} else if (theInput.equals("3")) {
				hoovervilleCharacter = new Harlot("male", user.getLogin());
			} else if (theInput.equals("4")) {
				hoovervilleCharacter = new Herbologist("male", user.getLogin());
			} else if (theInput.equals("5")) {
				hoovervilleCharacter = new LightKeeper("male", user.getLogin());
			} else if (theInput.equals("6")) {
				hoovervilleCharacter = new ShadowSeeker("male", user.getLogin());
			} else {
				//they didn't enter a valid character choice
				theOutput = CREATE_ACCOUNT_P3_TRY_AGAIN_MSG + "\n" + CREATE_ACCOUNT_P3_MSG;
				state = CREATE_ACCOUNT_P3;
				return theOutput;
			}
			//create their character and then have them log in
			hoovervilleCharacter.setUserName(user.getLogin());
			hoovervilleCharacter = clientConnection.getCommandController().getDb().updateNewlyCreatedCharacter(
					hoovervilleCharacter, user);
			if (hoovervilleCharacter != null) {
				//if it created the character
				theOutput = CREATE_ACCOUNT_P3_DONE_MSG + "\n" + LOGIN_USERNAME_MSG;
				state = LOGIN_USERNAME;
			} else {
				//something went wrong with the creation of the character
				//have them try again
				theOutput = CREATE_ACCOUNT_P3_ERR_MSG + "\n" + CREATE_ACCOUNT_P1_MSG;
				state = CREATE_ACCOUNT_P1;
			}
		} else if (state == LOGIN_USERNAME) {			
			user = null;
			user = new User(theInput, this.clientConnection);
			theOutput = LOGIN_PASSWORD_MSG;
			state = LOGIN_PASSWORD;			
		} else if (state == LOGIN_PASSWORD) {
			//get their input and check the database to see if a character exists with that password
			user.setPassword(theInput);
			boolean isLoginValid = clientConnection.getCommandController().getDb().isLoginValid(user.getLogin(), user.getPassword());
			
			if (isLoginValid) {
				// check to see if the user is logged in by passing the username
				// into the clientManager
				if (clientManager.isLoggedIn(user.getLogin())) {
					// if they are already logged in
					// disconnect the user on our end
					// first get the user from the ClientManager and 
					// then log them out
					User loggedInUser = clientManager.getUser(user.getLogin());
					loggedInUser.getClientConnection().forceLogOut();
				}
				//add user to the clientManager
				//get the character for that user and initialize it
				hoovervilleCharacter = null;
				hoovervilleCharacter = clientConnection.getCommandController().getDb().getCharacter(user.getLogin());
				user.setHoovervilleCharacter(hoovervilleCharacter);
				
				clientManager.addClient(user, clientConnection);				
				clientConnection.setUsername(user.getLogin());
				//add to their region
				hoovervilleCharacter.getCurrentRegion().addCharacter(hoovervilleCharacter);
				
				theOutput = "Welcome back " + user.getLogin();
				//display the message of the day here
				state = NEXT;
				System.out.println("User: " + theInput + " has logged in.");
			} else {
				theOutput = "You did not enter a valid login. Please try again.";
				state = LOGIN_USERNAME;
			}
		} else if (state == NEXT) {

			//theOutput = "used the command api";
			commandHandler.handleCommand(theInput, user);
		}

		return theOutput;
	}
}
