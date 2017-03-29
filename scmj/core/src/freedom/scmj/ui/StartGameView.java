package freedom.scmj.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import freedom.scmj.common.tween.accessor.ActorAccessor;
import freedom.scmj.common.ui.UI;

public class StartGameView extends Group {

	/**背景*/
	private Image back;
	/**开始文字*/
	private Image startWord;
	/**动画缓存*/
	Timeline animation;

	public StartGameView() 
	{
		// game start back
		back = (Image) UI.center(UI.image("game_start_mask.png"));
		back.setScaleX(UI.WIDTH);
		back.setOrigin(Align.center);
		// game start word
		startWord = (Image) UI.center(UI.image("game_start_word.png"));
		startWord.setOrigin(Align.center);
		// add child
		this.addActor(back);
		this.addActor(startWord);
		this.hide();
	}
	
	public void show()
	{
		this.setVisible(true);
	}
	
	public void hide()
	{
		this.setVisible(false);
	}
	
	@SuppressWarnings("unused")
	private void reset()
	{
		startWord.setScale(1);
		back.setScale(1);
	}
	
	public void playAnimation(TweenManager manager)
	{
		show();
		
		if(animation == null)
		{
			animation = Timeline.createSequence()
					.delay(0.5f)
					.push(Tween.to(startWord, ActorAccessor.SCALE_XY, 0.2f).target(3f,3f))
					.push(Tween.set(startWord, ActorAccessor.OPACITY).target(0))
					.push(Tween.to(back, ActorAccessor.SCALE_XY, 0.3f).targetRelative(0, -1))
					.setCallback(new TweenCallback() {
						
						public void onEvent(int arg0, BaseTween<?> arg1) 
						{
							hide();
						}
					});
		}
		
		animation.start(manager);
	}
}
