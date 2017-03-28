package freedom.scmj.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import freedom.scmj.GameApplication;
import freedom.scmj.entity.Card;
import freedom.scmj.entity.GameTable;
import freedom.scmj.screen.GameScreen;

public class CardListener extends ClickListener 
{
	private Card selectedCard;
	
	private GameScreen gameScreen;
	
	public CardListener()
	{
		gameScreen = GameApplication.I.getScreen(GameScreen.class);
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) 
	{
		Card target = (Card) event.getListenerActor();
		
		if(null == selectedCard)
		{
			//初始选牌
			selectedCard = target;
			target.addAction(Actions.moveBy(0, 30,0.06f));
		}
		else if(selectedCard == target)
		{
			GameTable table = gameScreen.getTable();
			if(table.getActionUser() == table.getSelfUser())
			//出牌
			gameScreen.outCard(target);
		}else
		{
			//切换选牌
			selectedCard.addAction(Actions.moveBy(0, -30,0.06f));
			target.addAction(Actions.moveBy(0, 30,0.06f));
			selectedCard = target;
		}
		
	}
}
