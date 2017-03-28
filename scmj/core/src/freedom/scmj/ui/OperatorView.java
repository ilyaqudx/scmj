package freedom.scmj.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import freedom.scmj.GameApplication;
import freedom.scmj.common.ui.UI;
import freedom.scmj.entity.GameTable;
import freedom.scmj.entity.Operator;
import freedom.scmj.screen.GameScreen;

public class OperatorView extends Group 
{
	public static final float[][] OPT_POS = 
		{
			{700,100},{520,100},{340,100},{160,100}
		};
	
	private Map<Byte,Image> operators = new HashMap<Byte,Image>();
	
	private OperatorListener listener = new OperatorListener();
	
	public OperatorView()
	{
		Image peng = UI.image("tray_word_peng.png");
		Image gang = UI.image("tray_word_gang.png");
		Image hu = UI.image("tray_hu.png");
		Image guo = UI.image("tray_guo.png");
		
		peng.setUserObject(GameTable.OPT_PENG);
		gang.setUserObject(GameTable.OPT_GANG);
		hu.setUserObject(GameTable.OPT_HU);
		guo.setUserObject(GameTable.OPT_GUO);
		
		operators.put(GameTable.OPT_PENG, peng);
		operators.put(GameTable.OPT_GANG, gang);
		operators.put(GameTable.OPT_HU, hu);
		operators.put(GameTable.OPT_GUO, guo);
		this.add();
	}
	
	private void add()
	{
		this.hide();
		for (Image child : operators.values())
		{
			child.setScale(0.75f);
			child.addListener(listener);
			this.addActor(child);
		}
	}
	
	public void hide()
	{
		Collection<Image> opts = operators.values();
		for (Image image : opts) 
			image.setVisible(false);
	}
	
	public void show(List<Operator> opts)
	{
		if(null == opts || opts.isEmpty())
			return;
		
		for (int i=0;i < opts.size();i++) 
		{
			Image image = operators.get((byte)(opts.get(i).getOpt()));
			image.setPosition(OPT_POS[i][0],OPT_POS[i][1]);
			image.setVisible(true);
			image.toFront();
		}
	}
	
	class OperatorListener extends ClickListener
	{
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			Actor target = event.getListenerActor();
			target.addAction(Actions.scaleTo(0.6f, 0.6f, 0.3f));
			return super.touchDown(event, x, y, pointer, button);
		}

		@Override
		public void clicked(InputEvent event, float x, float y) 
		{
			Actor target = event.getListenerActor();
			target.setScale(0.75f);
			GameApplication.I.getScreen(GameScreen.class).operator((Byte)target.getUserObject());
		}
		
	}
}
