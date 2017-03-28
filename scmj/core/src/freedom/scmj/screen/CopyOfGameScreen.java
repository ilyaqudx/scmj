package freedom.scmj.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenPaths;
import aurelienribon.tweenengine.equations.Elastic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import freedom.scmj.GameApplication;
import freedom.scmj.common.screen.BaseScreen;
import freedom.scmj.common.tween.accessor.ActorAccessor;
import freedom.scmj.common.ui.UI;
import freedom.scmj.entity.Card;
import freedom.scmj.entity.GameData;
import freedom.scmj.entity.GameTable;
import freedom.scmj.entity.GameUser;
import freedom.scmj.entity.MoveRange;
import freedom.scmj.entity.Operator;
import freedom.scmj.factory.CardFactory;
import freedom.scmj.factory.UserViewHelper;
import freedom.scmj.listener.CardListener;
import freedom.scmj.ui.GameStartNode;
import freedom.scmj.ui.GreatWall;
import freedom.scmj.ui.OperatorView;
import freedom.scmj.ui.TimerCenter;
import freedom.scmj.ui.UserNode;
import freedom.scmj.utils.AudioHelper;

public class CopyOfGameScreen extends BaseScreen {

	private TextureAtlas prepareAtlas;
	private TextureAtlas dengluAtlas;
	private TextureAtlas tileAtlas;
	private TextureAtlas userHeadAtlas;
	private TextureAtlas timerAtlas;

	private TweenManager manager;
	// 本家手牌监听器
	private CardListener cardListener;
	// 牌桌
	private GameTable table;
	/** 用户信息节点 */
	private Map<Integer, UserNode> userNodeMap = new LinkedHashMap<Integer, UserNode>();
	// 开始按钮
	private final Button startButton;
	// 牌桌中心计时器
	private final TimerCenter timer;
	// 长城
	private final GreatWall greatWall;
	// pointer
	private Actor pointer;
	// operator node
	private OperatorView operatorNode;
	/**
	 * 定时器
	 * */
	ScheduledExecutorService scheduledService = Executors
			.newScheduledThreadPool(2);

	public CopyOfGameScreen(GameApplication game) {
		super(game);

		//播放背景音乐
		AudioHelper.playBackGround();
		
		// set tween option
		manager = new TweenManager();
		Tween.setCombinedAttributesLimit(10);
		Tween.setWaypointsLimit(10);
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// load resource
		prepareAtlas = UI.atlas("gameprepareImage");
		dengluAtlas = UI.atlas("dengluImg");
		tileAtlas = UI.atlas("tileImage");
		userHeadAtlas = UI.atlas("userHead");
		timerAtlas = UI.atlas("timerImage");
		// init game table
		this.table = new GameTable(null);
		// init self handcard listener
		this.cardListener = new CardListener();
		// add bg
		this.add(UI.image("prepare_zhuomian.jpg"));
		// add greatwall
		greatWall = (GreatWall) this.add(new GreatWall(prepareAtlas));
		// add pointer
		pointer = this.add(UI.image("tile-pointer", tileAtlas));
		pointer.setVisible(false);
		// add operator node
		operatorNode = new OperatorView();
		this.add(operatorNode);
		// add danji chang
		this.add(UI.pos(UI.image("prepare_watermark_gbsingle", prepareAtlas),800, 30));

		/*// 来一张本家麻将背景
		Actor majiang = this.add(UI.originCenter(UI.pos(
				UI.image("denglu-majiang", dengluAtlas), 100, 100)));

		Timeline.createSequence()
				.push(Tween.to(majiang, ActorAccessor.POS_XY, 0.5f)
						.targetRelative(0, -30).ease(Back.OUT))
				.push(Tween.to(majiang, ActorAccessor.SCALE_XY, 0.3f).target(1,
						2f))
				.push(Tween.set(majiang, ActorAccessor.OPACITY).target(0))
				.start(manager);*/

		// 创建用户信息
		Collection<GameUser> gameUsers = table.getUsers();
		UserNode userNode = null;
		float[] userNodePos = { 20, 840, 510, 80 };
		for (GameUser user : gameUsers) {
			if (user.getData().getSeat() % 2 == 0) {
				userNode = UserViewHelper.createUpDownUser(user,
						userHeadAtlas);
				userNodeMap.put(user.getUserId(), userNode);
				this.add(UI.middleX(userNode, userNodePos[user.getData()
						.getSeat()]));
			} else {
				userNode = UserViewHelper.createLeftRightUser(user,
						userHeadAtlas);
				userNodeMap.put(user.getUserId(), userNode);
				this.add(UI.middleY(userNode, userNodePos[user.getData()
						.getSeat()]));
			}

		}

		// create timer
		timer = (TimerCenter) this.add(UI.center(new TimerCenter(timerAtlas)));
		timer.setVisible(false);

		// init start group
		this.initStartGroup();
		// start button
		startButton = (Button) this.add(UI.center(UI
				.button("resultbox_btn_jixu.png", "resultbox_btn_jixu_sel.png",
						"start")));
		// add start buuton listener
		startButton.addListener(new ClickListener() {
			
			public void clicked(InputEvent event, float x, float y) {
				startGame();
			}

		});
		/*
		 * new Thread(new Runnable() {
		 * 
		 *  public void run() { while(true) { try { Thread.sleep(3000);
		 * } catch (InterruptedException e) { e.printStackTrace(); }
		 * timer.scheduler(-3); startGroup.playAnimation(manager); } }
		 * }).start();
		 */
	}

