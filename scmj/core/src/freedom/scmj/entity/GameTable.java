package freedom.scmj.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import freedom.scmj.screen.GameScreen;

/**
 * 牌桌
 * 
 * @author majl
 *
 */
public class GameTable {
	/**
	 * 游戏桌状态
	 * */
	public static final byte STATUS_INIT = 1, STATUS_START = 2,
			STATUS_PLAYING = 3, STATUS_FINISH = 4, STATUS_OVER = 5;

	/**
	 * 玩家操作状态 6-出牌后,等待其他玩家的响应
	 * */

	public static final byte OPT_PUT = 1, OPT_OUT = 2, OPT_PENG = 3,
			OPT_GANG = 4, OPT_HU = 5, OPT_WAIT = 6 ,OPT_GUO = 7;

	public static final String[] NAMES = { "刘德华", "郭富城", "张学友", "李俊基" };
	/** 牌桌位置数量 */
	public static final int TABLE_SEATS = 4;
	/** 牌数量 */
	public static final int CARD_COUNT = 108;
	/** 棋牌类型:1-麻将 */
	public static final int GAME_TYPE = 1;
	/** 洗牌次数 */
	public static final int SHUFFLE_TIME = 5;
	/** 出牌时间 */
	private int outCardTimeout = 1;
	/** 当前活动玩家座位号 */
	private int actionSeat = 0;
	/** 本家 */
	private GameUser selfUser;
	/** 牌桌状态 */
	private int status;
	/** 桌子牌组 */
	private List<Card> tableCard = new ArrayList<Card>(CARD_COUNT);
	/** ID用户表 */
	private LinkedHashMap<Integer, GameUser> users = new LinkedHashMap<Integer, GameUser>();
	/** 座位用户表 */
	private List<GameUser> seatUsers = new ArrayList<GameUser>(TABLE_SEATS);
	private GameScreen screen;

	public GameTable(GameScreen screen) {
		this.screen = screen;
		this.initCardGroup();
		this.initUser();
	}

