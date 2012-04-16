package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.cdm.Game;
import com.cdm.SString;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;

public class MenuScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;



	public MenuScreen(Game pgame) {
		game = pgame;
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 300, Gdx.graphics
				.getWidth() / 4, 50, "start game", SString.create("startGame"),
				this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 200, Gdx.graphics
				.getWidth() / 4, 50, "options", SString.create("options"), this));
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 100, Gdx.graphics
				.getWidth() / 4, 50, "quit", SString.create("quit"), this));
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		gui.addTime(delta);
		gui.draw(renderer);
	}

	@Override
	public void buttonPressed(SString buttonName) {
		if (buttonName.equals(SString.create("quit")))
			Gdx.app.exit();
		else if (buttonName.equals(SString.create("startGame"))){
			game.setScreen(Screen.LEVEL_SCREEN);}
		else if (buttonName.equals(SString.create("options")))
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
