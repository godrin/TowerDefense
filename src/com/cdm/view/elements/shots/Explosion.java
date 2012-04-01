package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Unit;

public class Explosion extends Unit implements Element {

	// private Position pos;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private float size = getSize();

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
		Vector3 e1 = new Vector3();

		lines = Arrays.asList(new Vector3[] { a, a1, a1, b, b, b1, b1, c, c,
				c1, c1, d, d, d1, d1, e, e, a });
		poly = Arrays.asList(new Vector3[] {a1,b1,c1,a1,c1,d1});

	}

	public void draw(IRenderer renderer) {
		if (size >= 0.0f) {
			Color outerColor = new Color(0.5f, 0.5f, 0, 1.0f);
			renderer.drawLines(getPosition(), lines, 0, outerColor, size,
					RefSystem.Level);
			Color innerColor = new Color(1, 0, 0, 1);
			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor,
					size, RefSystem.Level);
			renderer.drawPoly(getPosition(), poly, 180 - 45, outerColor,
					size, RefSystem.Level);
			size = shrink(size);
		}
	}

	@Override
	public void setPosition(Position pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(float time) {

	}

	public float shrink(float size) {
		size -= 0.1;
		return size;
	}

}
