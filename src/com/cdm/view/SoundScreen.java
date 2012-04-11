package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.cdm.Game;
import com.cdm.SString;
import com.cdm.TowerGame;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;

public class SoundScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private MenuScreen menuScreen;
	public Music music, music0, music1;
	boolean stop = true;
	Color white = new Color(1, 1, 1, 1);
	String Smusic = "Not Playing...";
	private float vol = 0.75f;

	public SoundScreen(Game pgame) {

		music0 = Gdx.audio.newMusic(Gdx.files.internal("data/level01.ogg"));
		music1 = Gdx.audio.newMusic(Gdx.files.internal("data/level02.ogg"));
		music = music1;
		Smusic = "song 2 selected";
		game = pgame;
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 50, 300,
				Gdx.graphics.getWidth() / 6, 50, "sound on", SString
						.create("on"), this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 200, 300,
				Gdx.graphics.getWidth() / 6, 50, "sound off", SString
						.create("off"), this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 50, 240,
				Gdx.graphics.getWidth() / 6, 50, "Vol up",
				SString.create("up"), this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 3 + 200, 240,
				Gdx.graphics.getWidth() / 6, 50, "Vol down", SString
						.create("down"), this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 100, Gdx.graphics
				.getWidth() / 6, 50, "back", SString.create("back"), this));
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		gui.addTime(delta);
		gui.draw(renderer);
		renderer.drawText(280, 400, Smusic, white);
		renderer.drawText(300, 380, "Volume: " + vol, white);
	}

	@Override
	public void buttonPressed(SString buttonName) {
		if (buttonName.equals(SString.create("back")))
			game.setScreen(menuScreen = new MenuScreen(game));
		else if (buttonName.equals(SString.create("up"))) {
			if (music != null) {
				vol += 0.1f;
				if (vol >= 1)
					vol = 1;
				music.setVolume(vol);
				System.out.println(vol);
			}
		} else if (buttonName.equals(SString.create("down"))) {
			if (music != null) {
				vol -= 0.05f;
				if (vol <= 0)
					vol = 0;
				music.setVolume(vol);
				System.out.println(vol);
			}
		} else if (buttonName.equals(SString.create("on"))) {
			if (music != null) {
				music.setVolume(0.75f);
				music.setLooping(true);
				stop = false;
				if (!music.isPlaying())
					music.play();
			}
		} else if (buttonName.equals(SString.create("off"))) {
			music.stop();
			if (!music.isPlaying()) {
				if (music == music1) {
					if (stop != true) {
						music = music0;
						Smusic = "song 1 selected";
						stop = true;
					}
				} else if (music == music0) {
					if (stop != true) {
						music = music1;
						Smusic = "song 2 selected";
						stop = true;
					}
				}
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

}
