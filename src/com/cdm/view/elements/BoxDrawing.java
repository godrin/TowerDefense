package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public class BoxDrawing {
	float size = 2.0f;
	Color color = new Color(0, 0, 1, 1);
	Position pos = new Position(0, 0, RefSystem.Level);

	// upper box
	Vector3 a, b, c, d, e, f, g, h;

	List<Vector3> lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, e, f,
			f, g, g, h });
	float angle = 0.0f;

	public BoxDrawing(Position start, Position end, int gh) {

		// upper box
		a = new Vector3(0, start.y - 0.5f, 0);
		b = new Vector3(0, 0, 0);
		c = new Vector3(end.x - 0.5f, 0, 0);
		d = new Vector3(end.x - 0.5f, start.y - 0.5f, 0);
		// lower box
		e = new Vector3(0, start.y + 0.5f, 0);
		f = new Vector3(0, gh - 0.5f, 0);
		g = new Vector3(end.x - 0.5f, gh - 0.5f, 0);
		h = new Vector3(end.x - 0.5f, start.y + 0.5f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, e, f, f, g, g,
				h });

	}

	public void draw(IRenderer renderer) {

		renderer.drawLines(pos, lines, angle, color, size, RefSystem.Level);

	}
}
