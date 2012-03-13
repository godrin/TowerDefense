package com.cdm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.LevelScreen;
import com.cdm.view.Screen;

public class TowerGame implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	private boolean started = false;
	private float accum = 0;

	public void create() {
		running = true;
		setScreen(new LevelScreen());
		Gdx.input.setInputProcessor(screen);
		
		startMusic();
	}

	private void startMusic() {
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
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
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
