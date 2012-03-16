package com.cdm.gui.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;



public class SoundFX {
	
	public static Sound klick;
	public static Sound shot, shot2, hit;
		
	public static void Initialize(){
	klick = Gdx.audio.newSound(Gdx.files.internal("data/klick01.ogg"));
	shot = Gdx.audio.newSound(Gdx.files.internal("data/shot01.ogg"));
	shot2 = Gdx.audio.newSound(Gdx.files.internal("data/shot03.ogg"));
	hit = Gdx.audio.newSound(Gdx.files.internal("data/shot04.ogg"));
	}
}
