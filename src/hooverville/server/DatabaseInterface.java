package hooverville.server;

import hooverville.characters.CareTaker;
import hooverville.characters.Educator;
import hooverville.characters.Harlot;
import hooverville.characters.Herbologist;
import hooverville.characters.HoovervilleCharacter;
import hooverville.characters.LightKeeper;
import hooverville.characters.ShadowSeeker;
import hooverville.worlds.World;

import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInterface {

	Connection conn;
	private static String CONNECTION_URL = "jdbc:mysql://192.168.0.8:3306/hooverville";
	private static String DATABASE_USERNAME = "root";
	private static String DATABASE_PASSWORD = "test";
	private World world;

	public DatabaseInterface(World world) {
		this.world = world;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
		} catch (IllegalAccessException ex) {
			System.err.println(ex.getMessage());
		} catch (InstantiationException ex) {
			System.err.println(ex.getMessage());
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

	}

	
	/**
	 * @param username
	 *            the username to check the database for existence
	 *            
	 * @return true if the username wasn't in use before and if it is now reserved;
	 * 			 else return false
	 */
	public boolean checkAndCreateUsername(String username) {
		String query1 = "Select count(*) from users where character_name = '" + username + "';";
		int count = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);

			while (rs.next()) {
				count = rs.getInt("count(*)");
				break;
			}
			if (count == 0) {
				String query2 = "INSERT INTO users(character_name, password) VALUES ('" + username + "', 'NEWCHAR');";				
				st.executeUpdate(query2);
				return true;
			} else {
				return false;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;		
	}
	
	public HoovervilleCharacter updateNewlyCreatedCharacter(HoovervilleCharacter hc, User user){
		Point currentRegion = world.getIndicesOf(hc.getCurrentRegion());
		
		StringBuffer sb = new StringBuffer();		
		
		sb.append("UPDATE users ");
		sb.append("SET ");
		sb.append("password = '" + user.getPassword() + "',");
		sb.append("character_type = '" + hc.getType() + "',");
		sb.append("health = " + hc.getHealth() + ",");
		sb.append("mana = " + hc.getMana() + ",");
		sb.append("hp  = " + 0 + ",");
		sb.append("mp = " + 0 + ",");
		sb.append("dex = " + hc.getDexterity() + ",");
		sb.append("str = " + 0 + ",");
		sb.append("intel = " + hc.getIntelligence() + ",");
		sb.append("defense = " + hc.getDefense() + ",");
		sb.append("location_x = " + currentRegion.x + ",");
		sb.append("location_y = " + currentRegion.y + ",");
		sb.append("char_level = " + hc.getLevel().get() + ",");
		sb.append("potion_abil = " + hc.getPotionMakingAbility() + ",");
		sb.append("telepathy = " + hc.getTelepathy() + " ");
		sb.append("WHERE character_name = '" + user.getLogin() + "' ;");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sb.toString());
			return hc;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}

	public HoovervilleCharacter createNewCharacter(HoovervilleCharacter hc, User user) {

		//		hc.getUserName();
		//		hc.getCurrentRegion();
		//		hc.getDefense();
		//		hc.getDexterity();
		//		hc.getHealth();
		//		hc.getIntelligence();
		//		hc.getLevel();
		//		hc.getMana();
		//		hc.getPotionMakingAbility();
		//		hc.getTelepathy();
		//		hc.getType();
		//first check if there is someone with that username

		String query1 = "Select count(*) from users where character_name = '" + hc.getUserName() + "';";
		int count = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);

			while (rs.next()) {
				count = rs.getInt("count(*)");
				break;
			}
			Point currentRegion = world.getIndicesOf(hc.getCurrentRegion());
			String insertQuery = "INSERT INTO users(password, character_name, character_type, health, mana, hp, mp, dex, str, intel, defense, location_x, location_y, char_level, potion_abil, telepathy)"
					+ " VALUES ('"
					+ user.getPassword() //password
					+ "', '" + user.getLogin() //character_name
					+ "', '" + hc.getType() //character_type
					+ "', " + hc.getHealth() //health
					+ ", " + hc.getMana() //mana
					+ ", " + 0 //hp
					+ ", " + 0 //mp
					+ ", " + hc.getDexterity() //dex
					+ ", " + 0 //str
					+ ", " + hc.getIntelligence() //intel
					+ ", " + hc.getDefense() //defense
					+ ", " + currentRegion.x //location_x
					+ ", " + currentRegion.y //location_y
					+ ", " + hc.getLevel().get() //char_level
					+ ", " + hc.getPotionMakingAbility() //potion_abil
					+ ", " + hc.getTelepathy() //telepathy
					+ ");"; //end
			
			String update = "";
			
			if (count == 0) {
				//System.out.println(insertQuery);
				st.executeUpdate(insertQuery);
				return hc;
			} else {
				return null;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public void saveCharacter(HoovervilleCharacter hc) {
		//		hc.getUserName();
		//		hc.getCurrentRegion();
		//		hc.getDefense();
		//		hc.getDexterity();
		//		hc.getHealth();
		//		hc.getIntelligence();
		//		hc.getLevel();
		//		hc.getMana();
		//		hc.getPotionMakingAbility();
		//		hc.getTelepathy();
		//		hc.getType();

		Point currentRegion = world.getIndicesOf(hc.getCurrentRegion());

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE users ");
		sb.append("SET ");
		sb.append("health = " + hc.getHealth() + ",");
		sb.append("mana = " + hc.getMana() + ",");
		sb.append("hp  = " + 0 + ",");
		sb.append("mp = " + 0 + ",");
		sb.append("dex = " + hc.getDexterity() + ",");
		sb.append("str = " + 0 + ",");
		sb.append("intel = " + hc.getIntelligence() + ",");
		sb.append("defense = " + hc.getDefense() + ",");
		sb.append("location_x = " + currentRegion.x + ",");
		sb.append("location_y = " + currentRegion.y + ",");
		sb.append("char_level = " + hc.getLevel().get() + ",");
		sb.append("potion_abil = " + hc.getPotionMakingAbility() + ",");
		sb.append("telepathy = " + hc.getTelepathy() + " ");
		sb.append("WHERE character_name = '" + hc.getUserName() + "' ;");

		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sb.toString());
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

	}
	
	public boolean isLoginValid(String username, String password){
		String query1 = "Select count(*) from users where character_name = '" + username + "' AND password = '" + password + "';";
		int count = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);

			while (rs.next()) {
				count = rs.getInt("count(*)");
				break;
			}
			if (count == 0) {
				//didnt find it
				return false;
			} else {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;		
	}

	public HoovervilleCharacter getCharacter(String username) {
		String query = "SELECT * FROM users where character_name = '" + username + "';";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				//get the type of character
				String type = rs.getString("character_type");
				//create the new hoovervillecharacter based on the type
				HoovervilleCharacter hc;
				if (type.equals("Care Taker")) {
					hc = new CareTaker("male", username);
				} else if (type.equals("Educator")) {
					hc = new Educator("male", username);
				} else if (type.equals("Harlot")) {
					hc = new Harlot("male", username);
				} else if (type.equals("Herbologist")) {
					hc = new Herbologist("male", username);
				} else if (type.equals("Light Keeper")) {
					hc = new LightKeeper("male", username);
				} else if (type.equals("Shadow Seeker")) {
					hc = new ShadowSeeker("male", username);
				} else {
					return null;
				}

				
				hc.setCurrentRegion(world.getRegionAt(rs.getInt("location_x"), rs.getInt("location_y")));
				hc.setDefense(rs.getInt("defense"));
				hc.setDexterity(rs.getInt("dex"));
				hc.setHealth(rs.getInt("health"));
				hc.setIntelligence(rs.getInt("intel"));
				hc.setMana(rs.getInt("mana"));
				hc.setPotionMakingAbility(rs.getInt("potion_abil"));
				hc.setTelepathy(rs.getInt("telepathy"));
				hc.setUserName(rs.getString("character_name"));
				hc.getLevel().set(rs.getInt("char_level"));
				//TODO: store the users exp for that level
				return hc;

			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return null;
	}

}
