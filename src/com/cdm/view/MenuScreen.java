package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.cdm.Game;
import com.cdm.SString;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;
import com.cdm.gui.anim.Easings;
import com.cdm.gui.anim.MoveAnimation;

public class MenuScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private float animX = 0;

	public MenuScreen(Game pgame) {
		game = pgame;
		BigButton b0, b1, b2;

		float middle = Gdx.graphics.getWidth() / 2;

		gui.add(b0 = new BigButton(-200, 300, Gdx.graphics.getWidth() / 4, 50,
				"start game", SString.create("startGame"), this));
		gui.add(b1 = new BigButton(-200, 200, Gdx.graphics.getWidth() / 4, 50,
				"options", SString.create("options"), this));
		gui.add(b2 = new BigButton(-200, 100, Gdx.graphics.getWidth() / 4, 50,
				"quit", SString.create("quit"), this));

		b0.add(new MoveAnimation(Easings.QUAD, 1.5f, middle, b0.getY(), b0));
		b1.add(new MoveAnimation(Easings.QUAD, 2.0f, middle, b1.getY(), b1));
		b2.add(new MoveAnimation(Easings.QUAD, 2.5f, middle, b2.getY(), b2));
	}

	@Override
	public void render(float delta) {
		animX -= delta;
		super.render(delta);
		renderer.initGlSettings();
		gui.addTime(delta);

		gui.draw(renderer);
	}

	@Override
	public void buttonPressed(SString buttonName) {
		if (buttonName.equals(SString.create("quit")))
			Gdx.app.exit();
		else if (buttonName.equals(SString.create("startGame"))) {
			game.setScreen(Screen.LEVEL_SCREEN);
		} else if (buttonName.equals(SString.create("options")))
			game.setScreen(Screen.OPTIONS_SCREEN);

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
