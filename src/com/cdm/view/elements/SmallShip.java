package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class SmallShip extends EnemyUnit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;

	public SmallShip(Position position) {
		super(position);
		Vector3 a = new Vector3(-0.75f, 0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0.0f, 0);
		Vector3 c = new Vector3(-0.75f, -0.4f, 0);
		Vector3 d = new Vector3(-0.25f, 0, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, a, });
		poly = Arrays.asList(new Vector3[] { a, b, d, b, c, d });

	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);
		renderer.drawPoly(pos, poly, angle, new Color(0.5f, 0, 0, 1.0f));
		renderer.drawLines(pos, lines, angle, new Color(0.9f, 0, 0, 1.0f));
	}

	@Override
	public void move(float time) {
		pos.x += time * 0.3;
	}


}
