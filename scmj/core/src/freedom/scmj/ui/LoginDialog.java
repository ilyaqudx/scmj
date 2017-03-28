package freedom.scmj.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import freedom.scmj.common.ui.UI;

public class LoginDialog extends Group{

	//背景
	private Image backgroud;
	//用户名
	private Image username;
	//密码
	private Image password;
	
	public LoginDialog()
	{
		this.backgroud = UI.image("pop_window_small.png");
		this.username  = UI.image("input_bg.png");
		this.password  = UI.image("input_bg.png");
		
		this.username.setScale(2);
		this.password.setScale(2);
		
		UI.center(this.backgroud);
		UI.pos(this.username, 400, 380);
		UI.pos(this.password, 400, 400);
		
		addActor(backgroud);
		addActor(username);
		addActor(password);
	}
	
}
