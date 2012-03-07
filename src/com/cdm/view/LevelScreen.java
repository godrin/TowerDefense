package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.Button;
import com.cdm.gui.WidgetContainer;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;

public class LevelScreen extends Screen {
	public SpriteBatch spriteBatch = new SpriteBatch();
	public static TextureRegion bg;
	private Renderer renderer = new Renderer();
	private Level level = new Level();
	private WidgetContainer gui = new WidgetContainer();
	private Element dragElement = null;
	private Selector selector = new Selector();

	public LevelScreen() {
		bg = load("res/bg_stars.png", 64, 64);
		gui.add(new Button(40, 400, 30));
	}

	@Override
	public void render(float delta) {
		delta += mywait(delta);
		spriteBatch.begin();
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 16; y++)
				draw(bg, x * 64, y * 64);

		spriteBatch.end();
		drawLineBased(delta);

	}

	private void drawLineBased(float delta) {
		if (delta > 0) {
			level.move(delta);
			// System.out.println(t);
		}
		OrthographicCamera cam;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		GLCommon gl = Gdx.gl;

		long startPhysics = System.nanoTime();

		cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		cam.apply(Gdx.gl10);

		Gdx.gl10.glScalef(1, -1, 0);
		Gdx.gl10.glTranslatef(0, -Gdx.graphics.getHeight(), 0);

		level.draw(renderer);
		gui.draw(renderer);

		if (dragElement != null) {
			dragElement.draw(renderer);
		}
		selector.draw(renderer);

	}

	static final int CIRCLE_VERTICES = 10;
	private float mywait(float delta) {
		if (true)
			return 0.0f;
		try {
			Integer ms = (int) (delta * 1000);
			// ~ 50 fps
			// TODO: sleep shorter, if rendering does need more time
			int wait = 15 - ms;
			if (wait > 10) {
				Thread.sleep(wait);
				// System.out.println(wait);
			}
			return wait / 1000.0f;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
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
		if (gui.opaque(x, y)) {
			System.out.println("MYYY TOUCHDOWN");
			gui.touchDown(x, y, pointer, button);
			return true;
		}
		System.out.println("touchDown");
		System.out.println(x);
		System.out.println(y);
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		System.out.println("touchUp");
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		System.out.println("touchDrag");
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		System.out.println("touchmoved");
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		System.out.println("scroll");
		return false;
	}

}
