package freedom.scmj.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import freedom.scmj.common.ui.UI;
import freedom.scmj.entity.Card;

public class CardFactory
{
	
	public static final Card newSelfHandCard(Card card, TextureAtlas atlas)
	{
		// create card bg sprite
		Image cardBackground = null;
		try {
			//此处加载报错。NO GLCONTEXT INFO
			//单独开的一个线程，没有在UI线程.但为什么用atlas加载则可以。是因为之前已经把图片集加载进来了嘛?
			cardBackground = UI.image("room/front_block_2.png");
			// set card group size
			card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
			//card.setScale(0.8f);
			// add card background to parent
			card.addActor(cardBackground);
			// create value sprite
			Actor value = UI.center(UI.image("room/own_block_0x" + card.getString() + ".png"), card);
			value.setY(value.getY() - 10);
			card.addActor(value);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return card group
		return card;
	}
	
    /**
     * 创建本家手牌
     * 
     * @param card
     * @param atlas
     * @return
     */
    /*public static final Card newSelfHandCard(Card card, TextureAtlas atlas)
    {
	// create card bg sprite
	Image cardBackground = UI.image("tileBase-me-1", atlas);
	// set card group size
	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
	card.setScale(0.8f);
	// add card background to parent
	card.addActor(cardBackground);
	// create value sprite
	card.addActor(UI.middleX(
		UI.image(
			"tile-me-" + Card.getTypeString(card.getType())
				+ card.getValue(), atlas), 10, card));
	// return card group
	return card;
    }*/

    /**
     * 创建本家打出牌
     * 
     * @param card
     * @param atlas
     * @return
     */
    public static final Card newSelfTopOutCard(Card card, TextureAtlas atlas)
    {
	System.out.println("newselfoutcard,card = " + card);
	// create card bg sprite
	Image cardBackground = UI.image("room/own_out_bg.png");
	System.out.println("cardback create success!");
	// set card group size
	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
	System.out.println("card set size success!");
	// add card background to parent
	card.addActor(cardBackground);
	System.out.println("card add cardback success!");
	// create value sprite
	Actor value = UI.center(UI.image("room/own_out_0x" + card.getString() + ".png"), card);
	value.setY(value.getY() + 8 );
	card.addActor(value);
	System.out.println("newselftoutcard finished!");
	// return card group
	return card;
    }
/*    public static final Card newSelfTopOutCard(Card card, TextureAtlas atlas)
    {
    	System.out.println("newselfoutcard,card = " + card);
    	// create card bg sprite
    	Image cardBackground = UI.image("tileBase-meUp-1", atlas);
    	System.out.println("cardback create success!");
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	System.out.println("card set size success!");
    	// add card background to parent
    	card.addActor(cardBackground);
    	System.out.println("card add cardback success!");
    	// create value sprite
    	card.addActor(UI.middleX(
    			UI.image("tile-meUp-" + Card.getTypeString(card.getType())
    					+ card.getValue(), atlas), 19, card));
    	System.out.println("newselftoutcard finished!");
    	// return card group
    	return card;
    }
*/
    /**
     * @param type
     * @param value
     * @param id
     * @param atlas
     * @return
     */
    public static final Card newTopHandCard(Card card, TextureAtlas atlas)
    {
	// create card bg sprite
	Image cardBackground = UI.image("room/top_hand.png");
	// set card group size
	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
	// add card background to parent
	card.addActor(cardBackground);
	// return card group
	return card;
    }
    /*public static final Card newTopHandCard(Card card, TextureAtlas atlas)
    {
    	// create card bg sprite
    	Image cardBackground = UI.image("tileBack-up-1", atlas);
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	// add card background to parent
    	card.addActor(cardBackground);
    	// return card group
    	return card;
    }*/

    public static final Card newLeftHandCard(Card card, TextureAtlas atlas)
    {
	// create card bg sprite
	Image cardBackground = UI.image("room/l_hand.png");
	// set card group size
	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
	// add card background to parent
	card.addActor(cardBackground);
	// return card group
	return card;
    }
/*    public static final Card newLeftHandCard(Card card, TextureAtlas atlas)
    {
    	// create card bg sprite
    	Image cardBackground = UI.image("tileBack-left-1", atlas);
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	// add card background to parent
    	card.addActor(cardBackground);
    	// return card group
    	return card;
    }
*/
    public static final Card newRightHandCard(Card card, TextureAtlas atlas)
    {
	// create card bg sprite
	Image cardBackground = UI.image("room/r_hand.png");
	// set card group size
	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
	// add card background to parent
	card.addActor(cardBackground);
	// return card group
	return card;
    }
/*    public static final Card newRightHandCard(Card card, TextureAtlas atlas)
    {
    	// create card bg sprite
    	Image cardBackground = UI.image("tileBack-right-1", atlas);
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	// add card background to parent
    	card.addActor(cardBackground);
    	// return card group
    	return card;
    }
*/
    public static final Card newLeftOutCard(Card card, TextureAtlas atlas)
    {
		// create card bg sprite
		Image cardBackground = UI.image("room/left-right_block.png");
		// set card group size
		card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
		// add card background to parent
		card.addActor(cardBackground);
		// create value sprite
		card.addActor(UI.middleX(
			UI.image("room/l_out_0x" +card.getString()+ ".png"), 19, card));
		// return card group
		return card;
    }
    public static final Card newRightOutCard(Card card, TextureAtlas atlas)
    {
    	// create card bg sprite
    	Image cardBackground = UI.image("room/left-right_block.png");
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	// add card background to parent
    	card.addActor(cardBackground);
    	// create value sprite
    	card.addActor(UI.middleX(
    			UI.image("room/r_out_0x" +card.getString()+ ".png"), 19, card));
    	// return card group
    	return card;
    }
/*    public static final Card newLeftRightOutCard(Card card, TextureAtlas atlas)
    {
    	// create card bg sprite
    	Image cardBackground = UI.image("tileBase-leftRight-1", atlas);
    	// set card group size
    	card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
    	// add card background to parent
    	card.addActor(cardBackground);
    	// create value sprite
    	card.addActor(UI.middleX(
    			UI.image("tile-leftRight-" + Card.getTypeString(card.getType())
    					+ card.getValue(), atlas), 19, card));
    	// return card group
    	return card;
    }
*/    
    /**
     * 创建打出提示牌
     * */
    public static final Group newOutTipCard(Card card,TextureAtlas atlas)
    {
    	Group group = new Group();
    	Image cardBack = UI.image("tile-lastTileBG",atlas);
    	//cardBack.setScale(0.87f);
    	group.setSize(cardBack.getWidth(), cardBack.getHeight());
    	newSelfHandCard(card, atlas);
    	group.addActor(cardBack);
    	group.addActor(UI.center(card, group));
    	return group;
    }

	public static Card newSelfPengGangCard(Card card)
	{
		// create card bg sprite
		Image cardBackground = null;
		try {
			//此处加载报错。NO GLCONTEXT INFO
			//单独开的一个线程，没有在UI线程.但为什么用atlas加载则可以。是因为之前已经把图片集加载进来了嘛?
			cardBackground = UI.image("room/front_block.png");
			// set card group size
			card.setSize(cardBackground.getWidth(), cardBackground.getHeight());
			//card.setScale(0.8f);
			// add card background to parent
			card.addActor(cardBackground);
			// create value sprite
			Actor value = UI.center(UI.image("room/own_block_0x" + card.getString() + ".png"), card);
			value.setY(value.getY() - 10);
			card.addActor(value);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return card group
		return card;
	
	}
}
