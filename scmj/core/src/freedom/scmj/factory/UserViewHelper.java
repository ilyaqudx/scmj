package freedom.scmj.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import freedom.scmj.GameApplication;
import freedom.scmj.common.ui.NumberFont;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.var3d.net.freefont.FreeBitmapFont;
import freedom.scmj.entity.GameUser;
import freedom.scmj.ui.UserView;

/**游戏场
 * @author Administrator
 *
 */
public class UserViewHelper {

	public static final UserView createUpDownUser(GameUser user,TextureAtlas userHeadAtlas)
	{
		String userName = user.getName();
		int gender = user.getSex();
		int gold   = user.getGold();
		UserView node = new UserView(userName,gender,gold);
		Image back = UI.image(UserView.BACK_STRING_UP_DOWN, userHeadAtlas);
		node.setSize(back.getWidth(), back.getHeight());
		Image userHead = UI.image(gender == UserView.GENDER_MAN ? UserView.GENDER_MAN_RES : 
			UserView.GENDER_WOMAN_RES, userHeadAtlas);
		
		//create username
		FreeBitmapFont font = new FreeBitmapFont(GameApplication.I.getFreeListener());
		font.appendText(userName);
		Label userNameLabel = UI.label(userName, font);
		
		NumberFont goldFont = new NumberFont("main_jiadoushuzi.png", 24, 30,String.valueOf(gold), false);
		
		userHead.setPosition(5, 2);
		userNameLabel.setPosition(80, 40);
		goldFont.setPosition(80, 5);
		goldFont.setScale(0.8f);
		
		node.setNode(back, userHead, userNameLabel, goldFont);
		
		return node;
	}
	
	public static final UserView createLeftRightUser(GameUser user,TextureAtlas userHeadAtlas)
	{
		String userName = user.getName();
		int gender = user.getSex();
		int gold   = user.getGold();
		UserView node = new UserView(userName,gender,gold);
		Image back = UI.image(UserView.BACK_STRING_LEFT_RIGHT, userHeadAtlas);
		node.setSize(back.getWidth(), back.getHeight());
		
		Image userHead = UI.image(gender == UserView.GENDER_MAN ? UserView.GENDER_MAN_RES : 
			UserView.GENDER_WOMAN_RES, userHeadAtlas);
		
		//create username
		FreeBitmapFont font = new FreeBitmapFont(GameApplication.I.getFreeListener());
		font.appendText(userName);
		Label userNameLabel = UI.label(userName, font);
		
		NumberFont goldFont = new NumberFont("main_jiadoushuzi.png", 24, 30,String.valueOf(gold), false);
		
		UI.middleX(userHead,80,node);
		userNameLabel.setPosition(0, 40);
		goldFont.setPosition(0, 5);
		goldFont.setScale(0.8f);
		
		node.setNode(back, userHead, userNameLabel, goldFont);
		
		return node;
	}
}