	/**
	 * 初始化牌组
	 */
	private void initCardGroup() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++)
				// type
				for (int k = 1; k <= 9; k++)
					// value
					tableCard.add(new Card(j, k, i * 3 * 9 + j * 9 + k));
	}

	/**
	 * 初始化玩家信息
	 */
	private void initUser() {
		for (int i = 0; i < TABLE_SEATS; i++) {
			GameUser user = new GameUser();
			user.setName(NAMES[i]);
			user.getData().setSeat(i);
			user.setGold(10099);
			user.setUserId(1001001 + i);
			user.setSex(GameUser.SEX_MAN);
			users.put(user.getUserId(), user);
			seatUsers.add(user);
		}
		selfUser = seatUsers.get(0);
	}

	/**
	 * 洗牌
	 */
	private void shuffle() {
		for (int i = 0; i < SHUFFLE_TIME; i++) {
			Collections.shuffle(tableCard);
		}
	}

	/**
	 * 首次发牌,每一轮发牌数量
	 * */
	private static final int[] PUT_CARD_COUNT_EVERY_TURN = { 4, 4, 4, 1 };

	/**
	 * 发牌
	 */
	private void putCardOfFirst() {
		for (GameUser user : users.values()) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < PUT_CARD_COUNT_EVERY_TURN[i]; j++) {
					user.getData().getHandCard().add(tableCard.remove(0));
				}
			}
		}

		for (GameUser user : users.values()) {
			System.out.println("玩家 : " + user.getName());
			for (Card c : user.getData().getHandCard()) {
				System.out.println(c);
			}
		}
	}

	/**
	 * 开始游戏
	 * */
	public void startGame() {
		// 庄家
		actionSeat = 0;
		// 游戏桌状态(开始/发牌状态)
		status = STATUS_START;
		// 洗牌
		shuffle();
		// 发牌
		putCardOfFirst();
	}

	/**
	 * 当前行动者摸牌
	 * 
	 * @return
	 */
	public Card putCard() {
		return putCard(actionSeat);
	}

	/**
	 * 指定玩家摸牌
	 * */
	public Card putCard(int seat) {
		Card card = tableCard.remove(0);
		seatUsers.get(seat).getData().putCard(card);
		return card;
	}

	/**
	 * auto select one out card
	 * 
	 * @return
	 */
	public Card selectOneOutCard() 
	{
		GameData data = getActionUser().getData();
		Card lastPutCard = data.getLastPutCard();
		return lastPutCard == null ? data.removeCard() : lastPutCard;
	}

	public Collection<GameUser> getUsers() {
		return users.values();
	}

	public GameUser getUser(int userId) {
		return users.get(userId);
	}

	public GameData getGameData(int userId) {
		return users.get(userId).getData();
	}

	public int getTimeout() {
		return outCardTimeout;
	}

	public GameUser getActionUser() {
		return this.seatUsers.get(actionSeat);
	}

	public int getActionSeat() {
		return this.actionSeat;
	}

	public void setActionSeat(int actionSeat) {
		this.actionSeat = actionSeat;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isStatus(int status) {
		return this.status == status;
	}

	public void sortAll() {
		for (GameUser user : users.values()) {
			user.getData().sort();
		}
	}

	public void next() {
		actionSeat = actionSeat < TABLE_SEATS - 1 ? 1 + actionSeat : 0;
		if (this.seatUsers.get(actionSeat).getData().isHu())
			next();
	}

	public boolean isSelf() {
		return actionSeat == 0;
	}

	public boolean isRight() {
		return actionSeat == 1;
	}

	public boolean isTop() {
		return actionSeat == 2;
	}

	public boolean isLeft() {
		return actionSeat == 3;
	}

	public GameUser getSelfUser() {
		return this.selfUser;
	}

	/**
	 * 出牌(如果是新摸牌,返回新牌位置,否则返回-1)
	 * 
	 * @param card
	 */
	public MoveRange outCard(Card card) {
		GameData data = getActionUser().getData();
		List<Card> handCard = data.getHandCard();
		// 打出牌原来的位置
		int index = handCard.indexOf(card);
		// 从手牌中删除打出牌
		data.outCard(card);
		// 新摸的牌
		Card lastPutCard = data.getLastPutCard();
		// 本次出牌前是摸牌,并且打出的牌不是摸的牌
		if (lastPutCard != null && lastPutCard != card) 
		{
			//这时将摸的牌放入手牌中
			handCard.add(lastPutCard);
			data.sort();
			int newCardIndex = handCard.indexOf(lastPutCard);
			return new MoveRange(index, newCardIndex, handCard.size());
		}
		//碰牌打出
		else if(lastPutCard == null)
		{
			int newCount = handCard.size();
			if(index != newCount)
			return new MoveRange(index,newCount,newCount);
		}

		return null;
	}

	/**
	 * check operator after out card
	 * */
	public boolean checkOperator(Card outCard) {

		int hasOptCount = 0;
		GameUser lastUser = getActionUser();

		for (GameUser user : seatUsers) {
			if (user == lastUser || user.getData().isHu())
				continue;

			if (user.getData().hasOpt(outCard, false))
				hasOptCount++;
		}

		return hasOptCount > 0;
	}

	/**执行操作
	 * @param operator
	 * @return
	 */
	public Map<Integer,List<Card>> operator(byte operator) 
	{
		GameData data = selfUser.getData();
		//获取操作对象
		Operator opt = data.getOperator(operator);
		Card targetCard = opt.getTarget();
		
		if(operator == OPT_PENG)
			return data.peng(targetCard);
		else if(operator == OPT_GANG)
			return data.gang(targetCard);
		else if(operator == OPT_HU)
			data.hu(targetCard);
		
		return null;
	}
}
