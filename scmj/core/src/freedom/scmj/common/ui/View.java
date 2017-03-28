package freedom.scmj.common.ui;

import java.util.concurrent.atomic.AtomicLong;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

public class View {

	public  float WIDTH = 1280,HEIGHT = 720;
	public  float cx = WIDTH/2,cy = HEIGHT/2;
	public static final AtomicLong id = new AtomicLong(10000);
	public static final View create(Actor target)
	{
		return new View(target);
	}
	
	public  View image(String path)
	{
		return new View(UI.image(path));
	}

	private Actor target;
	
	public View(Actor target)
	{
		this.target = target;
		target.setUserObject(id.getAndIncrement());
	}
	
	public View runAction(Action action)
	{
		this.target.addAction(action);
		return this;
	}
	
	public View pos(float x,float y)
	{
		this.target.setPosition(x, y);
		return this;
	}
	
	public View addTo(Stage parent)
	{
		parent.addActor(this.target);
		return this;
	}
	
	public View addTo(Group parent)
	{
		parent.addActor(this.target);
		return this;
	}
	
	public View originCenter()
	{
		this.target.setOrigin(Align.center);
		return this;
	}
	
	public View scale(float scaleX,float scaleY)
	{
		this.target.setScale(scaleX,scaleY);
		return this;
	}
	
	public View scaleX(float scaleX)
	{
		this.target.setScaleX(scaleX);
		return this;
	}
	
	public View scaleY(float scaleY)
	{
		this.target.setScaleY(scaleY);
		return this;
	}
	
	/**设置在屏幕中心
	 * @param View
	 * @return
	 */
	public View center()
	{
		target.setPosition(WIDTH/2-target.getWidth()/2, HEIGHT/2-target.getHeight()/2);
		return this;
	}
	
	public float getWidth()
	{
		return this.target.getWidth();
	}
	
	public float getHeight()
	{
		return this.target.getHeight();
	}
	
	/**设置在父容器中心
	 * @param View
	 * @param parent
	 * @return
	 */
	public View center(View parent)
	{
		target.setPosition(parent.getWidth()/2-target.getWidth()/2, parent.getHeight()/2-target.getHeight()/2);
		return this;
	}
	
	public  View leftX(float y)
	{
		target.setPosition(0, y);
		return this;
	}
	
	public  View rightX(float y)
	{
		target.setPosition(WIDTH - target.getWidth(), y);
		return this;
	}
	
	public  View topY(float x)
	{
		target.setPosition(x, HEIGHT - target.getHeight());
		return this;
	}
	
	public  View bottomY(float x)
	{
		target.setPosition(x, 0);
		return this;
	}
	
	public  View leftTop()
	{
		target.setPosition(0, HEIGHT - target.getHeight());
		return this;
	}
	
	public  View rightTop()
	{
		target.setPosition(WIDTH-target.getWidth(), HEIGHT-target.getHeight());
		return this;
	}
	
	public  View leftBottom()
	{
		target.setPosition(0, 0);
		return this;
	}
	
	public  View rightBottom()
	{
		target.setPosition(WIDTH-target.getWidth(), 0);
		return this;
	}
	
	public  View middleX(float y)
	{
		target.setPosition(WIDTH/2 - target.getWidth()/2, y);
		return this;
	}
	
	public  View middleX(float y,View parent)
	{
		target.setPosition(parent.getWidth()/2 - target.getWidth()/2, y);
		return this;
	}
	
	public  View middleY(float x)
	{
		target.setPosition(x, HEIGHT/2 - target.getHeight()/2);
		return this;
	}
	
	public  View middleY(float x,View parent)
	{
		target.setPosition(x, parent.getHeight()/2 - target.getHeight()/2);
		return this;
	}
	
	public  View leftMiddle()
	{
		target.setPosition(0, HEIGHT/2 - target.getHeight()/2);
		return this;
	}
	
	public  View rightMiddle()
	{
		target.setPosition(WIDTH - target.getWidth(), HEIGHT/2 - target.getHeight()/2);
		return this;
	}
	
	public  View topMiddle()
	{
		target.setPosition(WIDTH/2 - target.getWidth()/2, HEIGHT-target.getHeight());
		return this;
	}
	
	public  View bottomMiddle()
	{
		target.setPosition(WIDTH/2 - target.getWidth()/2, 0);
		return this;
	}

	public View centerX(float y) 
	{
		target.setPosition(WIDTH/2-target.getWidth()/2, y);
		return this;
	}

	public View centerY(int x) 
	{
		target.setPosition(x, HEIGHT / 2 - target.getHeight() / 2);
		return this;
	}
	
	public View addClickListener(InputListener listener)
	{
		this.target.addListener(listener);
		return this;
	}
	
	public boolean isHit(Object userObject)
	{
		if(null == userObject)
			return false;
		return userObject == this.target.getUserObject();
	}
}
