package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position.RefSystem;

public interface IRenderer {

	void drawRect(float f, float g, float h, float d,Color c);

	void drawRect(float f, float g, float h, float i, Color c, RefSystem level);

	void fillRect(float f, float g, float h, float i, Color c, RefSystem level);

	public void fillRect(float x0, float y0, float x1, float y1, Color c);

	void drawLines(Position pos, List<Vector3> lines, float angle, Color color,
			float size, RefSystem level);

	void drawPoly(Position pos, List<Vector3> lines, float angle, Color color,
			float size, RefSystem level);

	void drawText(int i, int j, String string, Color c);

	void drawText(Position position, String money, Color moneyColor);

}
