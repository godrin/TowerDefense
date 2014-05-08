package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

//review1
public class UnitRenderer implements IRenderer {
	private IRenderer r;

	public UnitRenderer(IRenderer i) {
		r = i;
	}

	@Override
	public void drawRect(float f, float g, float h, float d, Color c) {
		r.drawRect(f, g, h, d, c);
	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {
		r.fillRect(x0, y0, x1, y1, c);

	}

	@Override
	public void drawLines(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		if (pos.buttonPos())
			size *= Position.BUTTON_REF.getScale();
		if (pos.screenPos())
			size *= Position.LEVEL_REF.getScale();
		r.drawLines(pos, lines, angle, color, size);
	}

	@Override
	public void drawPoly(Position pos, List<Vector3> lines, float angle,
			Color color, float size) {
		if (pos.buttonPos())
			size *= Position.BUTTON_REF.getScale();
		if (pos.screenPos())
			size *= Position.LEVEL_REF.getScale();
		r.drawPoly(pos, lines, angle, color, size);
	}

	@Override
	public void drawText(float i, float j, CharSequence string, Color c) {
		r.drawText(i, j, string, c);
	}

	@Override
	public void drawText(Position position, CharSequence money, Color moneyColor) {
		r.drawText(position, money, moneyColor);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int renderMode) {
		if (pos.buttonPos())
			size *= Position.BUTTON_REF.getScale();
		if (pos.screenPos())
			size *= Position.LEVEL_REF.getScale();
		r.render(sprite, pos, size, angle, renderMode);
	}

	@Override
	public void drawText(float i, float j, CharSequence string, Color c,
			float scale) {
		r.drawText(i, j, string, c, scale);
	}

	@Override
	public void render(PolySprite sprite, Position pos, float size,
			float angle, int glTriangles, Color color) {
		r.render(sprite, pos, size, angle, glTriangles, color);
	}

	@Override
	public void render(WorldCallback callback, Position pos, float size,
			float angle) {
		r.render(callback, pos, size, angle);
	}

	@Override
	public void drawText(float i, float j, CharSequence string, Color c,
			float scale, boolean positionCenter, boolean positionMiddle) {
		r.drawText(i, j, string, c, scale, positionCenter, positionMiddle);
	}

}
