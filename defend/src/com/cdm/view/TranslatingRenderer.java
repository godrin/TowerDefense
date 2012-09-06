package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class TranslatingRenderer implements IRenderer {
	private IRenderer orig;
	private float tx = 0;

	public TranslatingRenderer(IRenderer r) {
		orig = r;
	}

	public void setTranslationX(float x) {
		tx = x;
	}

	@Override
	public void drawRect(float x0, float y0, float x1, float y1, Color c) {
		orig.drawRect(x0 + tx, y0, x1 + tx, y1, c);

	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {
		orig.fillRect(x0 + tx, y0, x1 + tx, y1, c);
	}

	@Override
	public void drawLines(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		orig.drawLines(pos, lines, angle, color, size);
	}

	@Override
	public void drawPoly(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		orig.drawPoly(pos, lines, angle, color, size);

	}

	@Override
	public void drawText(int i, int j, String string, Color c) {
		orig.drawText(i + (int) tx, j, string, c);
	}

	@Override
	public void drawText(Position position, String money, Color moneyColor) {
		orig.drawText(position, money, moneyColor);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int glTriangles) {
		orig.render(sprite, pos, size, angle, glTriangles);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int glTriangles, Color color) {
		orig.render(sprite, pos, size, angle, glTriangles, color);
	}

	@Override
	public void drawText(int i, int j, String string, Color c, float scale) {
		orig.drawText(i, j, string, c, scale);
	}

}
