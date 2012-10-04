package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public interface IRenderer {

	void drawRect(float x0, float y0, float x1, float y1, Color c);

	public void fillRect(float x0, float y0, float x1, float y1, Color c);

	void drawLines(Position pos, List<Vector3> lines, float angle, Color color,
			float size);

	void drawPoly(Position pos, List<Vector3> lines, float angle, Color color,
			float size);

	void drawText(float i, float j, CharSequence charSequence, Color c);

	void drawText(Position position, CharSequence money, Color moneyColor);

	public void drawText(float i, float j, CharSequence string, Color c,
			float scale);

	public void drawText(float i, float j, CharSequence string, Color c,
			float scale, boolean positionCenter, boolean positionMiddle);

	public void render(PolySprite sprite, Position pos, float size,
			float angle, int glTriangles);

	public void render(PolySprite sprite, Position pos, float size,
			float angle, int glTriangles, Color color);

	public void render(WorldCallback callback, Position pos, float size,
			float angle);

}
