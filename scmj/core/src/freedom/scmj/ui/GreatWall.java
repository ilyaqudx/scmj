package freedom.scmj.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;

import freedom.scmj.common.ui.UI;

public class GreatWall extends Group 
{
	public GreatWall(TextureAtlas prepareAtlas)
	{
		this.addActor(UI.middleY(UI.image("prepare_wall-h", prepareAtlas), 370));
		this.addActor(UI.middleY(UI.image("prepare_wall-h", prepareAtlas), 865));
		this.addActor(UI.middleX(UI.image("prepare_wall-w", prepareAtlas), 112));
		this.addActor(UI.middleX(UI.image("prepare_wall-w", prepareAtlas), 530));
	}
	
	public void hide()
	{
		this.setVisible(false);
	}
}
