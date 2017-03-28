package freedom.scmj.common.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Dialog extends Group{

	private Image    dialog_bg;
	private ImageButton ok;
	private ImageButton cancel;
	private TTFLabel textLabel; 
	
	public Dialog(String background,String text,int textSize,Color textColor,
			ImageButton ok,ImageButton cancel){
		this.ok = ok;
		this.cancel = cancel;
		
		dialog_bg = new Image(new Texture("tanchukuang.png"));
		this.setSize(dialog_bg.getWidth(), dialog_bg.getHeight());
		
		textLabel = new TTFLabel(text,textSize,textColor);
		textLabel.setPosition(getWidth() / 2 - textLabel.getLabel().getWidth()/2,getHeight() - 80);
		
		this.cancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Dialog.this.setVisible(false);
			}
		});
		
		this.ok.setPosition(80, 100);
		this.cancel.setPosition(370, 100);
		
		this.addActor(dialog_bg);
		this.addActor(textLabel.getLabel());
		this.addActor(ok);
		this.addActor(cancel);
	}
	
	public void setText(String text){
		textLabel.setText(text);
	}
	
	public void addOkClickListener(ClickListener listener){
		System.out.println("ok = " + ok.addListener(listener));
	}
	
	public void addCancelClickListener(ClickListener listener){
		System.out.println("cancel = " + cancel.addListener(listener));
	}
}
