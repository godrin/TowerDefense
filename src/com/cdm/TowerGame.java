package com.cdm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.LevelScreen;
import com.cdm.view.Screen;

public class TowerGame implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	private boolean started = false;
	private float accum = 0;
	Music music;

	public void create() {
		running = true;
		setScreen(new LevelScreen());
		Gdx.input.setInputProcessor(screen);
		startMusic();

	}

	private void startMusic() {
		music = Gdx.audio.newMusic(Gdx.files.internal("data/level01.ogg"));
		music.setVolume(0.75f);
		music.setLooping(true);
		// music.play();
		SoundFX.Initialize();
	}

	private void stopMusic() {
		if (music != null) {
			music.pause();
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
		if (screen != null)
			screen.wait(this);
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			accum -= 1.0f / 60.0f;
		}
		screen.render(accum);
		if (Gdx.input.justTouched()) {
			SoundFX.play(Type.KLICK);
		}
		if (Gdx.input.isKeyPressed(41)) {
			if (!music.isPlaying())
				music.play();
		}
		if (Gdx.input.isKeyPressed(42)) {
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
}
