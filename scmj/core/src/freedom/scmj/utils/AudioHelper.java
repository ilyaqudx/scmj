package freedom.scmj.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import freedom.scmj.entity.Card;

public class AudioHelper 
{
	private static final Map<String,Sound> audios = new HashMap<String, Sound>();
	
	public static final String OUT_CARD = "give.ogg";
	public static final String PENG     = "peng.ogg";
	public static final String GANG     = "gang.ogg";
	public static final String HU       = "hu.ogg";
	public static final String GUO      = "guo.ogg";
	
	public static final Music BACK_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/playingInGame.mp3"));
	
	public static final String[] OTHER_AUDIO_POOL = 
		{
			OUT_CARD,PENG,GANG,HU
		};
	
	static
	{
		for (int i = 1; i < 10; i++) 
		{
			audios.put(String.valueOf(Card.TYPE_TONG)+i,Gdx.audio.newSound(Gdx.files.internal("audio/" + i +"tong.ogg")));
			audios.put(String.valueOf(Card.TYPE_TIAO)+i,Gdx.audio.newSound(Gdx.files.internal("audio/" + i +"tiao.ogg")));
			audios.put(String.valueOf(Card.TYPE_WAN)+i,Gdx.audio.newSound(Gdx.files.internal("audio/" + i +"wan.ogg")));
		}
		
		for (String audio : OTHER_AUDIO_POOL) 
		{
			audios.put(audio,Gdx.audio.newSound(Gdx.files.internal("audio/"+audio)));
		}
	}
	
	public static final void playAudio(String audio)
	{
		if(audio == null || "".equals(audio))
			return;
		audios.get(audio).play();
	}
	
	public static final void playCardAudio(Card card)
	{
		String audioKey = String.valueOf(card.getType()) + card.getValue();
		if(audios.containsKey(audioKey))
			audios.get(audioKey).play();
	}
	
	public static final void playBackGround()
	{
		if(!BACK_MUSIC.isLooping())
			BACK_MUSIC.setLooping(true);
		BACK_MUSIC.play();
	}
	
	public static final void pauseBackGround()
	{
		BACK_MUSIC.pause();
	}
	
	public static final void stopBackGround()
	{
		BACK_MUSIC.stop();
	}
}
