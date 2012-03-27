package com.cdm.gui.effects;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundFX {

	public enum Type {
		KLICK, SHOT, SHOT2, HIT
	};

	private static Sound klick;
	private static Sound shot, shot2;
	public static Sound hit;
	private static Map<Type, Sound> sounds = new TreeMap<Type, Sound>();

	public static void Initialize() {
		sounds.put(Type.KLICK,
				Gdx.audio.newSound(Gdx.files.internal("data/klick01.ogg")));
		sounds.put(Type.SHOT,
				Gdx.audio.newSound(Gdx.files.internal("data/shot01.ogg")));
		sounds.put(Type.SHOT2,
				Gdx.audio.newSound(Gdx.files.internal("data/shot03.ogg")));
		sounds.put(Type.HIT,
				Gdx.audio.newSound(Gdx.files.internal("data/shot04.ogg")));
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
			s.play();
	}
}
