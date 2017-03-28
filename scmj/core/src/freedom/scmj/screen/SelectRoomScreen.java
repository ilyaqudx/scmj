package freedom.scmj.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import freedom.scmj.GameApplication;
import freedom.scmj.common.screen.BaseScreen;
import freedom.scmj.common.ui.TouchListener;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.ui.View;
import freedom.scmj.common.var3d.net.freefont.FreeBitmapFont;

public class SelectRoomScreen extends BaseScreen {

	public SelectRoomScreen(GameApplication game)
	{
		super(game);
		
		//创建背景
		View.create(UI.image("load_Bg.jpg")).center().addTo(stage);
		//创建选择房间背景
		View.create(UI.image("mall/bg2.png")).centerX(20).addTo(stage);
		//创建选择房间背景上面的TAG  
		View.create(UI.image("tag_blue.png")).centerX(611).addTo(stage);
		//TAG上面的字
		createTagText();
		InputListener listener = new TouchListener()
		{
			@Override
			public void onClicked(Actor actor, float x, float y) 
			{
				SelectRoomScreen.this.game.setScreen(new GameScreen(SelectRoomScreen.this.game));
			}
		};
		//创建房间Item
		View.create(UI.image("room/item1.png")).pos(100, 100).addTo(stage).addClickListener(listener );
		View.create(UI.image("room/game1.png")).pos(150, 200).addTo(stage);
		View.create(UI.image("room/item2.png")).pos(500, 100).addTo(stage).addClickListener(listener);
		View.create(UI.image("room/game2.png")).pos(560, 200).addTo(stage);
		View.create(UI.image("room/item3.png")).pos(900, 100).addTo(stage).addClickListener(listener);
		View.create(UI.image("room/game3.png")).pos(960, 200).addTo(stage);
	}

	private void createTagText() {
		FreeBitmapFont font = GameApplication.I.font;
		font.setSize(36);
		font.setBold(true);
		font.appendText("血战到底");
		Label userNameLabel = UI.label("血战到底", font);
		View.create(userNameLabel).centerX(635).addTo(stage);
	}

	@Override
	public void draw(float delta)
	{
		
	}

}
