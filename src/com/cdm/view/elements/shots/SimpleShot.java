package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class SimpleShot extends MovingShot {
	public static final float SPEED = 1.5f;
	private List<Vector3> lines;
	private List<Vector3> poly;

	public SimpleShot(Position from, Position to, Level plevel, float impact) {
		super(from, to, plevel, impact);
		Vector3 a = new Vector3(-0.75f, 0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0.0f, 0);
		Vector3 c = new Vector3(-0.75f, -0.4f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, a });
		poly = Arrays.asList(new Vector3[] { a, b, c });
	}

	@Override
	public float getSpeed() {
		return SPEED;
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, angle, new Color(0.5f, 0, 0,
				1.0f), getSize());
		renderer.drawLines(getPosition(), lines, angle, new Color(0.9f, 0, 0,
				1.0f), getSize());

	}

	@Override
	public void drawAfter(IRenderer renderer) {
		// TODO Auto-generated method stub

	}

}
