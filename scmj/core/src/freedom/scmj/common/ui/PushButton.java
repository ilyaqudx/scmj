package freedom.scmj.common.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PushButton extends Image{

	private boolean pressedFlag;
	private Image normal;
	private Image pressed;
	@SuppressWarnings("unused")
	private PushButtonListener listener;
	
	public PushButton(String normal,String pressed)
	{
		this.normal  = UI.image(normal);
		this.pressed = UI.image(pressed);
		this.setDrawable(this.normal.getDrawable());
	}
	
	public PushButton(String normal,String pressed,TextureAtlas pack)
	{
		this(normal,pressed,false,pack);
	}
	
	public PushButton(String normal,String pressed,boolean pressedFlag,TextureAtlas pack){
		super(pack.createSprite(normal));
		this.normal = new Image(pack.createSprite(normal));
		this.pressed = new Image(pack.createSprite(pressed));
		this.pressedFlag = pressedFlag;
		if(pressedFlag)
		{
			this.setDrawable(getButton().getDrawable());
		}
	}
	
	private Image getButton(){
		return pressedFlag ? pressed : normal;
	}
	
	public void switchStatus(){
		pressedFlag = !pressedFlag;
		this.setDrawable(getButton().getDrawable());
	}
	
	public boolean isPressed(){
		return pressedFlag;
	}
	
	public void addListener(PushButtonListener listener)
	{
		if(null == listener)throw new IllegalArgumentException("listener");
		listener.button = this;
		this.listener = listener;
		super.addListener(listener);
	}
	
	public static abstract class PushButtonListener extends ClickListener{
		
		public PushButton button;
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if(button.pressedFlag)return;
			onClick(button);
			button.switchStatus();
		}
		
		public abstract void onClick(PushButton button);
	}
}
