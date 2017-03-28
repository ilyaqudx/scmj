package freedom.scmj.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freedom.scmj.utils.CardHelper;

public class GameData
{
    private int	seat;

    private List<Card> handCard = new ArrayList<Card>();

    private List<Card> outCard  = new ArrayList<Card>();
    
    private List<List<Card>> groupCard = new ArrayList<List<Card>>();

    /**
     * 操作码
     * */
    private int	opt;
    /**
     * 胡标记
     * */
    private boolean    hu;
    /**
     * 可操作集合
     * */
    private List<Operator> operators = new ArrayList<Operator>();

    public boolean isHu()
    {
	return hu;
    }

    public void setHu(boolean hu)
    {
	this.hu = hu;
    }

    private Card lastPutCard;

    public Card getLastPutCard()
    {
	return lastPutCard;
    }

    public void clearLastPutCard()
    {
	this.lastPutCard = null;
    }

    public int getOpt()
    {
	return opt;
    }

    public void setOpt(int opt)
    {
	this.opt = opt;
    }

    public boolean isOpt(int opt)
    {
	return this.opt == opt;
    }

    public int getSeat()
    {
	return seat;
    }

    public void setSeat(int seat)
    {
	this.seat = seat;
    }

    public void setHandCard(List<Card> handCard)
    {
	this.handCard = handCard;
    }
    
    public List<Card> getHandCard()
    {
	return this.handCard;
    }

    public List<Card> getOutCard()
    {
	return this.outCard;
    }

    public void putCard(Card card)
    {
	hasOpt(card,true);
	this.lastPutCard = card;
    }
    
    public void addHandCard(Card card)
    {
	handCard.add(card);
    }

    public void addOutCard(Card card)
    {
	outCard.add(card);
    }
    
    public Card removeCard()
    {
	return handCard.remove(handCard.size()-1);
    }

    public List<Operator> getOperators()
    {
        return operators;
    }
    
    public void clearOperators()
    {
	this.operators.clear();
    }

    public void sort()
    {
	Collections.sort(handCard);
    }
    
    /**
     * 摸牌/打牌后可做的操作(杠/胡)
     * */
    public boolean hasOpt(Card card,boolean selfCard)
    {
	//clear last operators
	if(!operators.isEmpty())
	    clearOperators();
	if(isHu(card))
	{
	    Operator opt = new Operator();
	    opt.setOpt(GameTable.OPT_HU);
	    opt.setTarget(card);
	    opt.setSelfCard(selfCard);
	    operators.add(opt);
	}
	if(isGang(card))
	{
	    Operator opt = new Operator();
	    opt.setOpt(GameTable.OPT_GANG);
	    opt.setTarget(card);
	    opt.setSelfCard(selfCard);
	    operators.add(opt);
	}
	if(!selfCard && isPeng(card))
	{
	    Operator opt = new Operator();
	    opt.setOpt(GameTable.OPT_PENG);
	    opt.setTarget(card);
	    opt.setSelfCard(selfCard);
	    operators.add(opt);
	}
	
	return !operators.isEmpty();
    }
    
    public boolean isHu(Card card)
    {
	return CardHelper.isHu(handCard, card);
    }
    
    public boolean isPeng(Card card)
    {
	return CardHelper.isPeng(handCard, card);
    }

    public boolean isGang(Card card)
    {
	return CardHelper.isGang(handCard, card);
    }
    
    public static void main(String[] args)
    {
	List<Card> cards = new ArrayList<Card>(14);
	
	int[][] group = {
		{1,2},{1,2},{1,2},
		{1,3},{1,4},{1,4},
		{1,5},{1,5},{1,6},
		{2,4},{2,5},{2,6},
		{2,8}
	};
	
	for (int i = 0; i < 13; i++)
	{
	    Card c = new Card(group[i][0],group[i][1],i);
	    cards.add(c);
	}
	
	Collections.shuffle(cards);
	
	for (Card c : cards)
	{
	    System.out.println(c);
	}
	
	long s1 = System.currentTimeMillis();
	boolean hu = CardHelper.isHu(cards,new Card(2,8,14));
	System.out.println(System.currentTimeMillis() - s1);
	System.out.println("hu : " + hu);
    }

    public void outCard(Card card)
    {
	handCard.remove(card);
	addOutCard(card);
    }
    
    public Operator getOperator(byte opt)
    {
		for (Operator operator : operators) 
		{
			if(operator.getOpt() == opt)
				return operator;
		}
		return null;
    }
    
    /**删除指定牌
     * @param target
     * @param count 数量
     * @return Map<Integer,List<Card>> 返回map是因为,场景需要移动牌,记录了删除牌的位置
     */
    private Map<Integer,List<Card>> removeTargetCard(Card target,int count)
    {
    	int size = handCard.size();
    	List<Card> removeList = new ArrayList<Card>(count);
    	Map<Integer,List<Card>> result = new HashMap<Integer, List<Card>>();
    	for (int i = size - 1; i >= 0 ; i--) 
    	{
    		if(Card.isSame(target, handCard.get(i)))
    		{
    			count--;
    			removeList.add(handCard.remove(i));
    			if(count == 0)
    			{
    				result.put(i, removeList);
    				break;
    			}
    		}
		}
    	return result;
    }
    
	/**处理碰(删除手牌并添加进已碰牌组)
	 * @param target
	 * @return
	 */
	public Map<Integer,List<Card>> peng(Card target) 
	{
		Map<Integer,List<Card>> removeList = removeTargetCard(target, 2);
		List<Card> groupItem = new ArrayList<Card>(2);
		for (List<Card> list : removeList.values())
			for (Card card : list)
				groupItem.add(card.copyProperties());
		groupCard.add(groupItem);
		
		return removeList;
	}
	/**处理杠(删除手牌并添加进已杠牌组)
	 * @param target
	 * @return
	 */
	public Map<Integer,List<Card>> gang(Card target) 
	{
		Map<Integer,List<Card>> removeList = removeTargetCard(target, 3);
		List<Card> groupItem = new ArrayList<Card>(3);
		for (List<Card> list : removeList.values())
			for (Card card : list)
				groupItem.add(card.copyProperties());
		groupCard.add(groupItem);
		
		return removeList;
	}
	public void hu(Card targetCard) 
	{
		//标记玩家已胡
		hu = true;
	}
	
	public List<Card> subHandCard(int from,int to)
	{
		return handCard.subList(from,to);
	}
	
	public List<Card> subHandCard(int to)
	{
		return handCard.subList(0, to);
	}
	
	public int getGroupCount()
	{
		return groupCard.size();
	}
}
