package com.cdm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.LevelScreen;
import com.cdm.view.MenuScreen;
import com.cdm.view.Screen;
import com.cdm.view.enemy.EnemyPlayer;

public class TowerGame extends EnemyPlayer implements ApplicationListener, Game {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	private boolean started = false;
	private float accum = 0;
	public Music music, music0, music1;
	boolean stop = false;
	private LevelScreen levelScreen;
	private MenuScreen menuScreen;

	public void create() {
		running = true;
		levelScreen=new LevelScreen(this);
		setScreen(menuScreen=new MenuScreen(this));
		music0 = Gdx.audio.newMusic(Gdx.files.internal("data/level01.ogg"));
		music1 = Gdx.audio.newMusic(Gdx.files.internal("data/level02.ogg"));
		music = music1;
		// startMusic();
		SoundFX.Initialize();

	}

	private void startMusic() {
		if (music != null) {
			music.setVolume(0.75f);
			music.setLooping(true);
			music.play();
			// SoundFX.Initialize();
		}
	}

	private void stopMusic() {
		if (music != null) {
			music.stop();
		}
	}

	public void pause() {
		running = false;
		stopMusic();
	}

	public void resume() {
		running = true;
		startMusic();
	}

	public void setScreen(Screen newScreen) {
		if (screen != null)
			screen.removed();
		screen = newScreen;

		if (screen != null) {
			Gdx.input.setInputProcessor(screen);
			screen.wait(this);
		}
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			accum -= 1.0f / 60.0f;
		}
		screen.render(accum);
		/*
		 * if (Gdx.input.justTouched()) { SoundFX.play(Type.KLICK); }
		 */
		if (Gdx.input.isKeyPressed(41)) {
			stop = false;
			if (!music.isPlaying())
				startMusic();
		}

		if (Gdx.input.isKeyPressed(42)) {
			if (!music.isPlaying()) {
				if (music == music1) {
					if (stop != true) {
						music = music0;
						stop = true;
					}
				} else if (music == music0) {
					if (stop != true) {
						music = music1;
						stop = true;
					}
				}
			}
			stopMusic();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		SoundFX.dispose();
		music.dispose();
		if (screen != null)
			screen.dispose();

	}

	@Override
	public void setScreen(String string) {
		if(Screen.LEVEL_SCREEN.equals(string))
			setScreen(levelScreen);
		
	}
}
