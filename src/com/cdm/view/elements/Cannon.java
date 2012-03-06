package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;

public class Cannon extends Unit implements Element {

	private List<Vector3> lines;
	float angle = 0.0f;

	public Cannon(int x, int y) {
		super(x, y);
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

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawLines(x*CELL_WIDTH, y*CELL_WIDTH, lines, angle,CELL_WIDTH);
	}

	@Override
	public void move(float time) {
		angle += time * 10;
	}

}
