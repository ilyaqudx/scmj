package freedom.scmj.common.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

public class UI {

	public static final float WIDTH = 1280,HEIGHT = 720;
	public static final float cx = WIDTH/2,cy = HEIGHT/2;
	
	public static final Actor originCenter(Actor actor)
	{
		actor.setOrigin(Align.center);
		return actor;
	}
	
	public static final Actor pos(Actor actor,float x,float y)
	{
		actor.setPosition(x, y);
		return actor;
	}
	
	/**设置在屏幕中心
	 * @param actor
	 * @return
	 */
	public static final Actor center(Actor actor)
	{
		actor.setPosition(WIDTH/2-actor.getWidth()/2, HEIGHT/2-actor.getHeight()/2);
		return actor;
	}
	
	/**设置在父容器中心
	 * @param actor
	 * @param parent
	 * @return
	 */
	public static final Actor center(Actor actor,Actor parent)
	{
		actor.setPosition(parent.getWidth()/2-actor.getWidth()/2, parent.getHeight()/2-actor.getHeight()/2);
		return actor;
	}
	
	public static final Actor leftX(Actor actor,float y)
	{
		actor.setPosition(0, y);
		return actor;
	}
	
	public static final Actor rightX(Actor actor,float y)
	{
		actor.setPosition(WIDTH - actor.getWidth(), y);
		return actor;
	}
	
	public static final Actor topY(Actor actor,float x)
	{
		actor.setPosition(x, HEIGHT - actor.getHeight());
		return actor;
	}
	
	public static final Actor bottomY(Actor actor,float x)
	{
		actor.setPosition(x, 0);
		return actor;
	}
	
	public static final Actor leftTop(Actor actor)
	{
		actor.setPosition(0, HEIGHT - actor.getHeight());
		return actor;
	}
	
	public static final Actor rightTop(Actor actor)
	{
		actor.setPosition(WIDTH-actor.getWidth(), HEIGHT-actor.getHeight());
		return actor;
	}
	
	public static final Actor leftBottom(Actor actor)
	{
		actor.setPosition(0, 0);
		return actor;
	}
	
	public static final Actor rightBottom(Actor actor)
	{
		actor.setPosition(WIDTH-actor.getWidth(), 0);
		return actor;
	}
	
	public static final Actor middleX(Actor actor,float y)
	{
		actor.setPosition(WIDTH/2 - actor.getWidth()/2, y);
		return actor;
	}
	
	public static final Actor middleX(Actor actor,float y,Actor parent)
	{
		actor.setPosition(parent.getWidth()/2 - actor.getWidth()/2, y);
		return actor;
	}
	
	public static final Actor middleY(Actor actor,float x)
	{
		actor.setPosition(x, HEIGHT/2 - actor.getHeight()/2);
		return actor;
	}
	
	public static final Actor middleY(Actor actor,float x,Actor parent)
	{
		actor.setPosition(x, parent.getHeight()/2 - actor.getHeight()/2);
		return actor;
	}
	
	public static final Actor leftMiddle(Actor actor)
	{
		actor.setPosition(0, HEIGHT/2 - actor.getHeight()/2);
		return actor;
	}
	
	public static final Actor rightMiddle(Actor actor)
	{
		actor.setPosition(WIDTH - actor.getWidth(), HEIGHT/2 - actor.getHeight()/2);
		return actor;
	}
	
	public static final Actor topMiddle(Actor actor)
	{
		actor.setPosition(WIDTH/2 - actor.getWidth()/2, HEIGHT-actor.getHeight());
		return actor;
	}
	
	public static final Actor bottomMiddle(Actor actor)
	{
		actor.setPosition(WIDTH/2 - actor.getWidth()/2, 0);
		return actor;
	}
	
	public static final Texture texture(String path)
	{
		Texture texture = new Texture(path);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}
	
	public static final Texture texture(Pixmap pixmap)
	{
		return new Texture(pixmap);
	}
	
	public static final Image image(String path)
	{
		return  new Image(texture(path));
	}
	
	public static final Image image(Texture texture)
	{
		return new Image(texture);
	}
	
