package freedom.scmj.common.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import freedom.scmj.GameApplication;
import freedom.scmj.common.ui.UI;

public abstract class BaseScreen extends ScreenAdapter{

	protected GameApplication game;
	protected Stage stage;
	protected SpriteBatch batch;
	public BaseScreen(GameApplication game)
	{
		this.game = game;
		this.game.add(this);
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		Viewport view = stage.getViewport();
		stage.setViewport(view);
		view.setWorldSize(UI.WIDTH,UI.HEIGHT);
		view.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void render(float delta) {
		batch.begin();
		draw(delta);
		batch.end();
		stage.act();
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	@Override
	public void hide() {
		super.hide();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	
	protected Actor add(Actor actor) 
	{
		stage.addActor(actor);
		return actor;
	}
	
	protected Actor add(Actor actor,float x,float y) 
	{
		actor.setPosition(x, y);
		stage.addActor(actor);
		return actor;
	}
	
	protected void remove(Actor actor)
	{
		Array<Actor> actors = stage.getActors();
		for (Actor child : actors) 
		{
			if(child == actor)
			{
				actors.removeValue(child, true);
				break;
			}
		}
	}
	
	public abstract void draw(float delta);
	
}
