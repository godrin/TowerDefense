package com.cdm.view.elements.shots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Unit;

public class Explosion extends Unit implements Element {

	public static class Splinter {
		Position currentPosition;
		Vector3 speed;
		float currentAngle;
		float angleSpeed;
		Color color;
		public float size;
	}

	// private Position pos;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private List<Vector3> splinterPoly;
	private float size = getSize();
	private List<Splinter> splinters = new ArrayList<Splinter>();

	public Explosion(Position pos) {
		super(pos);
		Vector3 a = new Vector3(-1, -1, 0);
		Vector3 a1 = new Vector3(0, -0.5f, 0);
		Vector3 b = new Vector3(1, -1, 0);
		Vector3 b1 = new Vector3(0.5f, 0, 0);
		Vector3 c = new Vector3(1, 1, 0);
		Vector3 c1 = new Vector3(0, 0.5f, 0);
		Vector3 d = new Vector3(-1, 1, 0);
		Vector3 d1 = new Vector3(-0.5f, 0, 0);
		Vector3 e = new Vector3(-0.5f, 0, 0);

		Vector3 s1 = new Vector3(0, 0.5f, 0);
		Vector3 s2 = new Vector3(-0.25f, -0.25f, 0);
		Vector3 s3 = new Vector3(0.25f, -0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, a1, a1, b, b, b1, b1, c, c,
				c1, c1, d, d, d1, d1, e, e, a });
		poly = Arrays.asList(new Vector3[] { a1, b1, c1, a1, c1, d1 });

		splinterPoly = Arrays.asList(new Vector3[] { s1, s2, s3 });

		for (int i = 0; i < 12; i++) {
			Splinter splinter = new Splinter();
			splinter.currentPosition = new Position(getPosition());
			splinter.currentAngle = (float) Math.random() * 180.0f;
			splinter.angleSpeed = (float) Math.random() * 180.0f - 90.0f;
			splinter.color = new Color(1, 0, 0, 1);
			splinter.size = getSize() * 0.5f + (float) Math.random() * 0.5f
					+ getSize();
			float maxSpeed = 2.0f;
			splinter.speed = new Vector3((float) Math.random() * maxSpeed
					- maxSpeed * 0.5f, (float) Math.random() * maxSpeed
					- maxSpeed * 0.5f, 0);
			splinters.add(splinter);
		}

	}

	public void draw(IRenderer renderer) {
		if (size >= 0.0f) {

			Color outerColor = new Color(0.5f, 0.5f, 0, 1.0f);

			// @undermink: this is how it was meant ??
			renderer.drawLines(getPosition(), lines, 180, outerColor, size);

			Color innerColor = new Color(1, 0, 0, 1);
			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor, size);
			renderer.drawPoly(getPosition(), poly, 180, outerColor, size);
		}

		for (Splinter splinter : splinters) {
			if (splinter.size > 0)
				renderer.drawPoly(splinter.currentPosition, splinterPoly,
						splinter.currentAngle, splinter.color, splinter.size);

		}
	}

	public void move(float time) {
		for (Splinter splinter : splinters) {

			splinter.currentPosition.x += splinter.speed.x * time;
			splinter.currentPosition.y += splinter.speed.y * time;

			splinter.currentAngle += splinter.angleSpeed * time;
			splinter.size -= time * 0.3f;

		}

		// shrink
		size -= time * 0.2f;
		if (size < 0) {
			// FIXME: remove from level
		}

	}

}
