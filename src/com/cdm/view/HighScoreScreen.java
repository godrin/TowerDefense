package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.Game;
import com.cdm.Highscore;
import com.cdm.Highscore.Entry;
import com.cdm.Highscore.HighscoreAccessException;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;

public class HighScoreScreen extends Screen implements IButtonPressed {

	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private List<Entry> vals;
	Color white = new Color(1, 1, 1, 1);

	public HighScoreScreen(Game pgame) {

		game = pgame;
		gui.add(new BigButton(Gdx.graphics.getWidth() / 2, 50, Gdx.graphics
				.getWidth() / 3, 50, "back", "back", this));
		try {
			vals = Highscore.main(null);
		} catch (HighscoreAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		gui.addTime(delta);
		gui.draw(renderer);
		renderer.drawText(150, 480, "HIGHSCORES:", white, 3);
		renderer.drawText(115, 380, "" + vals.get(0) + "Points"  + "\n" + vals.get(1) + "Points"
				+ "\n" + vals.get(2) + "Points"  + "\n" + vals.get(3) + "Points" + "\n" + vals.get(4) + "Points" 
				+ "\n" + vals.get(5)  + "Points" + "\n" + vals.get(6) + "Points"  + "\n" + vals.get(7) + "Points"
				+ "\n" + vals.get(8) + "Points"  + "\n" + vals.get(9) + "Points" + "\n", white,1.25f);

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
