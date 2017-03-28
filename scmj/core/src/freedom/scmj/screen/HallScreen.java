package freedom.scmj.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import freedom.scmj.GameApplication;
import freedom.scmj.common.screen.BaseScreen;
import freedom.scmj.common.ui.TouchListener;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.ui.View;

public class HallScreen extends BaseScreen {

	InputListener listener = new TouchListener() {
		@Override
		public void onClicked(Actor actor, float x, float y) 
		{
			HallScreen.this.onClicked(actor);
		}
	};
	
	
	public HallScreen(GameApplication game) 
	{
		super(game);
		createView();
	}
	
	View backgroud;
	View mallButton;
	View taskButton;
	View activityButton;
	View exchangeButton;
	View rankButton;
	View xzddButton;
	
	private void createView()
	{
		//大厅背景
		backgroud = View.create(UI.image("hall/hallBgMid.jpg")).addTo(stage);
		//创建底部菜单
		createBottomView();
		//创建左边菜单
		createLeftView();
		//创建右边菜单
		createRightView();
	}
	
	private void createRightView() {
		//血流成河
		View.create(UI.image("hall/btn_xlch.png")).pos(850, 450).addTo(stage).addClickListener(listener);
		//血战到底
		xzddButton = View.create(UI.image("hall/btn_xzdd.png")).pos(850, 300).addTo(stage).addClickListener(listener);
		//第三个为两个按钮组合起来的,背景
		View.create(UI.image("hall/btnBg.png")).pos(850, 150).addTo(stage);
		//左边比赛
		View.create(UI.image("hall/matchBtn.png")).pos(865, 168).addTo(stage).addClickListener(listener);
		//右边更多
		View.create(UI.image("hall/moreBtn.png")) .pos(1050, 168).addTo(stage).addClickListener(listener);
	}

	private void createLeftView() {
		//背景框
		View.create(UI.image("hall/btn_bg.png")).centerY(100).addTo(stage);
		//加入房间
		View.create(UI.image("hall/btn_addRoom.png")).pos(122, 370).addTo(stage).addClickListener(listener);
		//加入房间右边的LOGO熊猫
		View.create(UI.image("hall/logo1.png")).pos(322, 370).addTo(stage);
		//创建房间
		View.create(UI.image("hall/btn_createRoom.png")).pos(122, 190).addTo(stage).addClickListener(listener);
		//创建房间左边的LOGO熊猫
		View.create(UI.image("hall/log2.png")).pos(122, 190).addTo(stage);
	}

	private void createBottomView() {
		//背景
		View.create(UI.image("hall/BottomBg.png")).centerX(0).addTo(stage);
		//商城
		mallButton = View.create(UI.image("hall/mall.png")).pos(160, 10).addTo(stage).addClickListener(listener);
		//任务
		taskButton = View.create(UI.image("hall/task.png")).pos(360, 10).addTo(stage).addClickListener(listener);
		//活动
		activityButton = View.create(UI.image("hall/activity.png")).pos(560, 10).addTo(stage).addClickListener(listener);
		//兑换
		exchangeButton = View.create(UI.image("hall/exchange.png")).pos(760, 10).addTo(stage).addClickListener(listener);
		//排行
		rankButton = View.create(UI.image("hall/rank.png")).pos(960, 10).addTo(stage).addClickListener(listener);
	}

	@Override
	public void draw(float delta)
	{
		
	}

	public void onClicked(Actor actor)
	{
		Object tag = actor.getUserObject();
		if(tag != null){
			if(mallButton.isHit(tag))
			{
				System.out.println("点击了商城");
				game.setScreen(new MallScreen(game));
			}
			else if(taskButton.isHit(tag))
			{
				System.out.println("点击了任务");
			}
			else if(xzddButton.isHit(tag)){
				game.setScreen(new SelectRoomScreen(game));
			}
		}
	}
}
