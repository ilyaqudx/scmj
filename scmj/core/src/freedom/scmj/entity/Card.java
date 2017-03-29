package freedom.scmj.entity;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Card extends Group implements Comparable<Card>{

	
	public static final String[] TYPE_STRING = {"万","条","筒"};
	
	public static final int TYPE_TONG = 2,TYPE_TIAO = 1,TYPE_WAN = 0;
	
	/**牌类型(筒-条-万)*/
	private int type;
	/**牌值(1-9)*/
	private int value;
	/**唯一ID*/
	private int id;
	public Card(int type,int value,int id)
	{
		this.type  = type;
		this.value = value;
		this.id    = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	} 
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getString()
	{
		return type + "" + value;
	}
	
	public static final String getTypeString(int type)
	{
		return String.valueOf(type);
	}
	/*public static final String getTypeString(int type)
	{
		return type == 0 ? "0" : String.valueOf(type);
	}*/
	
	public static final boolean isSame(Card c1,Card c2)
	{
	    if(null == c1 || c2 == null)
		return false;
	    return isSameType(c1, c2) && c1.getValue() == c2.getValue();
	}
	
	public static final boolean isSameType(Card c1,Card c2)
	{
	    if(null == c1 || c2 == null)
		return false;
	    return c1.getType() == c2.getType();
	}
	
	public Card copyProperties()
	{
	    return new Card(type, value, id);
	}
	
	@Override
	public String toString() {
		return value + TYPE_STRING[type];
	}

	public int compareTo(Card o) {
		if(o.getType() == type)
			return o.getValue() > value ? -1 : 0;
		
		return o.getType() > type ? -1 : 0;
	}
}
