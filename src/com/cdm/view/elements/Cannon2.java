package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class Cannon2 extends Unit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;

	public Cannon2(Position p) {
		super(p);
		Vector3 a = new Vector3(-1, 0.25f, 0);
		Vector3 b = new Vector3(-0.25f, 0.75f, 0);
		Vector3 c = new Vector3(0.25f, 0.75f, 0);
		Vector3 d = new Vector3(0.85f, 0.25f, 0);
		Vector3 d2 = new Vector3(0.85f, -0.25f, 0);
		Vector3 e = new Vector3(0.25f, -0.75f, 0);
		Vector3 f = new Vector3(-0.25f, -0.75f, 0);
		Vector3 g = new Vector3(-1, -0.25f, 0);
		Vector3 h = new Vector3(-0.5f, -0.25f, 0);
		Vector3 i = new Vector3(-0.5f, 0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2, e,
				e, f, f, g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d, h,
				d2, g, f, d2, f, d2, e });

	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0, 0, 0.6f, 1.0f);
		renderer.drawPoly(pos, poly, angle, innerColor);
		Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
		renderer.drawLines(pos, lines, angle, outerColor);
	}

	@Override
	public void move(float time) {
		angle += time * 10;
	}

}
