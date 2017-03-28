package freedom.scmj.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import freedom.scmj.GameApplication;
import freedom.scmj.common.screen.BaseScreen;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.ui.View;
import freedom.scmj.ui.LoginDialog;

public class LoginScreen extends BaseScreen {

	View backgroud;
	View loginDialog;
	public LoginScreen(GameApplication game) {
		
		super(game);
		
		backgroud   = View.create(UI.image("load_Bg.jpg")).addTo(stage);
		loginDialog = View.create(new LoginDialog()).scale(0, 0).addTo(stage)
						.runAction(Actions.scaleTo(1, 1, 0.3f, Interpolation.elasticOut));
	}

	@Override
	public void draw(float delta) 
	{
		
	}

}
