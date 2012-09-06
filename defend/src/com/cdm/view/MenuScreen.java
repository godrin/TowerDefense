package com.cdm.view;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.Game;
import com.cdm.gui.AnimText;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;
import com.cdm.gui.anim.Easings;
import com.cdm.gui.anim.MoveAnimation;

public class MenuScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private long oldMicros = 0;
	private PolySprite sprite;
	private Position spritePos = new Position(Gdx.graphics.getWidth() / 4,
			Gdx.graphics.getHeight() / 2, Position.SCREEN_REF);

	public MenuScreen(Game pgame) {
		game = pgame;
		BigButton b0, b1, b2, b3;

		float middle = Gdx.graphics.getWidth() / 2;

		gui.add(b0 = new BigButton(-200, 400, Gdx.graphics.getWidth() / 4, 50,
				"start game", "startGame", this));
		gui.add(b1 = new BigButton(-200, 300, Gdx.graphics.getWidth() / 4, 50,
				"options", "options", this));
		gui.add(b2 = new BigButton(-200, 200, Gdx.graphics.getWidth() / 4, 50,
				"highscores", "highscores", this));
		gui.add(b3 = new BigButton(-200, 100, Gdx.graphics.getWidth() / 4, 50,
				"quit", "quit", this));
		gui.add(new AnimText(150, 150, 400, 100, Arrays.asList(new String[] {
				"godrin", "undermink", "proudly", "present" })));
		b0.add(new MoveAnimation(Easings.QUAD, 1.5f, middle, b0.getY(), b0));
		b1.add(new MoveAnimation(Easings.QUAD, 2.0f, middle, b1.getY(), b1));
		b2.add(new MoveAnimation(Easings.QUAD, 2.5f, middle, b2.getY(), b2));
		b3.add(new MoveAnimation(Easings.QUAD, 3.0f, middle, b3.getY(), b3));

		sprite = SpriteReader.read("/com/cdm/view/elements/units/power.sprite");

	}

	@Override
	public void render(float delta) {
		long millis = System.currentTimeMillis();
		long micro = System.nanoTime() / 1000 + millis * 1000;
		if (oldMicros > 0) {
			delta = (micro - oldMicros) * 0.000001f;
		}
		oldMicros = micro;

		super.render(delta);
		renderer.initGlSettings();
		gui.addTime(delta);

		renderBg();

		gui.draw(renderer);
	}

	private void renderBg() {
		renderer.render(sprite, spritePos, Gdx.graphics.getWidth() / 4, 0,
				GL10.GL_TRIANGLES);
	}

	@Override
	public void buttonPressed(String buttonName) {
		if (buttonName.equals("quit"))
			Gdx.app.exit();
		else if (buttonName.equals("startGame")) {
			game.setScreen(Screen.LEVEL_SCREEN);
		} else if (buttonName.equals("options")) {
			game.setScreen(Screen.OPTIONS_SCREEN);
		} else if (buttonName.equals("highscores"))
			game.setScreen(Screen.HIGHSCORE_SCREEN);

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
