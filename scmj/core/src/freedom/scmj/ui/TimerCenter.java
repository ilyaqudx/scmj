package freedom.scmj.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import freedom.scmj.common.ui.NumberFontNew;
import freedom.scmj.common.ui.UI;

public class TimerCenter extends Group {

	private NumberFontNew timeFont;
	
	private List<Actor> origin = new ArrayList<Actor>(4);
	
	private int time;
	
	private Actor lastBlinkActor;
	
	public TimerCenter(TextureAtlas timerAtlas) 
	{
		// set size
		this.setSize(156, 156);
		//set timer back 
		this.add(UI.image("Timer_blackBack", timerAtlas));
		//set blink back
		this.add(UI.pos(UI.image("Timer_windBack",timerAtlas),8,9));
		//set east south west north
		origin.add(this.add(UI.middleX(UI.image("Timer_wind0", timerAtlas), 0, this)));
		origin.add(this.add(UI.middleY(UI.image("Timer_wind3", timerAtlas), 102,this)));
		origin.add(this.add(UI.middleX(UI.image("Timer_wind2", timerAtlas), 100,this)));
		origin.add(this.add(UI.middleY(UI.image("Timer_wind1", timerAtlas), 0, this)));
		
		timeFont = new NumberFontNew("Timer_num.png", 23, 38, "99", true);
		this.add(UI.center(timeFont, this));
	}
	
	private Actor add(Actor actor)
	{
		this.addActor(actor);
		return actor;
	}
	
	public void start(int initTime)
	{
		this.time = initTime;
		timeFont.setText(String.valueOf(initTime));
	}
	
	public int getTime()
	{
		return Integer.parseInt(timeFont.getText());
	}
	
	public boolean isTimeout()
	{
		return 0 >= getTime();
	}
	
	public void resetTime()
	{
		timeFont.setText(String.valueOf(time));
	}
	
	public void scheduler(int time)
	{
		timeFont.scheduler(time);
	}
	
	public Actor getOrigin(int seat)
	{
		return origin.get(seat);
	}
	
	public void blink(int seat)
	{
		if(lastBlinkActor != null)
			unBlink(this.lastBlinkActor);
		Actor actor = getOrigin(seat);
		actor.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.5f),Actions.fadeOut(0.5f))));
		this.lastBlinkActor = actor;
	}
	
	public void unBlink(Actor actor)
	{
		actor.clearActions();
	}
}
