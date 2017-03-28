package freedom.scmj.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import freedom.scmj.common.ui.NumberFontNew;
import freedom.scmj.common.ui.UI;
import freedom.scmj.common.ui.View;

public class TimerCenterNew extends Group {

	private NumberFontNew timeFont;
	
	private List<Actor> origin = new ArrayList<Actor>(4);
	
	private int time;
	
	private Actor lastBlinkActor;
	
	public TimerCenterNew() 
	{
		// set size
		this.setSize(130, 130);
		//set timer background 
		View.create(UI.image("room/timer/frame.png")).addTo(this);
		
		//set blink back
		//this.add(UI.pos(UI.image("Timer_windBack",timerAtlas),8,9));
		//set east south west north
		origin.add(this.add(UI.middleX(UI.image("room/timer/downR.png"), 12, this)));
		origin.add(this.add(UI.middleY(UI.image("room/timer/rightR.png"), 95,this)));
		origin.add(this.add(UI.middleX(UI.image("room/timer/upR.png"), 96,this)));
		origin.add(this.add(UI.middleY(UI.image("room/timer/leftR.png"), 10, this)));
		
		timeFont = new NumberFontNew("room/timer/%d.png", 24, 38, "10");
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
