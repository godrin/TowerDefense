package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
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

		lines = Arrays.asList(new Vector3[] { a, a1, a1, b, b, b1, b1, c, c,
				c1, c1, d, d, d1, d1, e, e, a });
		poly = Arrays.asList(new Vector3[] { a1, b1, c1, a1, c1, d1 });

	}

	public void draw(IRenderer renderer) {
		if (size >= 0.0f) {
			Color outerColor = new Color(0.5f, 0.5f, 0, 1.0f);

			// @undermink: this is how it was meant ??
			renderer.drawLines(getPosition(), lines, 180, outerColor, size);
			Color innerColor = new Color(1, 0, 0, 1);
			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor, size);
			renderer.drawPoly(getPosition(), poly, 180 - 45, outerColor, size);
			
			
			// @undermink - moved to move()
			// size = shrink(size);
		}
	}

	@Override
	public void move(float time) {
		// shrink
		size -= time * 0.2f;
		if (size < 0) {
			// remove from level
		}

	}

	@Deprecated
	public float shrink(float size) {
		size -= 0.1;
		return size;
	}

}
