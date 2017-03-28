package freedom.scmj.common.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class TouchListener extends ClickListener{
	
	public static final float DOWN_OFFSET = 15;
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y,
			int pointer, int button) 
	{
		Actor target = event.getTarget();
		//返回TRUE则可接收后续事件
		target.addAction(Actions.moveBy(0, -DOWN_OFFSET,0.2f,Interpolation.swing));
		return super.touchDown(event, x, y, pointer, button);
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) 
	{
		Actor target = event.getTarget();
		target.addAction(Actions.moveBy(0, DOWN_OFFSET,0.3f,Interpolation.swing));
		super.touchUp(event, x, y, pointer, button);
	}
	
	
	
	@Override
	public void clicked(InputEvent event,final  float x,final float y) {
		final Actor target = event.getTarget();
		Action delay = Actions.delay(0.2f, Actions.run(new Runnable() {
			
			public void run() {
				onClicked(target, x, y);
			}
		}));
		target.addAction(delay);
	}

	public abstract void onClicked(Actor actor,float x,float y);
}
