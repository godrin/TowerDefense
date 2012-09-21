package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.cdm.Game;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;
import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;

// review1
public class SoundScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private static Music music, music0, music1, music2;
	private static int song = 2;
	private static boolean stop = false;
	private Color white = new Color(1, 1, 1, 1);
	private String Smusic = "Not Playing...";
	private static float vol = 0.5f;
	public static float FXvol = 0.6f;

	public SoundScreen(Game pgame) {

		music0 = Gdx.audio.newMusic(Gdx.files.internal("data/level01.ogg"));
		music1 = Gdx.audio.newMusic(Gdx.files.internal("data/level02.ogg"));
		music2 = Gdx.audio.newMusic(Gdx.files.internal("data/level03.ogg"));
		music = music1;
		game = pgame;
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 50, 300,
				Gdx.graphics.getWidth() / 6, 50, "music on", "on", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 200, 300,
				Gdx.graphics.getWidth() / 6, 50, "MusiC off", "off", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 50, 240,
				Gdx.graphics.getWidth() / 6, 50, "Vol up", "up", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 200, 240,
				Gdx.graphics.getWidth() / 6, 50, "Vol down", "down", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 50, 180,
				Gdx.graphics.getWidth() / 6, 50, "FX up", "FXup", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 200, 180,
				Gdx.graphics.getWidth() / 6, 50, "Fx down", "FXdown", this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 100, Gdx.graphics
				.getWidth() / 6, 50, "back", "back", this));
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		gui.addTime(delta);
		gui.draw(renderer);
		renderer.drawText(280, 400, "Song " + song + " selected", white);
		float Svol = vol * 100;
		float Fvol = FXvol * 100;
		renderer.drawText(275, 380,
				"Music volume: " + Integer.toString((int) Svol), white);
		renderer.drawText(260, 360,
				"SoundFX volume: " + Integer.toString((int) Fvol), white);
	}

	@Override
	public void buttonPressed(String buttonName) {
		if (buttonName.equals("back"))
			game.setScreen(Screen.MENU_SCREEN);
		else if (buttonName.equals("up")) {
			if (music != null) {
				vol += 0.1f;
				if (vol >= 1)
					vol = 1;
				music.setVolume(vol);
			}
		} else if (buttonName.equals("down")) {
			if (music != null) {
				vol -= 0.1;
				if (vol <= 0)
					vol = 0;
				music.setVolume(vol);
			}
		} else if (buttonName.equals("on")) {
			if (music != null) {
				music.setVolume(vol);
				music.setLooping(true);
				stop = false;
				if (!music.isPlaying())
					music.play();
			}
		} else if (buttonName.equals("off")) {
			stop=true;
			if (music.isPlaying())
				music.stop();
			if (song <= 2) {
				song += 1;
			} else
				song = 1;
			if (song == 1)
				music = music0;
			if (song == 2)
				music = music1;
			if (song == 3)
				music = music2;

		} else if (buttonName.equals("FXup")) {
			if (music != null) {
				FXvol += 0.1f;
				if (FXvol >= 1)
					FXvol = 1;
				SoundFX.play(Type.KLICK);
			}
		} else if (buttonName.equals("FXdown")) {
			if (music != null) {
				FXvol -= 0.1;
				if (FXvol <= 0)
					FXvol = 0;
				SoundFX.play(Type.KLICK);
			}
		}
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		y = Gdx.graphics.getHeight() - y;
		if (gui.opaque(x, y)) {
			gui.touchDown(x, y, pointer, button);
			return true;
		}
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		y = Gdx.graphics.getHeight() - y;
		if (gui.opaque(x, y)) {
			gui.touchUp(x, y, pointer, button);
			return true;
		}
		return false;
	}
	public static void playSong(int id) {
		if(stop)
			return;
		if(music!=null)
			music.stop();
		song=(id%3)+1;
		if (song == 1)
			music = music0;
		if (song == 2)
			music = music1;
		if (song == 3)
			music = music2;
		music.setVolume(vol);
		music.play();
	}

}
