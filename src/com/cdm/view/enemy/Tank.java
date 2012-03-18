package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public class Tank extends EnemyUnit {

	public Position nextStep = null;
	private float speed = 0.2f;

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;

	public Tank(Position pos) {
		super(pos);
		Vector3 c0 = new Vector3(-1, -1, 0);
		Vector3 c1 = new Vector3(1, -1, 0);
		Vector3 c2 = new Vector3(1, 1, 0);
		Vector3 c3 = new Vector3(-1, 1, 0);

		lines = Arrays.asList(new Vector3[] { c0, c1, c1, c2, c2, c3, c3, c0 });
		poly = Arrays.asList(new Vector3[] { c0, c1, c2, c0, c2, c3 });

	}

	@Override
	public void move(float time) {
		while (time > 0) {
			if (nextStep == null) {
				nextStep = getLevel().getNextPos(getPosition().alignToGrid());
			}
			Position nuPos = new Position(getPosition());

			Vector3 diff = getPosition().to(nextStep);
			float len = diff.len();
			float delta = time * speed;

			if (delta >= len) {
				setPosition(nextStep);
				time -= len / delta;
				nextStep = null;
			} else {
				diff.mul(delta / diff.len());
				nuPos.x += diff.x;
				nuPos.y += diff.y;
				setPosition(nuPos);
				time = 0;
			}

		}
	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0.7f, 0, 0.6f, 1.0f);
		renderer.drawPoly(getPosition(), poly, angle, innerColor, getSize(),
				RefSystem.Level);
		Color outerColor = new Color(0.7f, 0.2f, 1.0f, 1.0f);
		renderer.drawLines(getPosition(), lines, angle, outerColor, getSize(),
				RefSystem.Level);
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public Vector3 getMovingDirection() {
		if (nextStep != null)
			return getPosition().to(nextStep).nor();
		return new Vector3(1, 0, 0);
	}

}
