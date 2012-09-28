package com.cdm.defend;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.Game;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.Campaign;
import com.cdm.view.HighScoreScreen;
import com.cdm.view.InputScreen;
import com.cdm.view.LevelScreen;
import com.cdm.view.MenuScreen;
import com.cdm.view.Screen;
import com.cdm.view.SoundScreen;

// review1

public class DefendGame implements ApplicationListener, Game {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	boolean stop = false;
	private LevelScreen levelScreen;
	private LevelScreen demoScreen;
	private MenuScreen menuScreen;
	private SoundScreen optionsScreen;
	private HighScoreScreen highscoreScreen;
	private InputScreen inputScreen;
	private long oldMicros = 0;

	public void create() {
		running = true;

		Campaign c = new Campaign("/com/cdm/view/campaign1.txt");
		levelScreen = new LevelScreen(this, c, false);

		Campaign demoCampaign = new Campaign("/com/cdm/view/demo.txt");
		demoScreen = new LevelScreen(new DemoGame(), demoCampaign, true);

		optionsScreen = new SoundScreen(this);
		SoundScreen.playSong(1);
		setScreen(menuScreen = new MenuScreen(this, demoScreen));
		highscoreScreen = new HighScoreScreen(this);
		inputScreen = new InputScreen(this, c);
		SoundFX.Initialize();
		Gdx.graphics.setVSync(true);

	}

	public boolean backButtonPressed() {
		if (screen != menuScreen) {
			setScreen(menuScreen);
			return false;
		}
		return true;
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
		if (screen == levelScreen) {
			SoundScreen.playSong(2);
		}

		if (screen != null) {
			Gdx.input.setInputProcessor(screen);
			screen.wait(this);
		}
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		move();
		screen.render();

	}

	private void move() {
		if (!running)
			return;
		long millis = System.currentTimeMillis();
		long micro = System.nanoTime() / 1000 + millis * 1000;
		float delta = 0;
		if (oldMicros > 0) {
			delta = (micro - oldMicros) * 0.000001f;
			mywait(delta);
			screen.move(delta);
		}
		oldMicros = micro;

	}

	private void mywait(float delta) {
		if (false)
			return;
		try {
			int ms = (int) (delta * 1000);

			int wait = (1000 / 60) - ms;
			if (wait > 2) {
				Thread.sleep(wait);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resize(int width, int height) {

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
		else if (Screen.HIGHSCORE_SCREEN.equals(string))
			setScreen(highscoreScreen);
		else if (Screen.INPUT_SCREEN.equals(string))
			setScreen(inputScreen);
	}
}