	private void startGame() {
		// 先处理数据
		table.startGame();
		startButton.setVisible(false);
		timer.setVisible(true);
		// time out
		timer.start(table.getTimeout());
		// hide greatwall
		greatWall.hide();
		// show start group
		startGroup.playAnimation(manager);
		// show userNode animation
		playUserNodeAnimation();
		// play first put card animation
		ScheduledFuture<?> future = playFirstPutCardAnimation();

		/*
		 * if(future.isDone()) schedule();//开始定时任务
		 */}

	/**
	 * 第一次发牌动画
	 * 
	 * @return
	 * */
	private ScheduledFuture<?> playFirstPutCardAnimation() {
		final Collection<GameUser> users = table.getUsers();
		// 发3轮牌
		Runnable task = new Runnable() {
			private int count = 0;
			final List<Card> newCardList = new ArrayList<Card>();

			
			public void run() {
				// 发牌张数
				int putCount = count < 3 ? 4 : 1;
				if (count < 4) {
					for (GameUser user : users) {
						GameData data = user.getData();
						int seat = data.getSeat();
						List<Card> handCard = data.getHandCard();
						// 前3轮4张,最后一轮1张
						List<Card> temp = handCard
								.subList(count * 4,
										putCount == 1 ? count * 4 + 1
												: (count + 1) * 4);
						if (seat == 0) {
							// 本家发牌动画
							selfFirstPutCard(putCount, temp);
						} else if (seat == 1) {
							// 右家发牌动画
							for (int j = 0; j < putCount; j++) {
								Card card = temp.get(j);
								CardFactory.newRightHandCard(card, tileAtlas);
								CopyOfGameScreen.this.add(UI.pos(card, 890,
										455 - 25 * (count * 4 + j)));
							}
						} else if (seat == 2) {
							// 上家发牌动画
							for (int j = 0; j < putCount; j++) {
								Card card = temp.get(j);
								CardFactory.newTopHandCard(card, tileAtlas);
								CopyOfGameScreen.this.add(UI.pos(card,
										360 + 38 * (count * 4 + j), 520));
							}
						} else if (seat == 3) {
							// 左家发牌动画
							for (int j = 0; j < putCount; j++) {
								Card card = temp.get(j);
								CardFactory.newLeftHandCard(card, tileAtlas);
								CopyOfGameScreen.this.add(UI.pos(card, 100,
										510 - 25 * (count * 4 + j)));
							}
						}
					}
					count++;
				} else {
					// 发牌结束
					firstPutCardFinished();
					// 停止当前线程
					Thread.currentThread().stop();
				}
			}

			/**
			 * 本家发牌动画
			 * */
			private void selfFirstPutCard(int putCount, List<Card> temp) {

				// 显示新牌(乱序)
				for (int j = 0; j < putCount; j++) {
					Card card = temp.get(j);
					CardFactory.newSelfHandCard(card, tileAtlas);
					card.addListener(cardListener);
					CopyOfGameScreen.this.add(UI.middleY(card, UI.cx + j * 65));
				}
				// 排序是排的集合变量的指向,对象本身的位置是不会改变的
				List<Card> putedCardList = newCardList;// new
				// ArrayList<Card>(newCardList);
				// 添加新牌
				newCardList.addAll(temp);
				// 排序
				Collections.sort(newCardList);
				// 插入新牌
				for (Card card : temp) {
					int index = newCardList.indexOf(card);
					card.addAction(Actions.moveTo(80 + (index) * 58, 5, 0.5f));
				}
				// 移动旧牌
				for (Card card : putedCardList) {
					int index = newCardList.indexOf(card);
					card.addAction(Actions.moveTo(80 + (index) * 58, 5, 0.5f));
				}
			}
		};

		return scheduledService.scheduleWithFixedDelay(task, 10, 700,
				TimeUnit.MILLISECONDS);
	}

