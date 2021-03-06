package com.cdm.gui.effects;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.cdm.view.SoundScreen;

// review1
public class SoundFX {

	public enum Type {
		KLICK, SHOT, SHOT2, HIT, HIT2, HURT, STUNRAY, LOOSE, WIN, WIN2, LEVEL1, LEVEL2
	};

	private static Map<Type, Sound> sounds = new TreeMap<Type, Sound>();

	public static void Initialize() {
		sounds.put(Type.KLICK,
				Gdx.audio.newSound(Gdx.files.internal("data/klick01.ogg")));
		sounds.put(Type.HIT2,
				Gdx.audio.newSound(Gdx.files.internal("data/hit.mp3")));
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
		sounds.put(Type.LEVEL1,
				Gdx.audio.newSound(Gdx.files.internal("data/level_completed.ogg")));
		sounds.put(Type.LEVEL2,
				Gdx.audio.newSound(Gdx.files.internal("data/next_level.ogg")));
		sounds.put(Type.LOOSE,
				Gdx.audio.newSound(Gdx.files.internal("data/loose.ogg")));
		sounds.put(Type.WIN,
				Gdx.audio.newSound(Gdx.files.internal("data/bonus.ogg")));
		sounds.put(Type.WIN2,
				Gdx.audio.newSound(Gdx.files.internal("data/win02.ogg")));

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
