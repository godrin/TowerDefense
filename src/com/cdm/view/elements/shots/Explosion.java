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
	private List<Vector3> lines2;
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

		Vector3 aa = new Vector3(0, -1, 0);
		Vector3 aa1 = new Vector3(-0.5f, -0.5f, 0);
		Vector3 bb = new Vector3(1, 0, 0);
		Vector3 bb1 = new Vector3(0.5f, 0.5f, 0);
		Vector3 cc = new Vector3(0, -1, 0);
		Vector3 cc1 = new Vector3(-0.5f, 0.5f, 0);
		Vector3 dd = new Vector3(-1, 0, 0);
		Vector3 dd1 = new Vector3(-0.5f, -0.5f, 0);

		lines = Arrays.asList(new Vector3[] { a, a1, a1, b, b, b1, b1, c, c,
				c1, c1, d, d1, d1, a });
		lines2 = Arrays.asList(new Vector3[] { aa, aa1, aa1, bb, bb, bb1, bb1,
				cc, cc, cc1, cc1, dd, dd1, dd1, aa });
	}

	public void draw(IRenderer renderer) {
		while (size >= 0.0f) {
			Color outerColor = new Color(0.5f, 0.5f, 0, 1.0f);
			renderer.drawLines(getPosition(), lines, 180, outerColor, size);
			Color innerColor = new Color(1, 0, 0, 1);
			renderer.drawLines(getPosition(), lines2, 180, innerColor, size);
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
