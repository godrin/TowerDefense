package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.cdm.Game;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.IUnitTypeSelected;
import com.cdm.gui.UnitTypeButton;
import com.cdm.gui.WidgetContainer;
import com.cdm.view.elements.Elements;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.LevelFinishedListener;
import com.cdm.view.elements.UpgradeView;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.elements.units.Unit.UnitType;
import com.cdm.view.elements.units.UnitAction;

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
	private UpgradeView upgradeView = new UpgradeView(this);

	private Game game;
	private Campaign campaign;
	private boolean readonly;

	public LevelScreen(Game pGame, Campaign c, boolean pReadonly) {
		readonly = pReadonly;
		campaign = c;
		game = pGame;
		level = campaign.getNextLevel(pGame, this);
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
			tb = new UnitTypeButton((int) pos, 400, 30, t, level, this);
			tb.setListener(this);
			tb.setCost(t.getCost());
			gui.add(tb);

			pos += 80;
		}
	}

	private boolean dragging = false;
	private int dragDisplacement = 32;
	private boolean paused = false;
	private boolean gettingReady = true;

	@Override
	public synchronized void render() {
		if (rendering)
			return;
		synchronized (this) {

			renderer.initGlSettingsExplicit();
			rendering = true;
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			draw();
			rendering = false;
		}
	}

	private void draw() {
		if (gettingReady && !readonly)
			drawGetReady();
		else {
			drawLineBased();
			if (!readonly)
				hud.draw(renderer);
		}
	}

	float textPhase = 0;

	public void drawGetReady() {
		renderer.drawText(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, "LEVEL " + campaign.getLevelNo(),
				Color.WHITE, 1, true, true);
		renderer.drawText(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - 30, "GET READY", Color.WHITE,
				1.5f + (float) Math.sin(textPhase * 3) * 0.2f, true, true);
	}

	public void move(float delta) {
		if (delta > 0)
			level.setFps((int) (1.0f / delta));
		if (gettingReady && !readonly) {
			textPhase += delta;
			return;
		}
		if (delta > 0 && !paused) {
			level.move(delta);
		}
		gui.addTime(delta);

	}

	private void modCam(int dx, int dy) {
		Position.LEVEL_REF.moveBy(dx, dy);
	}

	private void drawLineBased() {

		if (Gdx.gl10 != null)
			Gdx.gl10.glPushMatrix();
		else {
			Renderer.pushMatrix();
		}
		Position.LEVEL_REF.apply();

		level.draw(unitRenderer);
		upgradeView.drawAfter(unitRenderer);

		if (dragElement != null) {
			dragElement.draw(unitRenderer);
		}
		if (Gdx.gl10 != null) {
			Gdx.gl10.glPopMatrix();
			Gdx.gl10.glPushMatrix();
		} else {
			Renderer.popMatrix();
			Renderer.pushMatrix();
		}
		Position.SCREEN_REF.apply();

		if (!readonly)
			gui.draw(unitRenderer);
		if (Gdx.gl10 != null)

			Gdx.gl10.glPopMatrix();
		else
			Renderer.popMatrix();

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
		if (gettingReady)
			return false;
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
				// reset selected unit
				if (selectedUnit != null) {
					selectedUnit.selected(false);
					upgradeView.setVisible(false);
				}
				if ((selectedUnit = level.getPlayerUnitAt(tmp)) != null) {

					if (tmp.distance(selectedUnit.getPosition()) > 0.3f) {
						selectedUnit = null;
					} else {
						upgradeView.setPosition(selectedUnit.getPosition());
						upgradeView.setVisible(true);
						upgradeView.setTargetUnit(selectedUnit);

						selectedUnit.selected(true);
						vibrateShort();
					}
				}

				if (selectedUnit == null) {
					// do the dragging
					dragging = true;
					dragPosition.set(x, y, Position.SCREEN_REF);
					oldDragPosition.set(x, y, Position.SCREEN_REF);
				}

			}
		}
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {

		if (gettingReady) {
			gettingReady = false;
			vibrateShort();
			return false;
		}

		if (selectedUnit != null) {
			UnitAction selectedUprade = upgradeView.getSelectedUpgrade();
			if (selectedUprade != null && selectedUnit != null) {
				selectedUprade.doAction(selectedUnit);
				int price = selectedUprade.getCostForNext();
				level.setMoney(level.getMoney() - price);
				vibrateShort();
			}

			selectedUnit.selected(false);
			selectedUnit = null;
			upgradeView.setVisible(false);
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

	public boolean isDragging() {
		return dragElement != null;
	}

	private void stopDragging() {
		if (level.gameover())
			return;
		if (dragElement != null) {
			vibrateShort();
			level.add(dragElement);
		}
		dragElement = null;
	}

	private void vibrateShort() {
		Gdx.input.vibrate(10);
	}

	public boolean touchDragged(int x, int y, int pointer) {
		if (gettingReady)
			return false;
		synchronized (this) {

			if (level.gameover())
				return false;
			y = Gdx.graphics.getHeight() - y;

			dragPosition.set(x, y, Position.SCREEN_REF);
			if (dragElement != null) {
				dragPosition.y += dragDisplacement;
				dragPosition.set(dragPosition.to(Position.LEVEL_REF)
						.alignedToGrid());

				dragElement.setPosition(dragPosition);
				level.hover(dragPosition);
			} else if (selectedUnit != null) {
				dragPosition.set(dragPosition.to(Position.LEVEL_REF));
				upgradeView.hover(dragPosition);
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
		if (gettingReady)
			return false;
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
	public void buttonPressed(String buttonName) {

	}

	// FIXME move this function in a "Campaign" or game-control class, maybe
	// "towergame"
	public void restart() {
		if (campaign.playerState.getPoints() >= 1000) {
			SoundScreen.playSong(0);
			game.setScreen(Screen.INPUT_SCREEN);
		} else {
			game.setScreen(Screen.HIGHSCORE_SCREEN);
			SoundScreen.playSong(0);
			campaign.restart(game);
			setLevel(campaign.getNextLevel(game, this));
		}
	}

	@Override
	public void levelFinished() {
		setLevel(campaign.getNextLevel(game, this));
		level.setMoney(level.getMoney() + 10);
		gettingReady = true;
	}

	private void setLevel(Level nextLevel) {
		level = nextLevel;
		hud.setLevel(level);
		// recreate, so that buttons have nu level
		createUnitButtons();
	}

	public Level getLevel() {
		return level;
	}

	public boolean keyDown(int keycode) {
		if (keycode == 131) {
			game.setScreen(Screen.MENU_SCREEN);
		}
		if (keycode == 44) {
			paused = !paused;
		}
		if (keycode == 47) {

			Json json = new Json();
			String text = json.toJson(level);
			System.out.println("JSON " + text);

			level = json.fromJson(Level.class, text);
			level.init(game, this);

		}
		System.out.println("KEYCODE " + keycode);
		return false;
	}

}
