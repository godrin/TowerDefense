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
import com.cdm.view.elements.Cannon;
import com.cdm.view.elements.Level;

public class LevelScreen extends Screen {
	public SpriteBatch spriteBatch = new SpriteBatch();
	public static TextureRegion bg;
	private Level level=new Level();
	private Long lastRun=0L;

	public LevelScreen() {
		bg = load("res/bg_stars.png", 64, 64);
	}

	public void render() {
		spriteBatch.begin();
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 16; y++)
				draw(bg, x * 64, y * 64);

		spriteBatch.end();
		drawLines();
		
		if(false)
		mywait();
	}

	private void drawLines() {
		long currentRun=System.nanoTime();
		long diff=currentRun-lastRun;
		if(lastRun>0) {
			float t=(diff/1000000000.0f);
			level.move(t);
			//System.out.println(t);
		}
		lastRun=currentRun;
		OrthographicCamera cam;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		GLCommon gl = Gdx.gl;
		ImmediateModeRenderer renderer = new ImmediateModeRenderer();

		long startPhysics = System.nanoTime();

		cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		cam.apply(Gdx.gl10);

		long startRender = System.nanoTime();
		//drawMySprite(renderer);
		level.draw(new Renderer());

	}

	static final int CIRCLE_VERTICES = 10;

	private void drawMySprite(ImmediateModeRenderer renderer) {

		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glTranslatef(40, 40, 0);
		Vector3[] vs = new Vector3[] { new Vector3(-0.5f, -0.5f, 0.0f),
				new Vector3(0.5f, -0.5f, 0.0f), new Vector3(0.5f, 0.5f, 0.0f),
				new Vector3(-0.5f, 0.5f, 0.0f) };

		Gdx.gl10.glScalef(10, 10, 10);

		renderer.begin(GL10.GL_LINES);
		for (int i = 0; i < 4; i++) {
			Vector3 a = vs[i];
			Vector3 b = vs[(i + 1) % 4];
			renderer.color(1, 1, 1, 1);
			renderer.vertex(a);
			renderer.color(1, 1, 1, 1);
			renderer.vertex(b);
		}

		renderer.end();
		Gdx.gl10.glPopMatrix();

		renderer.begin(GL10.GL_LINES);
		renderer.color(1, 1, 1, 1);
		// renderer.ver
		renderer.vertex(40, 40, 0);
		renderer.color(1, 1, 1, 1);
		renderer.vertex(20, 20, 0);
		renderer.end();

		return;
	}

	private void mywait() {
		try {
			// ~ 50 fps
			// TODO: sleep shorter, if rendering does need more time
			Thread.sleep(20);
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

}
