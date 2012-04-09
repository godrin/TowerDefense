package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cdm.Game;
import com.cdm.SString;
import com.cdm.gui.BigButton;
import com.cdm.gui.Button;
import com.cdm.gui.IButtonPressed;
import com.cdm.gui.IUnitTypeSelected;
import com.cdm.gui.UnitTypeButton;
import com.cdm.gui.WidgetContainer;
import com.cdm.view.elements.Elements;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.Unit;
import com.cdm.view.elements.Unit.UnitType;

public class LevelScreen extends Screen implements IUnitTypeSelected,
		IButtonPressed {
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
	private Sound sound;
	private Game game;

	public LevelScreen(Game pGame) {
		game = pGame;
		level = new Level(20, 10, 5);
		hud.setLevel(level);
		bg = load("data/bg_stars2.png", 128, 128);
		createUnitButtons();

		Button sizeButton = new Button(300, 400, 30);
		sizeButton.setButtonName(SString.SIZE_BUTTON);
		sizeButton.setPressedListener(this);
		gui.add(sizeButton);

	}

	public void dispose() {
		renderer.dispose();
	}

	private void createUnitButtons() {
		float pos = 35;
		UnitTypeButton tb;
		for (UnitType t : new UnitType[] { UnitType.CANNON, UnitType.STUNNER,
				UnitType.ROCKET_THROWER }) {
			tb = new UnitTypeButton((int) pos, 400, 30, t, level);
			tb.setListener(this);
			tb.setCost(t.getCost());
			gui.add(tb);

			pos += 80;
		}
		System.out.println(gui);
	}

	private Long oldMicros = 0L;

	@Override
	public synchronized void render(float delta) {
		if (rendering)
			return;
		renderer.initGlSettings();
		rendering = true;
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		delta = move(delta);

		draw(delta);
		rendering = false;

	}

	private void draw(float delta) {
		//drawBackground();

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
		System.out.println("S:" + Position.LEVEL_REF.getScale());
		System.out.println("X:" + Position.LEVEL_REF.getX()
				* Position.LEVEL_REF.getScale());
		System.out.println("Y:" + Position.LEVEL_REF.getY()
				* Position.LEVEL_REF.getScale());
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
		if (level.gameover())
			return false;
		int oy = y;
		y = Gdx.graphics.getHeight() - y;
		if (gui.opaque(x, y)) {
			gui.touchDown(x, y, pointer, button);
			return true;
		} else {

			dragPosition.set(x, y, Position.SCREEN_REF);
			oldDragPosition.set(x, y, Position.SCREEN_REF);
		}
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		if (level.gameover())
			return false;
		y = Gdx.graphics.getHeight() - y;
		if (gui.opaque(x, y)) {
			gui.touchUp(x, y, pointer, button);
			return true;
		}
		stopDragging();
		level.stopHover();
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
		if (level.gameover())
			return false;
		y = Gdx.graphics.getHeight() - y;

		dragPosition.set(x, y, Position.SCREEN_REF);
		if (dragElement != null) {
			dragPosition = dragPosition.to(Position.LEVEL_REF).alignedToGrid();

			dragElement.setPosition(dragPosition);
			level.hover(dragPosition);
		} else {
			int dx = (int) (dragPosition.x - oldDragPosition.x);
			int dy = (int) (dragPosition.y - oldDragPosition.y);
			System.out.println("DX " + dx + " DY" + dy);
			oldDragPosition.set(dragPosition);
			modCam(dx, dy);
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		y = Gdx.graphics.getHeight() - y;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		int nu = (int) (Position.LEVEL_REF.getScale() + amount);
		if (nu >= 32 && nu <= 128)
			Position.LEVEL_REF.setScale(nu);

		System.out.println("SCROLL " + amount + " "
				+ Position.LEVEL_REF.getScale());
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
		if (buttonName.equals(SString.SIZE_BUTTON)) {
			sound = Gdx.audio.newSound(Gdx.files.internal("data/zoom.ogg"));
			sound.play();
			// FIXME
		}
	}
}
