package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Renderer implements IRenderer {
	ImmediateModeRenderer renderer = null;
	ImmediateModeRenderer20 renderer20 = null;
	public static BitmapFont font;
	private final SpriteBatch spriteBatch;

	private static Matrix4 projMatrix = null;
	private static List<Matrix4> matrixStack = new ArrayList<Matrix4>();

	public Renderer() {

		if (projMatrix == null) {
			projMatrix = new Matrix4();

			projMatrix.trn(-1f, -1f, 0);
			projMatrix.scl(new Vector3(2.0f / Gdx.graphics.getWidth(),
					2.0f / Gdx.graphics.getHeight(), 0.01f));
		}
		font = new BitmapFont(Gdx.files.internal("data/font16.fnt"),
				Gdx.files.internal("data/font16.png"), false);
		spriteBatch = new SpriteBatch();

		if (Gdx.gl20 == null)
			renderer = new ImmediateModeRenderer();
		else
			renderer20 = new ImmediateModeRenderer20(false, true, 0);
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
			initGlSettings();

			Gdx.gl20.glLineWidth(pos.getSystem().getScale() * 0.04f);
			Matrix4 p = new Matrix4();
			Matrix4 s = new Matrix4();
			Matrix4 x = new Matrix4(projMatrix);
			s.setToScaling(size, size, size);
			p.setToRotation(Vector3.Z, angle);
			p.trn(pos.x, pos.y, 0);
			x.mul(p);
			x.mul(s);
			renderer20.begin(x, GL10.GL_LINES);
			for (Vector3 v : lines) {
				renderer20.color(color.r, color.g, color.b, color.a);
				renderer20.vertex(v.x, v.y, v.z);
			}

			renderer20.end();
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
		if (renderer != null) {
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
		} else {
			Gdx.gl20.glLineWidth(pos.getSystem().getScale() * 0.04f);
			Matrix4 p = new Matrix4();
			Matrix4 s = new Matrix4();
			Matrix4 x = new Matrix4(projMatrix);
			s.setToScaling(size, size, size);
			p.setToRotation(Vector3.Z, angle);
			p.trn(pos.x, pos.y, 0);
			x.mul(p);
			x.mul(s);
			renderer20.begin(x, GL10.GL_TRIANGLES);
			for (Vector3 v : lines) {
				renderer20.color(color.r, color.g, color.b, color.a);
				renderer20.vertex(v.x, v.y, v.z);
			}

			renderer20.end();
		}

	}

	@Override
	public void drawRect(float x0, float y0, float x1, float y1, Color c) {
		if (renderer != null) {
			Gdx.gl10.glLineWidth(3);
			initGlSettings();
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
		} else {
			Gdx.gl20.glLineWidth(3);
			initGlSettings();
			renderer20.begin(projMatrix, GL10.GL_LINE_LOOP);

			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x0, y0, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x1, y0, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x1, y1, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x0, y1, 0);

			renderer20.end();
		}
	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {

		if (renderer != null) {
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
		} else {
			initGlSettings();

			renderer20.begin(projMatrix, GL10.GL_TRIANGLES);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x0, y0, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x1, y1, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x1, y0, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x0, y0, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x1, y1, 0);
			renderer20.color(c.r, c.g, c.b, c.a);
			renderer20.vertex(x0, y1, 0);
			renderer20.end();
		}
	}

	@Override
	public void drawText(int i, int j, String string, Color c) {
		drawText(i, j, string, c, 1);
	}

	public void drawText(int i, int j, String string, Color c, float scale) {
		if (renderer != null)
			Gdx.gl10.glPushMatrix();
		spriteBatch.begin();
		font.setScale(scale + 0.001f);

		TextBounds bounds = font.getMultiLineBounds(string);
		spriteBatch.setColor(c);
		// spriteBatch.setBlendFunction(GL10.GL_ONE,
		// GL10.GL_ONE_MINUS_SRC_ALPHA);
		font.setColor(c);
		font.drawMultiLine(spriteBatch, string, i, j,
		// 160 + bounds.height / 2,
				bounds.width, HAlignment.CENTER);
		spriteBatch.end();
		if (renderer != null)
			Gdx.gl10.glPopMatrix();

	}

	@Override
	public void drawText(Position position, String money, Color moneyColor) {
		drawText((int) position.x, (int) position.y, money, moneyColor);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int renderMode) {
		render(sprite, pos, size, angle, renderMode, Color.WHITE);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int renderMode, Color color) {
		if (Gdx.gl10 != null) {

			Gdx.gl10.glPushMatrix();
			initGlSettings();
			Gdx.gl10.glTranslatef(pos.x, pos.y, 0);
			Gdx.gl10.glRotatef(angle, 0, 0, 1);

			Gdx.gl10.glColor4f(color.r, color.g, color.b, color.a);
			Gdx.gl10.glScalef(size, size, size);
			Gdx.gl10.glLineWidth(pos.getSystem().getScale() * 0.04f);

			sprite.render(renderMode);

			Gdx.gl10.glPopMatrix();
		} else {
			/*
			Matrix4 p = new Matrix4();

			p.setToRotation(Vector3.Z, angle);
			p.trn(pos.x, pos.y, 0);
			p.mul(projMatrix);
			*/
			initGlSettings();
			Matrix4 p = new Matrix4();
			Matrix4 s = new Matrix4();
			Matrix4 x = new Matrix4(projMatrix);
			//size*=pos.getSystem().getScale();
			s.setToScaling(size, size, size);
			p.setToRotation(Vector3.Z, angle);
			p.trn(pos.x, pos.y, 0);
			x.mul(p);
			x.mul(s);

			//Gdx.gl20.glBlendColor(color.r, color.g, color.b, color.a);
			//Gdx.gl20.glScalef(size, size, size);
			Gdx.gl20.glLineWidth(pos.getSystem().getScale() * 0.04f);
//			x=new Matrix4();

			sprite.render(x, renderMode);
		}
	}

	public static void pushMatrix() {
		matrixStack.add(new Matrix4(projMatrix));

	}

	public static void popMatrix() {
		projMatrix = matrixStack.get(matrixStack.size() - 1);
		matrixStack.remove(matrixStack.size() - 1);
	}

	public static void scaleMatrix(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.setToScaling(x, y, z);
		//m.mul(projMatrix);
		//projMatrix = m;
		projMatrix.mul(m);
	}

	public static void translateMatrix(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.setToTranslation(x, y, z);
		projMatrix.mul(m);

	}
}
