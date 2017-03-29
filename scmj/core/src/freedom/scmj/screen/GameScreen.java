package freedom.scmj.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
import freedom.scmj.common.ui.View;
import freedom.scmj.entity.Card;
import freedom.scmj.entity.GameData;
import freedom.scmj.entity.GameTable;
import freedom.scmj.entity.GameUser;
import freedom.scmj.entity.MoveRange;
import freedom.scmj.entity.Operator;
import freedom.scmj.factory.CardFactory;
import freedom.scmj.factory.UserViewHelper;
import freedom.scmj.listener.CardListener;
import freedom.scmj.ui.GreatWall;
import freedom.scmj.ui.OperatorView;
import freedom.scmj.ui.StartGameView;
import freedom.scmj.ui.TimerCenterNew;
import freedom.scmj.ui.UserView;
import freedom.scmj.utils.AudioHelper;

public class GameScreen extends BaseScreen {

	public static final int STATE_FIRST_PUT_CARD = 1;
	public  int currentPutCardSeat;
	public  int currentPutCardRound = 0;
	private int currentState;
	private boolean canPut = true;
	private final int lastPutCardSeat = 3;
	
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
	private Map<Integer, UserView> userViewMap = new LinkedHashMap<Integer, UserView>();
	// 开始按钮
	private final Button startButton;
	// 牌桌中心计时器
	private final TimerCenterNew timer;
	// 长城
	private final GreatWall greatWall;
	// pointer
	private Actor pointer;
	// operator node
	private OperatorView operatorView;
	
	//新麻将
	private TextureAtlas roomAtlas;
	
	/**
	 * 定时器
	 * */
	ScheduledExecutorService scheduledService = Executors
			.newScheduledThreadPool(2);

	public GameScreen(GameApplication game) {
		super(game);
		//只作为延时用的ACTOR
		add(delayActor);
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
		roomAtlas  = UI.atlas("room.txt");
		// init game table
		this.table = new GameTable(this);
		// init self handcard listener
		this.cardListener = new CardListener();
		// add bg
		this.add(UI.image("room/sc_room_bg.jpg"));
		// add greatwall
		greatWall = (GreatWall) this.add(new GreatWall(prepareAtlas));
		// add pointer
		pointer = this.add(UI.image("tile-pointer", tileAtlas));
		pointer.setVisible(false);
		// add operator node
		operatorView = new OperatorView();
		this.add(operatorView);
		// add danji chang
		this.add(UI.pos(UI.image("prepare_watermark_gbsingle", prepareAtlas),800, 30));

		//显示用户头像
		drawUserView();

		// create timer
		timer = (TimerCenterNew) this.add(UI.center(new TimerCenterNew()));
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
	}

	private void drawUserView() {
		float[] userViewPos = { 20, 940, 610, 240 };
		Collection<GameUser> gameUsers = table.getUsers();
		UserView userView = null;
		for (GameUser user : gameUsers)
		{
			if (user.getData().getSeat() % 2 == 0) 
			{
				userView = UserViewHelper.createUpDownUser(user,userHeadAtlas);
				View.create(userView).middleX(userViewPos[user.getData().getSeat()]).addTo(stage);
				userViewMap.put(user.getUserId(), userView);
			} else {
				userView = UserViewHelper.createLeftRightUser(user,userHeadAtlas);
				View.create(userView).middleY( userViewPos[user.getData().getSeat()]).addTo(stage);
				userViewMap.put(user.getUserId(), userView);
			}
		}
	}

	private void startGame() 
	{
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
		playUserViewAnimation();
		// play first put card animation
		startFirstPutCard();
	}

	
	final List<Card> newCardList = new ArrayList<Card>();
	
