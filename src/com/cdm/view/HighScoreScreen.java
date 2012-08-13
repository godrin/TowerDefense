package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.Game;
import com.cdm.Highscore;
import com.cdm.HighscoreServer;
import com.cdm.HighscoreServer.Entry;
import com.cdm.HighscoreServer.HighscoreAccessException;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;

public class HighScoreScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private List<Entry> vals;
	private Highscore highscoreServer;
	Color white = new Color(1, 1, 1, 1);

	public HighScoreScreen(Game pgame) {

		game = pgame;

		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 100, Gdx.graphics
				.getWidth() / 6, 50, "back", "back", this));
		highscoreServer = new HighscoreServer();

	}

	@Override
	public void render(float delta) {
		if (vals == null) {
			try {
				vals = highscoreServer.read();
			} catch (HighscoreAccessException e) {
			}
		}
		super.render(delta);
		gui.addTime(delta);
		gui.draw(renderer);
		renderer.drawText(150, 480, "HIGHSCORES:", white, 3);
		if (vals != null) {
			StringBuilder sb = new StringBuilder();
			for (Entry entry : vals) {
				sb.append(entry.toString());
				sb.append("\n");
			}
			renderer.drawText(220, 350, sb.toString(), white);
		}
	}

	@Override
	public void buttonPressed(String buttonName) {
		if (buttonName.equals("back"))
			game.setScreen(Screen.MENU_SCREEN);

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
