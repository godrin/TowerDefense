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

public class InputScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private List<Entry> vals;
	private Highscore highscoreServer;
	public String name = "";
	private int points;
	private Campaign campaign;
	Color white = new Color(1, 1, 1, 1);

	public InputScreen(Game pgame, Campaign c) {

		game = pgame;
		campaign = c;
		points = c.playerState.getPoints();
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 50, Gdx.graphics
				.getWidth() / 3, 50, "commit", "commit", this));
		highscoreServer = new HighscoreServer();

	}

	public boolean keyTyped(char character) {
		char c = character;
		name += c;
		return false;
	}

	@Override
	public void render() {
		if (vals == null) {
			try {
				vals = highscoreServer.read();
			} catch (HighscoreAccessException e) {
			}
		}
		super.render();

		gui.draw(renderer);
		renderer.drawText(150, 480, "ENTER YOUR NAME:", white, 2);
		renderer.drawText(250, 300, name, white, 2);

		if (vals != null) {
			StringBuilder sb = new StringBuilder();
			for (Entry entry : vals) {
				sb.append(entry.toString());
				sb.append(" Points\n");
			}
			// renderer.drawText(110, 380, sb.toString(), white,1.25f);
		}
	}

	@Override
	public void move(float delta) {
		super.move(delta);
		gui.addTime(delta);
	}

	@Override
	public void buttonPressed(String buttonName) {
		if (buttonName.equals("commit")) {

			Entry n = new Entry(name, getPoints());
			try {
				highscoreServer.post(n);
			} catch (HighscoreAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			name = "";
			game.setScreen(Screen.HIGHSCORE_SCREEN);
			campaign.restart(game);
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

	public int getPoints() {
		points = campaign.playerState.getPoints();
		return points;

	}

}
