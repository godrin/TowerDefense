package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cdm.Game;
import com.cdm.SString;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.IUnitTypeSelected;
import com.cdm.gui.UnitTypeButton;
import com.cdm.gui.WidgetContainer;
import com.cdm.view.elements.Elements;
import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.LevelFinishedListener;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.elements.units.Unit.UnitType;

public class LevelScreen extends Screen implements IUnitTypeSelected,
		IButtonPressed, LevelFinishedListener {
	public SpriteBatch spriteBatch = new SpriteBatch();
	public static TextureRegion bg;
	private Renderer renderer = new Renderer();
	private UnitRenderer unitRenderer = new UnitRenderer(renderer);
	private Level level;
	private WidgetContainer gui = new WidgetContainer();
	private Unit dragElement = null;
	private LevelDisplays hud = new LevelDisplays();
	private boolean rendering = false;
	private Position dragPosition = new Position(0, 0, Position.SCREEN_REF);
	private Position oldDragPosition = new Position(0, 0, Position.SCREEN_REF);
	private Position checkPosition = new Position(0, 0, Position.SCREEN_REF);
	private PlayerUnit selectedUnit = null;

	private Game game;
	private Campaign campaign;

	public LevelScreen(Game pGame, Campaign c) {
		campaign = c;
		game = pGame;
		level = campaign.getNextLevel(this);
		hud.setLevel(level);
		hud.setCampaign(c);
		bg = load("data/bg_stars2.png", 128, 128);
		createUnitButtons();
	}

	public void dispose() {
		renderer.dispose();
	}

	/**
	 * called only at screen initialization
	 */
	private void createUnitButtons() {
		float pos = 35;
		gui.clear();
		UnitTypeButton tb;
		for (UnitType t : new UnitType[] { UnitType.CANNON, UnitType.STUNNER,
				UnitType.ROCKET_THROWER }) {
			tb = new UnitTypeButton((int) pos, 400, 30, t, level);
			tb.setListener(this);
			tb.setCost(t.getCost());
			gui.add(tb);

			pos += 80;
		}
	}

	private Long oldMicros = 0L;
	private boolean dragging = false;

	@Override
	public synchronized void render(float delta) {
		if (rendering)
			return;
		synchronized (this) {

			renderer.initGlSettings();
			rendering = true;
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			delta = move(delta);

			draw(delta);
			rendering = false;
		}
	}

	private void draw(float delta) {
		// drawBackground();

		drawLineBased(delta);
		hud.draw(renderer);
	}

	private void drawBackground() {

		spriteBatch.begin();
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 16; y++)
				draw(bg, x * 128, y * 128);

		spriteBatch.end();
	}

	private float move(float delta) {
		long millis = System.currentTimeMillis();
		long micro = System.nanoTime() / 1000 + millis * 1000;
		if (oldMicros > 0) {
			delta = (micro - oldMicros) * 0.000001f;
		}
		oldMicros = micro;
		mywait(delta);
		return delta;
	}

	private void modCam(int dx, int dy) {
		Position.LEVEL_REF.moveBy(dx, dy);
	}

	private void drawLineBased(float delta) {
		if (delta > 0) {
			level.move(delta);
		}
		if (Gdx.gl10 != null)
			Gdx.gl10.glPushMatrix();
		Position.LEVEL_REF.apply();

		level.draw(unitRenderer);
		if (dragElement != null) {
			dragElement.draw(unitRenderer);
		}
		if (Gdx.gl10 != null) {
			Gdx.gl10.glPopMatrix();
			Gdx.gl10.glPushMatrix();
		}
		Position.SCREEN_REF.apply();

		gui.addTime(delta);
		gui.draw(unitRenderer);
		if (Gdx.gl10 != null)

			Gdx.gl10.glPopMatrix();

	}

	static final int CIRCLE_VERTICES = 10;

	private void mywait(float delta) {
		try {
			Integer ms = (int) (delta * 1000);

			int wait = 15 - ms;
			if (wait > 5) {
				Thread.sleep(wait);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void draw(TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0)
			width = -width;
		spriteBatch.draw(region, x, y, width, -region.getRegionHeight());
	}

	public static TextureRegion load(String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
		region.flip(false, true);
		return region;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		synchronized (this) {

			if (level.gameover()) {
				restart();
				return false;

			}
			y = Gdx.graphics.getHeight() - y;
			if (gui.opaque(x, y)) {
				gui.touchDown(x, y, pointer, button);
				return true;
			} else {
				// FIXME: check if unit is below
				checkPosition.set(x, y, Position.SCREEN_REF);
				Position tmp = checkPosition.to(Position.LEVEL_REF);
				if (selectedUnit != null)
					selectedUnit.selected(false);
				if ((selectedUnit = level.getPlayerUnitAt(tmp)) != null) {
					System.out.println("PLAYER UNIT FOUND " + selectedUnit);
					selectedUnit.selected(true);
				} else {

					dragging = true;
					dragPosition.set(x, y, Position.SCREEN_REF);
					oldDragPosition.set(x, y, Position.SCREEN_REF);
				}

			}
		}
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		if (selectedUnit != null) {

			// FIXME: check if an upgrade was selected
			selectedUnit.selected(false);
			selectedUnit = null;
		}
		synchronized (this) {

			dragging = false;

			if (level.gameover())
				return false;

			y = Gdx.graphics.getHeight() - y;
			if (gui.opaque(x, y)) {
				gui.touchUp(x, y, pointer, button);
				return true;
			}
			stopDragging();
			level.stopHover();
		}
		return false;

	}

	private void stopDragging() {
		if (level.gameover())
			return;
		if (dragElement != null) {
			level.add(dragElement);
		}
		dragElement = null;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		synchronized (this) {

			if (level.gameover())
				return false;
			y = Gdx.graphics.getHeight() - y;

			dragPosition.set(x, y, Position.SCREEN_REF);
			if (dragElement != null) {
				dragPosition = dragPosition.to(Position.LEVEL_REF)
						.alignedToGrid();

				dragElement.setPosition(dragPosition);
				level.hover(dragPosition);
			} else if (dragging) {
				int dx = (int) (dragPosition.x - oldDragPosition.x);
				int dy = (int) (dragPosition.y - oldDragPosition.y);
				oldDragPosition.set(dragPosition);
				modCam(dx, dy);
			}
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		synchronized (this) {

			y = Gdx.graphics.getHeight() - y;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		synchronized (this) {

			int nu = (int) (Position.LEVEL_REF.getScale() + amount);
			if (nu >= 40 && nu <= 128)
				Position.LEVEL_REF.setScale(nu);
		}
		return false;
	}

	@Override
	public void unitTypeSelected(UnitType type, Position screenPos, int cost) {
		dragElement = Elements.getElementBy(type,
				screenPos.to(Position.LEVEL_REF).alignedToGrid());
		dragElement.setCost(cost);

	}

	@Override
	public void buttonPressed(SString buttonName) {

		if (!level.gameover()) {
			if (buttonName.equals(SString.SIZE_BUTTON)) {
				// FIXME
			}
		}
	}

	// FIXME move this function is a "Campaign" or game-control class, maybe
	// "towergame"
	public void restart() {
		game.setScreen(Screen.MENU_SCREEN);
		
		campaign.restart();
		
		setLevel(campaign.getNextLevel(this));
		
	}

	@Override
	public void levelFinished() {
		setLevel(campaign.getNextLevel(this));
	}

	private void setLevel(Level nextLevel) {
		level = nextLevel;
		hud.setLevel(level);
		// recreate, so that buttons have nu level
		createUnitButtons();
	}
}