	/** 开始游戏将用户头像移动到合适的位置 */
	private void playUserNodeAnimation() {
		float[][] userNodePos_After_Start = { { -420, 80 }, { 80, -30 },
				{ -300, 0 }, { -70, 0 } };
		int index = 0;
		for (UserNode node : userNodeMap.values()) {
			node.addAction(Actions.moveBy(userNodePos_After_Start[index][0],
					userNodePos_After_Start[index][1], 0.5f));
			index++;
		}
	}

	/** 定时任务 */
	private void schedule() {
		scheduledService.scheduleWithFixedDelay(new Runnable() {

			
			public void run() {
				if (table.isStatus(GameTable.STATUS_PLAYING)) {
					GameData actionData = table.getActionUser().getData();
					if (actionData.isOpt(GameTable.OPT_PUT)) {
						CopyOfGameScreen.this.putCard();
					} else if (actionData.isOpt(GameTable.OPT_OUT)) {
						if (timer.isTimeout()) {
							autoOutCard();
						}
					} else if (actionData.isOpt(GameTable.OPT_PENG)) {

					} else if (actionData.isOpt(GameTable.OPT_GANG)) {

					} else if (actionData.isOpt(GameTable.OPT_HU)) {

					} else if (actionData.isOpt(GameTable.OPT_WAIT)) {
						if (timer.isTimeout()) {
							autoWaitFinished();
						}
					}
					timer.scheduler(-1);
				}
			}
		}, 50, 1000, TimeUnit.MILLISECONDS);
	}

	private void firstPutCardFinished() {
		// resort users handcard
		table.sortAll();
		// 发牌结束,进入游戏状态
		table.setStatus(GameTable.STATUS_PLAYING);
		// 摸牌
		table.getActionUser().getData().setOpt(GameTable.OPT_PUT);
		// 游戏循环
		schedule();
	}

	// 摸牌
	private void putCard() {
		// 重新计时
		timer.resetTime();
		// 闪烁灯
		timer.blink(table.getActionSeat());
		// 摸的牌
		Card card = table.putCard();
		// 播放动画
		playPutCardAnimation(card);
		// 切换状态到打牌状态
		GameData data = table.getActionUser().getData();
		data.setOpt(GameTable.OPT_OUT);
		// 显示可操作提示
		showOperators(data.getOperators());
	}

	private void autoOutCard() {
		System.out.println("打牌超时,托管出牌.");
		System.out.println("下一位玩家摸牌");
		// out card
		Card outCard = table.selectOneOutCard();
		outCard(outCard);
	}

	private void outCardFinished(Card outCard) {
		//清除上一张摸牌
		table.getActionUser().getData().clearLastPutCard();
		// table check opts
		boolean hasOpt = table.checkOperator(outCard);
		System.out.println("outcardfinished check opt,hasopt = " + hasOpt);
		if (hasOpt) {
			timer.resetTime();
			table.getActionUser().getData().setOpt(GameTable.OPT_WAIT);
			//check self opts
			List<Operator> opts = table.getSelfUser().getData().getOperators();
			operatorNode.show(opts);
		} else {
			// next action
			table.next();
			System.out.println("下一个玩家座位号 = " + table.getActionSeat());
			// putcard
			putCard();
		}
	}

	private void autoWaitFinished() {
		// hide users opt option
		table.next();
		putCard();
	}

	private void showOperators(List<Operator> opts) {
		if (null != opts && !opts.isEmpty()) {
			for (Operator operator : opts) {
				System.out.println("可以操作  : " + operator);
			}
		}
	}

	private void playPutCardAnimation(Card card) {
		if (table.isSelf()) {
			CardFactory.newSelfHandCard(card, tileAtlas);
			card.setPosition(850, 20);
			card.addListener(cardListener);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isRight()) {
			CardFactory.newRightHandCard(card, tileAtlas);
			card.setPosition(890, 528);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isTop()) {
			CardFactory.newTopHandCard(card, tileAtlas);
			card.setPosition(315, 535);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isLeft()) {
			CardFactory.newLeftHandCard(card, tileAtlas);
			card.setPosition(100, 185);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		}
	}

	private GameStartNode startGroup;

	/**
	 * 游戏开始时显示的背景和文字动画
	 * */
	private void initStartGroup() {
		startGroup = new GameStartNode();
		this.add(startGroup);
	}

	
	public void draw(float delta) {
		manager.update(delta);
	}

