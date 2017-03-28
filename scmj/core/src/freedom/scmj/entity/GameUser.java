package freedom.scmj.entity;

public class GameUser {

	public static final byte SEX_MAN = 1,SEX_WOMAN = 2;
	
	private int userId;
	private String name;
	private byte sex;
	private int gold;
	private GameData data = new GameData();
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getSex() {
		return sex;
	}
	public void setSex(byte sex) {
		this.sex = sex;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public GameData getData() {
		return data;
	}
	@Override
	public String toString() {
		return "GameUser [userId=" + userId + ", name=" + name + ", gold="
				+ gold + "]";
	}
}
