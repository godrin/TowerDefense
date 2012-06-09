package com.cdm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.Campaign;
import com.cdm.view.LevelScreen;
import com.cdm.view.MenuScreen;
import com.cdm.view.Screen;
import com.cdm.view.SoundScreen;

public class TowerGame implements ApplicationListener, Game {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	private Screen prevScreen = null;
	private boolean started = false;
	private float accum = 0;
	boolean stop = false;
	private LevelScreen levelScreen;
	private MenuScreen menuScreen;
	private SoundScreen optionsScreen;

	public void create() {
		running = true;

		Campaign c = new Campaign();
		levelScreen = new LevelScreen(this, c);
		optionsScreen = new SoundScreen(this);
		setScreen(menuScreen = new MenuScreen(this));
		SoundFX.Initialize();
	}

	public void pause() {
		// FIXME
		running = false;
	}

	public void resume() {
		running = true;
	}

	public void setScreen(Screen newScreen) {
		if (screen != null)
			screen.removed();

		if (false)
			prevScreen = screen;

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
		if (prevScreen != null)
			prevScreen.render(accum);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		SoundFX.dispose();
		if (screen != null)
			screen.dispose();

	}

	@Override
	public void setScreen(String string) {
		if (Screen.LEVEL_SCREEN.equals(string))
			setScreen(levelScreen);
		else if (Screen.OPTIONS_SCREEN.equals(string))
			setScreen(optionsScreen);
		else if (Screen.MENU_SCREEN.equals(string))
			setScreen(menuScreen);

	}
}
