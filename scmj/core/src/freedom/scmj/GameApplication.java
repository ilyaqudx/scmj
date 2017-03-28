package freedom.scmj;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import freedom.scmj.common.var3d.net.freefont.FreeBitmapFont;
import freedom.scmj.common.var3d.net.freefont.FreeListener;
import freedom.scmj.common.var3d.net.freefont.FreePaint;
import freedom.scmj.net.NetManager;
import freedom.scmj.screen.GameScreen;
import freedom.scmj.screen.HallScreen;

public class GameApplication extends Game {
	private Map<Class<? extends Screen>, Screen> screens = new HashMap<Class<? extends Screen>, Screen>();

	public <T> void add(Screen screen) 
	{
		if (null == screen) {
			return;
		}
		screens.put(screen.getClass(), screen);
		//初始化网络
		NetManager.getInstance();
	}

	@SuppressWarnings("unchecked")
	public <T> T getScreen(Class<T> clazz) 
	{
		return (T) screens.get(clazz);
	}
	
	private FreeListener freeListener;
	public FreeBitmapFont font;
	public static GameApplication I;
	public GameApplication(FreeListener freeListener)
	{
		I = this;
		this.freeListener = freeListener;
		this.font         = new FreeBitmapFont(freeListener, new FreePaint());
	}

	public void create() 
	{
		setScreen(new GameScreen(this));
	}
	
	public FreeListener getFreeListener()
	{
		return this.freeListener;
	}
}