	final Actor delayActor = new Actor();
	/**
	 * 本家发牌动画
	 * */
	private void selfFirstPutCard(int putCount, List<Card> temp) {
		int seat = 0;
		// 显示新牌(乱序)
		for (int j = 0; j < putCount; j++)
		{
			Card card = temp.get(j);
			CardFactory.newSelfHandCard(card, tileAtlas);
			card.addListener(cardListener);
			GameScreen.this.add(UI.middleY(card, UI.cx + j * 65));
		}
		// 排序是排的集合变量的指向,对象本身的位置是不会改变的
		List<Card> putedCardList = newCardList;// new
		// 添加新牌
		newCardList.addAll(temp);
		// 排序
		Collections.sort(newCardList);
		// 插入新牌
		for (Card card : temp) 
		{
			int index = newCardList.indexOf(card);
			card.addAction(Actions.moveTo(HAND_CARD_START_POS[seat][0] + (index) * HAND_CARD_WIDTH[seat], HAND_CARD_START_POS[seat][1], 0.5f));
		}
		// 移动旧牌
		for (Card card : putedCardList) 
		{
			int index = newCardList.indexOf(card);
			card.addAction(Actions.moveTo(HAND_CARD_START_POS[seat][0] + (index) * HAND_CARD_WIDTH[seat], HAND_CARD_START_POS[seat][1], 0.5f));
		}
		
		firstPutCardCallback(0.5f);
	}

	private void firstPutCardCallback(float delay)
	{
		delayActor.addAction(Actions.delay(delay, Actions.run(new Runnable() {
			
			public void run() 
			{
				if(currentPutCardSeat == lastPutCardSeat)
				{
					//说明这一轮的四位玩家的玩都已经发完了.可以进入下一轮
					//currentPutCardRound++;
					currentPutCardRound++;
					System.out.println("首家发完牌,round : " + currentPutCardRound + ":canPut :" + canPut + ",seat : " + currentPutCardSeat);
				}
				if(currentPutCardRound == 4)//四轮已经发牌完毕
					firstPutCardFinished();
				else{
					canPut = true;
					currentPutCardSeat = currentPutCardSeat == 3 ? 0 : ++currentPutCardSeat;
				}
			}
		})));
	}
	
	/**
	 * 首次发牌(每人13张)
	 * 
	 * @return
	 * */
	private void startFirstPutCard() 
	{
		this.currentState = STATE_FIRST_PUT_CARD;
		this.currentPutCardRound = 0;
		this.currentPutCardSeat = 0;
	}

	/** 开始游戏将用户头像移动到合适的位置 */
	private void playUserViewAnimation() {
		float[][] userNodePos_After_Start = { { 160, 160 }, { 1150, UI.cy },
				{ 160, 620 }, { 30, UI.cy } };
		int index = 0;
		for (UserView node : userViewMap.values()) 
		{
			node.addAction(Actions.moveTo(userNodePos_After_Start[index][0],
					userNodePos_After_Start[index][1], 0.5f));
			index++;
		}
	}

	private long lastActionTime;
	