	public TextureAtlas getTileAtlas() {
		return tileAtlas;
	}

	/**
	 * 出牌
	 * 
	 * @param card
	 */
	public void outCard(Card card) {
		GameUser actionUser = table.getActionUser();
		GameData data = actionUser.getData();
		List<Card> handCard = data.getHandCard();
		// remove handcard
		MoveRange move = table.outCard(card);
		// clear texture
		this.remove(card);
		// move animation
		if (move != null) {
			showSelfHandCardMove(data, handCard, move);
		}
		// show out card anim
		this.playOutCardAnimation(card);
		// out card finished
		outCardFinished(card);

		System.out.println("==========TURN========================");
	}

	private void playOutCardAnimation(Card card) {
		int count = table.getActionUser().getData().getOutCard().size();
		boolean newLine = count > 10;
		final float targetX,targetY;
		System.out.println("play out card anim ,card = " + card);
		Card showOutCard = card.copyProperties();
		final Group showTipCard = CardFactory.newOutTipCard(card.copyProperties(), tileAtlas);
		this.add(showOutCard);
		this.add(showTipCard);
		//play audio
		AudioHelper.playCardAudio(card);
		if (table.isSelf()) {
			UI.middleX(showTipCard, 200);
			CardFactory.newSelfTopOutCard(showOutCard, tileAtlas);
			UI.pos(showOutCard, card.getX(), card.getY());
			
			targetX = ((newLine ? count - 10 : count) - 1) * 37 + 300;
			targetY = newLine ? 120 : 163;
			
			Timeline.createSequence()
			.push(Tween.to(showOutCard, ActorAccessor.POS_XY, 0.5f)
					.path(TweenPaths.linear).waypoint(750, 80)
					.waypoint(450, 150)
					.target(targetX,targetY))
			.push(Tween.call(new TweenCallback() {
				
				public void onEvent(int arg0, BaseTween<?> arg1) {
					System.out.println("run callback");
					//show pointer
					showPointer(targetX, targetY);
				}
			}))
			.start(manager);
			
		} else if (table.isLeft()) 
		{
			UI.middleY(showTipCard, 200);
			targetX = newLine ? 153 : 200;
			targetY = 460 - ((newLine ? count - 10: count) - 1) * 32;
			CardFactory.newLeftRightOutCard(showOutCard, tileAtlas);
			UI.pos(showOutCard, card.getX(), card.getY());
			showOutCard.addAction(Actions.sequence(Actions.moveTo(targetX,targetY,0.5f),Actions.run(new Runnable() {
				
				public void run() {
					showPointer(targetX, targetY);
				}
			})));
		} else if (table.isTop()) 
		{
			UI.middleX(showTipCard, 450);
			targetX = 680 - ((newLine ? count - 10 : count) - 1) * 37;
			targetY = newLine ? 387 : 430;
			CardFactory.newSelfTopOutCard(showOutCard, tileAtlas);
			UI.pos(showOutCard, card.getX(), card.getY());
			showOutCard.addAction(Actions.sequence(Actions.moveTo(targetX,targetY,0.5f),Actions.run(new Runnable() {
				
				
				public void run() {
					showPointer(targetX, targetY);
				}
			})));
		} else if (table.isRight())
		{
			UI.middleY(showTipCard, 750);
			targetX = newLine ? 777 : 730;
			targetY = 430 - ((newLine ? count - 10: count) - 1) * 32;
			CardFactory.newLeftRightOutCard(showOutCard, tileAtlas);
			UI.pos(showOutCard, card.getX(), card.getY());
			showOutCard.addAction(Actions.sequence(Actions.moveTo(targetX,targetY,0.5f),Actions.run(new Runnable() {
				
				
				public void run() {
					showPointer(targetX, targetY);
				}
			})));
		}
		
		showTipCard.addAction(Actions.sequence(Actions.fadeIn(0.2f),Actions.delay(1),Actions.fadeOut(0.3f),
				Actions.run(new Runnable() {
					
					
					public void run() {
						CopyOfGameScreen.this.remove(showTipCard);
					}
				})));
	}
	
	private void showPointer(float x,float y)
	{
		//暂时将打完牌后的牌声音放在这儿
		AudioHelper.playAudio(AudioHelper.OUT_CARD);
		if(!pointer.isVisible())
			pointer.setVisible(true);
		pointer.clearActions();
		pointer.setZIndex(1000);
		pointer.setPosition(x + 3, y + 50);
		pointer.addAction(Actions.forever(
				Actions.sequence(
						Actions.moveBy(0, -10,0.5f),
						Actions.moveBy(0, 10,0.5f)
						)));
	}

	
	/**
	 * 本家手牌左边距(根据碰/杠情况计算)
	 * */
	private static final float[] MARGIN_LEFT_BY_GROUP = {80,196,312,428,544};
	
