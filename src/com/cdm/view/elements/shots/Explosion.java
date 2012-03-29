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
	private float size = getSize() + 1;

	public Explosion(Position pos) {
		super(pos);
		Vector3 a = new Vector3(-1, -1, 0);
		Vector3 b = new Vector3(1, -1, 0);
		Vector3 c = new Vector3(1, 1, 0);
		Vector3 d = new Vector3(-1, 1, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d });
	}

	public void draw(IRenderer renderer) {
		while (size >= 0.0f) {
			/*Color innerColor = new Color(0.4f, 0, 0, 0);
			renderer.drawPoly(getPosition(), poly, 180, innerColor, size,
					RefSystem.Level);*/
			Color outerColor = new Color(1.7f, 0, 0, 1.0f);
			renderer.drawLines(getPosition(), lines, 180, outerColor, size,
					RefSystem.Level);
			size = shrink(size);
		}
	}

	@Override
	public void setPosition(Position pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(float time) {
		// TODO Auto-generated method stub

	}

	public float shrink(float size) {
		size -= 0.01;
		return size;
	}

}