	/** 定时任务 */
	private void schedule() 
	{
		if (table.isStatus(GameTable.STATUS_PLAYING)) 
		{
			GameData actionData = table.getActionUser().getData();
			if (actionData.isOpt(GameTable.OPT_PUT)) {
				GameScreen.this.putCard();
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
			if(System.currentTimeMillis() - lastActionTime >= 1000){
				timer.scheduler(-1);
				lastActionTime = System.currentTimeMillis();
			}
			
		}
	}

	private void firstPutCardFinished() {
		this.currentState = -1;
		this.lastActionTime = System.currentTimeMillis();
		// resort users handcard
		table.sortAll();
		// 发牌结束,进入游戏状态
		table.setStatus(GameTable.STATUS_PLAYING);
		// 摸牌
		table.getActionUser().getData().setOpt(GameTable.OPT_PUT);
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
			operatorView.show(opts);
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

	/**
	 * 第一次发牌的起始位置
	 * */
	public static final float[][] HAND_CARD_START_POS= {
		{80,5},{1100,505},{420,610},{130,510}
	};
	/**
	 * 新手牌插入的位置
	 * */
	public static final float[][] HAND_CARD_INSERT_POS = {
		{1160,20},{1100,550},{380,635},{130,125}
	};
	public static final float HAND_CARD_WIDTH[] = 
		{
			82,25,38,25
		};
	
	private void playPutCardAnimation(Card card) {
		if (table.isSelf()) {
			CardFactory.newSelfHandCard(card, tileAtlas);
			card.setPosition(1160, 20);
			card.addListener(cardListener);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isRight()) {
			CardFactory.newRightHandCard(card, tileAtlas);
			card.setPosition(1100, 580);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isTop()) {
			CardFactory.newTopHandCard(card, tileAtlas);
			card.setPosition(370, 625);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		} else if (table.isLeft()) {
			CardFactory.newLeftHandCard(card, tileAtlas);
			card.setPosition(130, 125);
			this.add(card);
			Tween.to(card, ActorAccessor.POS_XY, 0.5f).targetRelative(0, -15)
					.ease(Elastic.OUT).start(manager);
		}
	}

	private StartGameView startGroup;

	/**
	 * 游戏开始时显示的背景和文字动画
	 * */
	private void initStartGroup() {
		startGroup = new StartGameView();
		this.add(startGroup);
	}

	
	public void draw(float delta) 
	{
		manager.update(delta);
		if(currentState == STATE_FIRST_PUT_CARD){
			//首发状态
			// 发牌张数
			final Collection<GameUser> users = table.getUsers();
			int putCount = currentPutCardRound < 3 ? 4 : 1;
			final int count = currentPutCardRound;
			for (GameUser user : users) 
			{
				GameData data = user.getData();
				int seat = data.getSeat();
				if(seat != currentPutCardSeat)
					continue;
				if(!canPut)
					continue;
				canPut = false;
				List<Card> handCard = data.getHandCard();
				
				// 前3轮4张,最后一轮1张
				int countEnd = putCount == 1 ? count * 4 + 1 : (count + 1) * 4;
				List<Card> temp = handCard.subList(count * 4,countEnd);
				if (seat == 0) 
				{
					// 本家发牌动画
					selfFirstPutCard(putCount, temp);
				} else if (seat == 1) {
					// 右家发牌动画
					for (int j = 0; j < putCount; j++) {
						Card card = temp.get(j);
						CardFactory.newRightHandCard(card, tileAtlas);
						GameScreen.this.add(UI.pos(card, HAND_CARD_START_POS[seat][0],HAND_CARD_START_POS[seat][1] - HAND_CARD_WIDTH[seat] * (count * 4 + j)));
					}
					firstPutCardCallback(0.2f);
					
				} else if (seat == 2) {
					// 上家发牌动画
					for (int j = 0; j < putCount; j++) {
						Card card = temp.get(j);
						CardFactory.newTopHandCard(card, tileAtlas);
						GameScreen.this.add(UI.pos(card,HAND_CARD_START_POS[seat][0] + HAND_CARD_WIDTH[seat] * (count * 4 + j), HAND_CARD_START_POS[seat][1]));
					}
					firstPutCardCallback(0.2f);
				} else if (seat == 3) {
					// 左家发牌动画
					for (int j = 0; j < putCount; j++) {
						Card card = temp.get(j);
						CardFactory.newLeftHandCard(card, tileAtlas);
						GameScreen.this.add(UI.pos(card, HAND_CARD_START_POS[seat][0],HAND_CARD_START_POS[seat][1] - HAND_CARD_WIDTH[seat] * (count * 4 + j)));
					}
					firstPutCardCallback(0.2f);
				}
			}
			
			if (currentPutCardRound == 4)
			{
				// 发牌结束
				firstPutCardFinished();
			} 
		}
		else{
			// 游戏循环
			schedule();
		}
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

	public static final float[][] OUT_CARD_POS = {
		{},{},{},{}
	};
	
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
			
			targetX = ((newLine ? count - 10 : count) - 1) * 37 + 400;
			targetY = newLine ? 200 : 243;
			
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
			targetX = newLine ? 253 : 300;
			targetY = 490 - ((newLine ? count - 10: count) - 1) * 32;
			CardFactory.newLeftOutCard(showOutCard, tileAtlas);
			UI.pos(showOutCard, card.getX(), card.getY());
			showOutCard.addAction(Actions.sequence(Actions.moveTo(targetX,targetY,0.5f),Actions.run(new Runnable() {
				
				public void run() {
					showPointer(targetX, targetY);
				}
			})));
		} else if (table.isTop()) 
		{
			UI.middleX(showTipCard, 450);
			targetX = 880 - ((newLine ? count - 10 : count) - 1) * 37;
			targetY = newLine ? 447 : 490;
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
			targetX = newLine ? 1007 : 960;
			targetY = 430 - ((newLine ? count - 10: count) - 1) * 32;
			CardFactory.newRightOutCard(showOutCard, tileAtlas);
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
						GameScreen.this.remove(showTipCard);
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
		int seat = 0;
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
					moveCard.addAction(Actions.moveBy(-HAND_CARD_WIDTH[seat], 0, 0.5f));
			}
		}
		else
		{
			int marginLeftCount = data.getGroupCount();
			// 清除上一张摸的牌
			data.clearLastPutCard();
			if (begin == end) {
				if (begin == size - 1)
					newCard.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + HAND_CARD_WIDTH[seat] * (size - 1), 5, 0.5f));
				else {
					playInsertAnimation(end, newCard,marginLeftCount);
				}
			} else if (begin < end) {
				List<Card> moveList = handCard.subList(begin, end);
				for (Card moveCard : moveList) {
					moveCard.addAction(Actions.moveBy(-HAND_CARD_WIDTH[seat], 0, 0.5f));
				}
				if (size - 1 == end)
					newCard.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + HAND_CARD_WIDTH[seat] * (end), 5, 0.5f));
				else {
					playInsertAnimation(end, newCard,marginLeftCount);
				}

			} else if (begin > end) {
				List<Card> moveList = handCard.subList(end, begin + 1);
				for (Card moveCard : moveList) {
					moveCard.addAction(Actions.moveBy(HAND_CARD_WIDTH[seat], 0, 0.5f));
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
		int seat = 0;
		SequenceAction seq = Actions.sequence();
		seq.addAction(Actions.moveBy(0, 70, 0.3f));
		seq.addAction(Actions.moveTo(MARGIN_LEFT_BY_GROUP[marginLeftCount] + HAND_CARD_WIDTH[seat] * end, 75, 0.3f));
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
		int seat = 0;
		//隐藏操作选择
		operatorView.hide();
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
				//手牌中清除已碰牌,STAGE中删除
				for (Card card : removeCard)
					card.remove();
				//显示碰牌
				GameData data  = table.getSelfUser().getData();
				int groupCount = data.getGroupCount();
				List<Card> lastGroup = data.getGroupCard().get(groupCount - 1);
				for (int i = 0;i < lastGroup.size();i++) 
				{
					Card pengCard = CardFactory.newSelfPengGangCard(lastGroup.get(0));
					View.create(pengCard)
					.pos(80 + HAND_CARD_WIDTH[seat] * (i + (groupCount - 1) * 3), 
							HAND_CARD_START_POS[seat][1])
					.addTo(stage);
				}
				//移动剩余手牌位置.删除牌左边的牌右右移
				int removeIndex = operatorList.keySet().iterator().next();
				if(removeIndex > 0)
				{
					List<Card> fontCardList = table.getSelfUser().getData().subHandCard(removeIndex);
					for (Card card : fontCardList)
						card.addAction(Actions.moveBy(HAND_CARD_WIDTH[seat] * 2, 0,0.5f));
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
				//手牌中清除已杠牌
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