	/**
	 * show self handcard animation after out card
	 * */
	private void showSelfHandCardMove(GameData data, List<Card> handCard,
			MoveRange move) {
		// 显示移动动画(4种情况,打首补首,打尾补尾,打前补后,打后补前)
		int begin = move.getBegin();
		int end = move.getEnd();
		int size = move.getSize();
		// 获取新牌
		Card newCard = data.getLastPutCard();
		if(null == newCard)
		{
			if (begin < end) 
			{
				List<Card> moveList = handCard.subList(begin, end);
				for (Card moveCard : moveList) 
					moveCard.addAction(Actions.moveBy(-58, 0, 0.5f));
			}
		}
		else
		{
			int marginLeftCount = data.getGroupCount();
			// 清除上一张摸的牌
			data.clearLastPutCard();
			if (begin == end) {
				if (begin == size - 1)
					newCard.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + 58 * (size - 1), 5, 0.5f));
				else {
					playInsertAnimation(end, newCard,marginLeftCount);
				}
			} else if (begin < end) {
				List<Card> moveList = handCard.subList(begin, end);
				for (Card moveCard : moveList) {
					moveCard.addAction(Actions.moveBy(-58, 0, 0.5f));
				}
				if (size - 1 == end)
					newCard.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + 58 * (end), 5, 0.5f));
				else {
					playInsertAnimation(end, newCard,marginLeftCount);
				}

			} else if (begin > end) {
				List<Card> moveList = handCard.subList(end, begin + 1);
				for (Card moveCard : moveList) {
					moveCard.addAction(Actions.moveBy(58, 0, 0.5f));
				}
				playInsertAnimation(end, newCard,marginLeftCount);
			}
		}
	}

	/**
	 * 播放新牌入列动画
	 * 
	 * @param end
	 * @param newCard
	 */
	private void playInsertAnimation(int end, Card newCard,int marginLeftCount) {
		SequenceAction seq = Actions.sequence();
		seq.addAction(Actions.moveBy(0, 70, 0.3f));
		seq.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + 58 * end, 75, 0.3f));
		seq.addAction(Actions.moveBy(0, -70, 0.3f));
		newCard.addAction(seq);
	}
	
	public GameTable getTable()
	{
		return table;
	}

	/**玩家选择的操作(碰/杠/胡/过)
	 * @param operator
	 */
	public void operator(byte operator) 
	{
		//隐藏操作选择
		operatorNode.hide();
		if(operator != GameTable.OPT_GUO)
		{
			//执行逻辑处理
			Map<Integer,List<Card>> operatorList = table.operator(operator);
			if(operator == GameTable.OPT_PENG)
			{
				//播放碰音效
				AudioHelper.playAudio(AudioHelper.PENG);
				//播放碰动画
				playPengOrGangAnimation(operator);
				//获得删除的牌
				List<Card> removeCard = operatorList.values().iterator().next();
				//手牌中清除已碰牌
				for (Card card : removeCard)
					card.remove();
				//移动剩余手牌位置.删除牌左边的牌右右移
				int removeIndex = operatorList.keySet().iterator().next();
				if(removeIndex > 0)
				{
					List<Card> fontCardList = table.getSelfUser().getData().subHandCard(removeIndex);
					for (Card card : fontCardList)
						card.addAction(Actions.moveBy(116, 0,0.5f));
				}
				//切换活动玩家
				table.setActionSeat(table.getSelfUser().getData().getSeat());
				table.getActionUser().getData().setOpt(GameTable.OPT_OUT);
				//时间重置
				timer.resetTime();
				timer.blink(table.getActionSeat());
				
			}
			else if(operator == GameTable.OPT_GANG)
			{
				AudioHelper.playAudio(AudioHelper.GANG);
				//手牌中清除已碰牌
				List<Card> removeCard = operatorList.values().iterator().next();
				for (Card card : removeCard)
					card.remove();
			}
			else if(operator == GameTable.OPT_HU)
			{
				AudioHelper.playAudio(AudioHelper.HU);
				//活动玩家转移
				table.setActionSeat(table.getSelfUser().getData().getSeat());
				table.next();
				timer.resetTime();
				timer.blink(table.getActionSeat());
			}
		}
	}

	/**
	 * 播放碰/杠动画
	 * */
	private void playPengOrGangAnimation(byte operator) 
	{
		
	}
}
