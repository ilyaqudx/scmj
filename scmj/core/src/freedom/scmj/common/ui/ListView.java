package freedom.scmj.common.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


/**
 * @author Administrator
 *
 *
 * * ListView创建步骤</br>
 * 
 * 1-实例化ListView</br>
 * 2-设置滚动方向,默认是水平滚动。</br>
 * 3-设置宽高(如果滚动方向的长度大于了元素总长度则不会滚动。因为不需要滚动)</br>
 * 4-设置ListView的位置</br>
 * 5-添加子元素</br>
 * 6-最后将ListView加入Stage即可</br>
 * @param <T>
 */
public class ListView<T extends Actor> extends ScrollPane{

	
	/**滚动方向
	 * 1-纵
	 * 2-横
	 */
	public static final int DIRECTION_VERTICAL = 1,DIRECTION_LANDSCAPE = 2;
	
	private int direction;
	
	private Table container;
	
	public ListView()
	{
		super(new Table());
		
		this.container = (Table) getWidget();
		
		init();
	}
	
	private void init()
	{
		setScrollDirection(DIRECTION_LANDSCAPE);
	}
	
	public void addItem(T...ts){
		for (int i = 0; i < ts.length; i++)
		{
			addItem(ts[i]);
		}
	}
	
	/**
	 * 添加元素
	 * */
	public void addItem(T t)
	{
		if (direction == DIRECTION_LANDSCAPE) 
		{
			container.add(t);
		}
		else if(direction == DIRECTION_VERTICAL)
		{
			container.row();
			
			container.add(t);
		}
	}
	
	public void setBackgroud(String background)
	{
		getStyle().background = UI.drawable(background);
	}
	
	
	/**设置滚动方向
	 * @param direction
	 */
	public void setScrollDirection(int direction)
	{
		if(DIRECTION_LANDSCAPE == direction)
			setScrollingDisabled(false,true);
		else
			setScrollingDisabled(true, false);
		this.direction = direction;
	}
	
	
}
