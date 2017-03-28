package freedom.scmj.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import freedom.scmj.GameApplication;
import freedom.scmj.common.screen.BaseScreen;
import freedom.scmj.common.ui.ListView;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.ui.View;

public class MallScreen extends BaseScreen {

	public MallScreen(GameApplication game) {
		super(game);
		
		//创建背景
		View.create(UI.image("load_Bg.jpg")).center().addTo(stage);
		//创建商品背景
		View.create(UI.image("mall/bg2.png")).center().addTo(stage);
		
		createListView();
	}
	
	private void createListView()
	{
		Actor actor1 = UI.image("mall/item_bg.png");
		Actor actor2 = UI.image("mall/item_bg.png");
		Actor actor3 = UI.image("mall/item_bg.png");
		Actor actor4 = UI.image("mall/item_bg.png");
		Actor actor5 = UI.image("mall/item_bg.png");
		ListView<Actor> listView = new ListView<Actor>();
		listView.setScrollDirection(ListView.DIRECTION_LANDSCAPE);
		listView.setSize(1170,500);
		listView.setPosition(50, 100);
		listView.addItem(actor1);
		listView.addItem(actor2);
		listView.addItem(actor3);
		listView.addItem(actor4);
		listView.addItem(actor5);
		add(listView);
	}

	private ScrollPane scrollPane;
	
	@Override
	public void draw(float delta)
	{
		
	}

}
