package freedom.scmj.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**将NumberFont转换成Group
 * 
 * 1-继承Group
 * 2-将TextureRegion转换成Image添加进Group  new Image(TextureRegion region)
 * @author Administrator
 *
 */
public class NumberFontNew extends Group{

	private String num;
	//这个值用的相当好
	private int value;
	private int width;
	private int height;
	private boolean zeroFill;
	private TextureRegion[] regions;
	private List<Integer> numList = new ArrayList<Integer>();
	
	public static final int ZERO = 0;
	public static final int NINE = 9;
	
	public NumberFontNew(String fontPrefix,int width,int height,String num)
	{
		this.num = num;
		this.zeroFill = true;
		this.value    = Integer.parseInt(num);
		this.width    = width;
		this.height   = height;
		this.setSize(10 * width, height);
		regions = new TextureRegion[10];
		for (int i = 0; i < regions.length; i++) 
		{
			regions[i] = new TextureRegion(UI.texture(String.format(fontPrefix, i)));
		}
		splitNum();
	}
	
	public NumberFontNew(String fontPath,int width,int height,String num,boolean zeroFill){
		this.num = num;
		this.zeroFill = zeroFill;
		this.value = Integer.parseInt(num);
		this.width = width;
		this.height = height;
		this.setSize(width * num.length(), height);
		TextureRegion[][] ts = TextureRegion.split(UI.texture(fontPath), width, height);
		int count = ts[0].length;
		regions = new TextureRegion[count];
		for (int j = 0; j < count; j++) {
			regions[j] = ts[0][j];
		}
		splitNum();
	}
	
	
	private void splitNum(){
		num = String.valueOf(value);
		if(zeroFill && value <= NINE && num.length() == 1)
			num = ZERO + num;
		char[] cs = num.toCharArray();
		numList.clear();
		for (char c : cs) {
			numList.add(Integer.parseInt(String.valueOf(c)));
		}
	}
	
	
	
	@Override
	protected void drawChildren(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.drawChildren(batch, parentAlpha);
		for (int i = 0; i < numList.size(); i++) 
		{
			batch.draw(regions[numList.get(i)],96 +  i * width, 0);
		}
	}


	public void setText(String num){
		value = Integer.parseInt(num);
		splitNum();
	}
	
	public String getText(){
		return num;
	}
	
	public int getValue(){
		return value;
	}
	
	public int getFontWidth(){
		return width;
	}
	
	public int getFontHeight(){
		return height;
	}
	
	public void scheduler(int add){
		value = (value += add ) > 0 ? value : 0 ;
		splitNum();
	}
}
