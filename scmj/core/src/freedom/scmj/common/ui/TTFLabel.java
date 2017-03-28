package freedom.scmj.common.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TTFLabel{


	private static final FreeTypeFontGenerator generator = 
			new FreeTypeFontGenerator(Gdx.files.internal("test.ttf"));
	
	private static StringBuilder wordPool = new StringBuilder(FreeTypeFontGenerator.DEFAULT_CHARS);
	
	private LabelStyle style;
	private FreeTypeFontParameter fontParam;
	private BitmapFont font;
	private Label label;
	
	public  TTFLabel(String text,int size,Color color){
		
		fontParam = new FreeTypeFontParameter();
		checkRepeatChar(text);
		fontParam.characters = wordPool.toString();
		fontParam.size = size;
		fontParam.flip = false;
		font = generator.generateFont(fontParam);
		style = new LabelStyle(font,color);
		style.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		label = new Label(text, style);
	}
	
	public void setPosition(float x,float y){
		label.setPosition(x, y);
	}
	
	public void setText(String text){
		if(null == text)return;
		
		boolean result = checkRepeatChar(text);
		if(result){
			fontParam.characters = wordPool.toString();
			font.dispose();
			font = generator.generateFont(fontParam);
			style.font = font;
			style.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			label.setStyle(style);
		}
		label.setText(text);
	}
	
	//check repeat word.return true if have new word.
	private boolean checkRepeatChar(String text){
		if(null == text)throw new IllegalArgumentException("text");
		
		char[] cs = text.toCharArray();
		String pool = wordPool.toString();
		int index = -1;
		boolean newWord = false;
		for (char c : cs) {
			index = pool.indexOf(c);
			if(index <0 ){
				System.out.println("添加的字 = "+c);
				wordPool.append(c);
				newWord = true;
			}
		}
		return newWord;
	}

	public Actor getLabel() {
		return label;
	}
	
	
}
