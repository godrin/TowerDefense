package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Renderer implements IRenderer {
	ImmediateModeRenderer renderer = null;
	BitmapFont font;
	private final SpriteBatch spriteBatch;

	public Renderer() {
		font = new BitmapFont(Gdx.files.internal("data/font16.fnt"),
				Gdx.files.internal("data/font16.png"), false);
		spriteBatch = new SpriteBatch();

		if (Gdx.gl20 == null)
			renderer = new ImmediateModeRenderer();
	}

	public void dispose() {
		font.dispose();
	}

	@Override
	public void drawLines(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		if (Gdx.gl10 != null) {

			Gdx.gl10.glPushMatrix();
			initGlSettings();
			Gdx.gl10.glTranslatef(pos.x, pos.y, 0);
			Gdx.gl10.glRotatef(angle, 0, 0, 1);

			Gdx.gl10.glScalef(size, size, size);
			Gdx.gl10.glLineWidth(pos.getSystem().getScale() * 0.04f);

			renderer.begin(GL10.GL_LINES);
			for (Vector3 v : lines) {
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(v);
			}

			renderer.end();

			Gdx.gl10.glPopMatrix();
		} else {

		}
	}

	public void initGlSettings() {
		if (Gdx.gl10 != null) {
			Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
			Gdx.gl10.glEnable(GL10.GL_BLEND);
			Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		} else if (Gdx.gl20 != null) {
			Gdx.gl20.glEnable(GL10.GL_LINE_SMOOTH);
			Gdx.gl20.glEnable(GL10.GL_BLEND);
			Gdx.gl20.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);

		}
	}

	@Override
	public void drawPoly(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		initGlSettings();
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glTranslatef(pos.x, pos.y, 0);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

		Gdx.gl10.glScalef(size, size, size);

		renderer.begin(GL10.GL_TRIANGLES);
		for (Vector3 v : lines) {
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v);
		}

		renderer.end();

		Gdx.gl10.glPopMatrix();

	}

	@Override
	public void drawRect(float x0, float y0, float x1, float y1, Color c) {
		renderer.begin(GL10.GL_LINE_LOOP);
		initGlSettings();

		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y1, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y1, 0);

		renderer.end();
	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {
		renderer.begin(GL10.GL_TRIANGLES);
		initGlSettings();

		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y1, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y1, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y1, 0);

		renderer.end();
	}

	@Override
	public void drawText(int i, int j, String string, Color c) {
		spriteBatch.begin();

		TextBounds bounds = font.getMultiLineBounds(string);
		spriteBatch.setColor(c);
		// spriteBatch.setBlendFunction(GL10.GL_ONE,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
font.setColor(c);
		font.drawMultiLine(spriteBatch, string, i, j,
		// 160 + bounds.height / 2,
				bounds.width, HAlignment.CENTER);
		spriteBatch.end();

	}

	@Override
	public void drawText(Position position, String money, Color moneyColor) {
		drawText((int) position.x, (int) position.y, money, moneyColor);
	}
}
