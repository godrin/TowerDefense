package com.cdm.view;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.cdm.Game;
import com.cdm.gui.AnimText;
import com.cdm.gui.BigButton;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.WidgetContainer;
import com.cdm.gui.anim.Easings;
import com.cdm.gui.anim.MoveAnimation;

public class MenuScreen extends Screen implements IButtonPressed {

	private static final Color BLACK_TRANSLUCENT = new Color(0, 0, 0, 0.5f);
	private WidgetContainer gui = new WidgetContainer();
	private Renderer renderer = new Renderer();
	private Game game;
	private PolySprite sprite;
	private Position spritePos = new Position(Gdx.graphics.getWidth() / 4,
			Gdx.graphics.getHeight() / 2, Position.SCREEN_REF);

	private LevelScreen bgScreen;

	public MenuScreen(Game pgame, LevelScreen demoScreen) {
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
				"v-defend", "2012", "presented", "by", "godrin", "and",
				"undermink", })));
		b0.add(new MoveAnimation(Easings.QUAD, 1.5f, middle, b0.getY(), b0));
		b1.add(new MoveAnimation(Easings.QUAD, 2.0f, middle, b1.getY(), b1));
		b2.add(new MoveAnimation(Easings.QUAD, 2.5f, middle, b2.getY(), b2));
		b3.add(new MoveAnimation(Easings.QUAD, 3.0f, middle, b3.getY(), b3));

		sprite = SpriteReader.read("/com/cdm/view/elements/units/power.sprite");

		bgScreen = demoScreen;
	}

	@Override
	public void render() {
		super.render();
		renderer.initGlSettings();

		renderBg();
		bgScreen.render();
		renderer.fillRect(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), BLACK_TRANSLUCENT);

		gui.draw(renderer);
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		super.move(delta);
		gui.addTime(delta);
		bgScreen.move(delta);
	}

	private void renderBg() {
		renderer.render(sprite, spritePos, Gdx.graphics.getWidth() / 4, 0,
				GL20.GL_TRIANGLES);
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
