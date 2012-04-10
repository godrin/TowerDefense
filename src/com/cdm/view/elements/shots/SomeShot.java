package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class SomeShot extends MovingShot {

	public static float speed = 1f;
	private List<Vector3> lines;
	private List<Vector3> poly;

	public SomeShot(Position from, Position to, Level plevel) {
		super(from, to, plevel);
		Vector3 a = new Vector3(0, -0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0, 0);
		Vector3 c = new Vector3(0, 0.4f, 0);
		Vector3 d = new Vector3(0, 0.2f, 0);
		Vector3 e = new Vector3(-0.5f, 0.2f, 0);
		Vector3 f = new Vector3(-0.75f, 0.5f, 0);
		Vector3 g = new Vector3(-0.75f, -0.5f, 0);
		Vector3 h = new Vector3(-0.5f, -0.2f, 0);
		Vector3 i = new Vector3(0, -0.2f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, e, e, f, f,
				g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, d, e, i, i, h, e, h, g,
				f, f, g, i, f, g, d });

	}

	public float getSize() {
		return 0.2f;

	}

	@Override
	public void draw(IRenderer renderer) {

		renderer.drawPoly(getPosition(), poly, angle,
				new Color(1, 0, 0, 1.0f), getSize());
		renderer.drawLines(getPosition(), lines, angle,
				new Color((float) Math.random() + 1, (float) Math.random(),
						(float) Math.random(), 1.0f), getSize());
	}

	@Override
	public float getSpeed() {
		return speed;
	}
	
	public static float getImpact() {
		return 25;
	}

}
