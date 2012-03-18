package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;

public class SmallShip extends EnemyUnit implements Element {

	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);
	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;
	private float speed = 0.3f;

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

		renderer.drawPoly(getPosition(), poly, angle, new Color(0, 0, 0, 0),
				getSize(), RefSystem.Level);
		renderer.drawLines(getPosition(), lines, angle, new Color(0.9f, 0, 0,
				1.0f), getSize(), RefSystem.Level);

	}

	@Override
	public void move(float time) {

		Position pos = getPosition();
		if (pos.x < 26) {
			pos.x += time * speed;
		} else
			pos.x = -2;
		setPosition(pos);

	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public Vector3 getMovingDirection() {
		return DIRECTION;
	}
}
