package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public interface IRenderer {

	void drawLines(float x, float y, List<Vector3> lines, float angle,
			float scale, Color color);

	void drawRect(float f, float g, float h, float d);

	public void fillRect(float x0, float y0, float x1, float y1, Color c);

	void drawPoly(float x, float y, List<Vector3> lines, float angle,
			float scale, Color color);

}
