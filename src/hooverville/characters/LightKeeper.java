package hooverville.characters;

import hooverville.items.Item;
import hooverville.skillsets.Defense;
import hooverville.skillsets.Dexterity;
import hooverville.skillsets.Health;
import hooverville.skillsets.Intelligence;
import hooverville.skillsets.Mana;
import hooverville.skillsets.PotionMakingAbility;
import hooverville.skillsets.Skill;
import hooverville.skillsets.Telepathy;
import hooverville.skillsets.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class LightKeeper extends HoovervilleCharacter {

	public static final String TYPE = "Light Keeper";

	private final List<Item> sac = new ArrayList<Item>();
	private final List<Skill> skillsets = new ArrayList<Skill>();

	private final Level level = new Level(1);

	private final Skill health = new Health(100);
	private final Skill mana = new Mana(100);
	private final Skill defense = new Defense(70);
	private final Skill intel = new Intelligence(80);
	private final Skill telepathy = new Telepathy(10);
	private final Skill dex = new Dexterity(50);
	private final Skill potion = new PotionMakingAbility(10);

	public LightKeeper(String gender, String userName) {
		super(gender, userName);
		initSkillSets();
	}

	@Override
	public int getDefense() {
		return defense.get();
	}

	@Override
	public String getDescription() {
		StringBuilder desc = new StringBuilder();
		desc.append(TYPE);         	desc.append(": \n");
		desc.append("Health: ");   	desc.append(health.get()); desc.append("\n");
		desc.append("Mana: ");    	desc.append(mana.get());   desc.append("\n");
		desc.append("Level: ");		desc.append(level.get());  desc.append("\n");
		return desc.toString();
	}

	@Override
	public boolean emptyInventory() {
		return sac.size() == 0;
	}

	@Override
	public int getDexterity() {
		return dex.get();
	}

	@Override
	public int getHealth() {
		return health.get();
	}

	@Override
	public int getIntelligence() {
		return intel.get();
	}

	@Override public List<Item> getInventory() {
		return sac;
	}

	@Override public Level getLevel() {
		return level;
	}

	@Override
	public int getMana() {
		return mana.get();
	}

	@Override
	public int getPotionMakingAbility() {
		return potion.get();
	}

	public List<Skill> getSkillSets() {
		return skillsets;
	}

	@Override
	public int getTelepathy() {
		return telepathy.get();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	private void initSkillSets() {
		skillsets.add(health);
		skillsets.add(mana);
		skillsets.add(defense);
		skillsets.add(intel);
		skillsets.add(telepathy);
		skillsets.add(dex);
		skillsets.add(potion);
	}

	@Override
	public void setDefense(int delta) {
		defense.set(delta);
	}

	@Override
	public void setDexterity(int delta) {
		dex.set(delta);
	}

	@Override
	public void setHealth(int delta) {
		health.set(delta);
	}

	@Override
	public void setIntelligence(int delta) {
		intel.set(delta);
	}

	@Override
	public void setMana(int delta) {
		mana.set(delta);
	}

	@Override
	public void setPotionMakingAbility(int delta) {
		dex.set(delta);
	}

	@Override
	public void setTelepathy(int delta) {
		telepathy.set(delta);
	}

}