	public static final Image image(TextureRegion region)
	{
		return new Image(region);
	}
	
	public static final Image image(Pixmap pixmap)
	{
		return image(texture(pixmap));
	}
	
	public static final Sprite sprite(String path)
	{
		return new Sprite(texture(path));
	}
	
	public static final Drawable drawable(String path)
	{
		return new SpriteDrawable(sprite(path));
	}
	
	public static final Button button(String up,String down,String name)
	{
		Button button = new Button(drawable(up),drawable(down));
		button.setName(name);
		return button;
	}
	
	public static final  Button button(String up,String down,String disable,String name)
	{
		Button button = new Button(drawable(up),drawable(down),drawable(disable));
		button.setName(name);
		return button;
	}
	
	public static final ProgressBarStyle progressbarStyle(String background,String after)
	{
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		ProgressBarStyle progressBarStyle = new ProgressBarStyle();
		progressBarStyle.background = skin.newDrawable("white", new Color(1, 0, 0, 0.66f));
		progressBarStyle.background.setMinHeight(15);
		progressBarStyle.knobBefore = skin.newDrawable("white", Color.CLEAR);
		progressBarStyle.knobBefore.setMinHeight(15);
		progressBarStyle.knobAfter = skin.newDrawable("white", new Color(0.25f, 0.25f, 0.25f, 0.66f));
		progressBarStyle.knobAfter.setMinHeight(15);
		
		return progressBarStyle;
	}
	
	public static final ProgressBar progressbar(String background,String knocAfter,boolean vertical)
	{
		return progressbar(0, 100, 1, vertical, progressbarStyle(background, knocAfter));
	}
	
	public static final ProgressBar progressbar(float min,float max,float stepSize,boolean vertical,ProgressBarStyle style)
	{
		return new ProgressBar(min, max, stepSize, vertical, style);
	}
	public static final ProgressBar progressbar(float min,float max,float stepSize,boolean vertical,Skin skin)
	{
		return new ProgressBar(min, max, stepSize, vertical, skin);
	}

	public static Label label(String text,LabelStyle style)
	{
		return new Label(text,style);
	}
	
	public static Label label(String text,Skin skin) 
	{
		return new Label(text,skin);
	}
	
	public static final Label label(String text,BitmapFont font)
	{
		return new Label(text,labelStyle(font));
	}
	
	public static final LabelStyle labelStyle(BitmapFont font)
	{
		return new LabelStyle(font,Color.WHITE);
	}
	
	public static final Group popLayer()
	{
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 0.6f);
		pixmap.fill();
		
		Image pop = new Image(new Texture(pixmap));
		
		Group popLayer = new Group();
		popLayer.setName("popLayer");
		popLayer.setSize(pixmap.getWidth(), pixmap.getHeight());
		popLayer.addActor(pop);
		
		return popLayer;
	}
	
	public static final Image popLayer(int width,int height)
	{
		Pixmap pixmap = new Pixmap(width,height,Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 0.6f);
		pixmap.fill();
		
		return image(texture(pixmap));
	}
	
	
	public static final ScrollPane scrollPane(float x,float y,float width,float height,String background,boolean disableX,boolean disableY)
	{
		Table container = new Table();
		ScrollPane scrollPane = new ScrollPane(container);
		scrollPane.setSize(width, height);
		scrollPane.setPosition(x, y);
		scrollPane.setScrollingDisabled(disableX, disableY);
		
		return scrollPane;
	}
	
	public static final ScrollPane scrollPane(float x,float y,float width,float height)
	{
		return scrollPane(x, y, width, height, null, false, true);
	}
	
	public static final Pixmap pixmap(int width,int height,Color color)
	{
		Pixmap pixmap = new Pixmap(width, height,Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		
		return pixmap;
	}
	
	/**加载一个gdx图片集
	 * @param path	文件名称
	 * @return
	 */
	public static final TextureAtlas atlas(String path)
	{
		return new TextureAtlas(path);
	}
	
	public static final Image image(String imageName,TextureAtlas atlas)
	{
		return new Image(atlas.findRegion(imageName));
	}
}
