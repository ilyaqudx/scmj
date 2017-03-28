package freedom.scmj.common.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class ProgressToAction extends TemporalAction{

	public static final ProgressToAction create(float duration) 
	{
		ProgressToAction action = new ProgressToAction();
		action.setDuration(duration);
		
		return action;
	}
	
	@Override
	protected void update(float percent) 
	{
		//System.out.println(percent);
		ProgressBar bar = (ProgressBar)target;
		bar.setValue(100 - percent * 100);
	}

	
}
