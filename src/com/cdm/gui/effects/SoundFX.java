package com.cdm.gui.effects;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.cdm.view.SoundScreen;

public class SoundFX {

	public enum Type {

		KLICK, SHOT, SHOT2, HIT, HURT,STUNRAY, LOOSE, WIN

	};

	private static Map<Type, Sound> sounds = new TreeMap<Type, Sound>();

	public static void Initialize() {
		sounds.put(Type.KLICK,
				Gdx.audio.newSound(Gdx.files.internal("data/klick01.ogg")));
		sounds.put(Type.SHOT,
				Gdx.audio.newSound(Gdx.files.internal("data/shot01.ogg")));
		sounds.put(Type.SHOT2,
				Gdx.audio.newSound(Gdx.files.internal("data/tschiu02.ogg")));
		sounds.put(Type.HIT,
				Gdx.audio.newSound(Gdx.files.internal("data/shot04.ogg")));
		sounds.put(Type.HURT,
				Gdx.audio.newSound(Gdx.files.internal("data/punch.ogg")));
		sounds.put(Type.STUNRAY,
				Gdx.audio.newSound(Gdx.files.internal("data/stunray01.ogg")));

		sounds.put(Type.LOOSE, Gdx.audio.newSound(Gdx.files.internal("data/loose.ogg")));
		sounds.put(Type.WIN, Gdx.audio.newSound(Gdx.files.internal("data/bonus.ogg")));
		
	}

	public static void dispose() {
		for (Sound s : sounds.values()) {
			s.dispose();
		}
		sounds.clear();
	}

	public static void play(Type type) {
		Sound s = sounds.get(type);
		if (s != null)
			s.play(SoundScreen.FXvol);
	}
}
