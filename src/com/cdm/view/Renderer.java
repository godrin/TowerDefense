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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.Position.RefSystem;

public class Renderer implements IRenderer {
	ImmediateModeRenderer renderer = new ImmediateModeRenderer();
	BitmapFont font;
	private final SpriteBatch spriteBatch;

	public Renderer() {
		font = new BitmapFont(Gdx.files.internal("data/font16.fnt"),
				Gdx.files.internal("data/font16.png"), false);
		spriteBatch = new SpriteBatch();

	}

	public void dispose() {
		font.dispose();
	}

	@Override
	public void drawLines(Position pos, List<Vector3> lines, float angle,
			Color color, float size, RefSystem level) {
		float scale = size * Settings.getCellWidth() / 2;
		// pos.getScale();
		if (Settings.getCellWidth() == 32)
			Gdx.gl10.glLineWidth(2);
		else
			Gdx.gl10.glLineWidth(3);
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl10.glTranslatef(pos.getX(), pos.getY(), 0);
		Gdx.gl10.glScalef(scale, scale, scale);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

		renderer.begin(GL10.GL_LINES);
		for (Vector3 v : lines) {
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v);
		}

		renderer.end();

		Gdx.gl10.glPopMatrix();

	}

	@Override
	public void drawPoly(Position pos, List<Vector3> lines, float angle,
			Color color, float size, RefSystem system) {
		float scale = 1.0f;
		if (system.isLevel())
			scale = Settings.getScale();

		// if (pos.equals(Position.RefSystem.Level)) {
		scale = size * Settings.getCellWidth() / 2;
		// }
		if (Settings.getCellWidth() == 32)
			Gdx.gl10.glLineWidth(2);
		else
			Gdx.gl10.glLineWidth(3);
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl10.glTranslatef(pos.getX(), pos.getY(), 0);
		Gdx.gl10.glScalef(scale, scale, scale);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

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
		drawRect(x0, y0, x1, y1, c, RefSystem.Screen);
	}

	@Override
	public void drawRect(float x0, float y0, float x1, float y1, Color c,
			RefSystem system) {
		float scale = 1.0f;
		if (system.isLevel())
			scale = Settings.getScale();

		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);

		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glScalef(scale, scale, scale);
		renderer.begin(GL10.GL_LINE_LOOP);

		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y0, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x1, y1, 0);
		renderer.color(c.r, c.g, c.b, c.a);
		renderer.vertex(x0, y1, 0);

		renderer.end();
		Gdx.gl10.glPopMatrix();

	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {
		fillRect(x0, y0, x1, y1, c, RefSystem.Screen);
	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c,
			RefSystem system) {
		float scale = 1.0f;
		if (system.isLevel())
			scale = Settings.getScale();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glScalef(scale, scale, scale);

		renderer.begin(GL10.GL_TRIANGLES);

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
		Gdx.gl10.glPopMatrix();

	}

	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();

	@Override
	public void drawText(int i, int j, String string, Color c) {
		int h = Gdx.graphics.getHeight();
		/*
		 * viewMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), h);
		 * spriteBatch.setProjectionMatrix(viewMatrix);
		 * spriteBatch.setTransformMatrix(transformMatrix);
		 */
		spriteBatch.begin();
		// String text = "It is the end my friend.\nTouch to continue!";
		TextBounds bounds = font.getMultiLineBounds(string);
		spriteBatch.setColor(c);
		spriteBatch.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

		font.drawMultiLine(spriteBatch, string, i, j,
		// 160 + bounds.height / 2,
				bounds.width, HAlignment.CENTER);
		spriteBatch.end();

	}

	@Override
	public void drawText(Position position, String money, Color moneyColor) {
		drawText((int) position.getX(), (int) position.getY(), money,
				moneyColor);
	}
}
