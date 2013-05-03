package hooverville.characters;

import hooverville.actions.InteractiveAction;
import hooverville.actions.SoloAction;
import hooverville.actions.interactive.Curse;
import hooverville.actions.interactive.Spell;
import hooverville.actions.solo.BrewPotion;
import hooverville.items.Item;
import hooverville.items.self.Plant;
import hooverville.skillsets.Skill;
import hooverville.skillsets.levels.Level;
import hooverville.worlds.World;
import hooverville.worlds.regions.Region;
import hooverville.worlds.regions.RegionIndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public abstract class HoovervilleCharacter {

	protected static final int DEFAULT_HEALTH = 100;
	protected static final int DEFAULT_MANA = 100;
	protected static final int INIT_LEVEL = 1;

	protected volatile Region currentRegion;
	protected volatile Region lastRegion;

	protected int xPos;
	protected int yPos;
	protected boolean fighting;
	protected boolean hiding;

	protected List<Skill> skillsets = new ArrayList<Skill>();
	protected List<SoloAction> soloActions = new ArrayList<SoloAction>();
	protected List<InteractiveAction> interactiveActions = new ArrayList<InteractiveAction>();
	protected List<HoovervilleCharacter> friends = new ArrayList<HoovervilleCharacter>();

	final String sex;
	private String name;

	public HoovervilleCharacter(String sex, String userName) {
		this(sex, userName, World.getDefault());
	}

	public HoovervilleCharacter(String sex, String userName, Region initial) {
		this.sex = sex;
		name = userName;
		currentRegion = initial;
		lastRegion = currentRegion;
	}

	public abstract boolean emptyInventory();

	public void castSpell(Spell s) {
		s.doAction();
	}

	public int curse(Curse curse) {
		return curse.doAction();
	}

	/**
	 * Returns the location, i.e. the "Region" that the character
	 * currently resides in.
	 *
	 * @return the current region of the character
	 */
	public Region getCurrentRegion() { return currentRegion; }

	public abstract int getDefense();
	/**
	 * Gives a description of the characters current status. This could be the state of their health,
	 * their mana, or other vitals. Also, this could entail a characters history, strengths, weaknesses, etc.
	 *
	 * @return a meaningful description of the player's status
	 */
	public abstract String getDescription();

	public abstract int getDexterity();
	public List<HoovervilleCharacter> getFriends() { return friends; }

	/**
	 * Returns whether or not the character is male or female
	 *
	 * @return gender - The gender of the character
	 */
	public String getGender() { return sex; }
	public abstract int getHealth();
	public abstract int getIntelligence();

	public List<InteractiveAction> getInteractiveActions() { return interactiveActions; }
	/**
	 *
	 * @return the items the character currently possesses
	 */
	public abstract List<Item> getInventory();

	public abstract Level getLevel();
	public abstract int getMana();

	public Region getMostRecentPreviousRegion() { return lastRegion; }

	public abstract int getPotionMakingAbility();

	public List<SoloAction> getSoloActions() { return soloActions; }

	public abstract int getTelepathy();

	/**
	 * Gives the specific type of the character being used. For example,
	 * a character could be a Light Keeper, a Shadow Seeker, A harlot, etc.
	 *
	 * @return type - The type of character being used
	 */
	public abstract String getType();

	public String getUserName() { return name; }
	public void setUserName(String s) { name = s; }

	public int getX() { return xPos; }

	public int getY() { return yPos; }

	public boolean isFighting() {
		return fighting;
	}

	public boolean isHiding() {
		return hiding;
	}
	public void makePotion(List<Plant> plants) {
		new BrewPotion(this).brewHealthRegenPotion();
	}

	public void setCurrentRegion(Region newRegion) {
		lastRegion = currentRegion;
		currentRegion = newRegion;
	}

	public abstract void setDefense(int delta);

	public abstract void setDexterity(int delta);
	public void setFighting(boolean b) {
		fighting = b;
	}

	public abstract void setHealth(int delta);
	public void setHiding(boolean b) {
		hiding = b;
	}

	public abstract void setIntelligence(int delta);
	public abstract void setMana(int delta);

	public abstract void setPotionMakingAbility(int delta);
	public abstract void setTelepathy(int delta);

	public void setX(int x) { xPos = x; }

	public void setY(int y) { yPos = y; }
	
	public void goNorth() throws RegionIndexOutOfBoundsException {
		try {
			currentRegion.moveNorth(this);
		}
		catch (RegionIndexOutOfBoundsException e) {
			Region north = World.getRegionNorthOf(currentRegion);
			if (north == null) throw e;
			else {
				currentRegion.removeCharacter(this);
				north.addCharacter(this);
				currentRegion = north;
			}
		}
	}
	
	public void goSouth() throws RegionIndexOutOfBoundsException {
		try {
			currentRegion.moveSouth(this);
		}
		catch (RegionIndexOutOfBoundsException e) {
			Region south = World.getRegionSouthOf(currentRegion);
			if (south == null) throw e;
			else {
				currentRegion.removeCharacter(this);
				south.addCharacter(this);
				currentRegion = south;
			}
		}
	}
	
	public void goEast() throws RegionIndexOutOfBoundsException {
		try {
			currentRegion.moveEast(this);
		}
		catch (RegionIndexOutOfBoundsException e) {
			Region east = World.getRegionEastOf(currentRegion);
			if (east == null) throw e;
			else {
				currentRegion.removeCharacter(this);
				east.addCharacter(this);
				currentRegion = east;
			}
		}
	}
	
	public void goWest() throws RegionIndexOutOfBoundsException {
		try {
			currentRegion.moveWest(this);
		}
		catch (RegionIndexOutOfBoundsException e) {
			Region west = World.getRegionWestOf(currentRegion);
			if (west == null) throw e;
			else {
				currentRegion.removeCharacter(this);
				west.addCharacter(this);
				currentRegion = west;
			}
		}
	}

}
