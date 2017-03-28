package freedom.scmj.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import freedom.scmj.common.ui.NumberFont;

public class UserNode extends Group 
{
	
	public static final String BACK_STRING_UP_DOWN = "Head_back_up_down",
			BACK_STRING_LEFT_RIGHT = "head_back_left_right";
	
	public static final int GENDER_MAN = 1,GENDER_WOMAN = 2;
	
	public static final String GENDER_MAN_RES = "Head_Common_Male",GENDER_WOMAN_RES = "Head_Common_Female";
	
	/**背景*/
	private Image back;
	/**头像*/
	private Image userHead;
	private Label userName;
	private NumberFont goldFont;
	
	/**
	 * 基础数据
	 * */
	private int gold;
	private int gender;
	private String name;
	
	public UserNode(String name,int gender,int gold)
	{
		//原始数据
		this.name  = name;
		this.gold  = gold;
		this.gender= gender;
	}
	
	public UserNode setNode(Image back,Image userHead,Label userName,NumberFont goldFont)
	{
		this.back = back;
		this.userHead = userHead;
		this.userName = userName;
		this.goldFont = goldFont;
		
		this.addActor(back);
		this.addActor(userHead);
		this.addActor(userName);
		this.addActor(goldFont);
		
		return this;
	}
}
