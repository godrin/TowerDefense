package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public class Cannon extends Unit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;
	float targetAngle = angle;
	float turningSpeed = 45.0f; // degrees per second
	float shotFrequency = 1.0f;
	float lastShot = 0.0f;

	public Cannon(Position p) {
		super(p);
		Vector3 a = new Vector3(-0.95f, 0.25f, 0);
		Vector3 b = new Vector3(-0.25f, 0.75f, 0);
		Vector3 c = new Vector3(0.25f, 0.75f, 0);
		Vector3 d = new Vector3(0.85f, 0.25f, 0);
		Vector3 d2 = new Vector3(0.85f, -0.25f, 0);
		Vector3 e = new Vector3(0.25f, -0.75f, 0);
		Vector3 f = new Vector3(-0.25f, -0.75f, 0);
		Vector3 g = new Vector3(-0.95f, -0.25f, 0);
		Vector3 h = new Vector3(-0.5f, -0.25f, 0);
		Vector3 i = new Vector3(-0.5f, 0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2, e,
				e, f, f, g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d, h,
				d2, g, f, d2, f, d2, e });

	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0, 0, 0.6f, 1.0f);
		renderer.drawPoly(getPosition(), poly, angle, innerColor, getSize());
		Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
		renderer.drawLines(getPosition(), lines, angle, outerColor, getSize());
	}

	@Override
	public void move(float time) {

		EnemyUnit enemy = getLevel().getNextEnemy(getPosition());
		lastShot += time;
		if (enemy != null) {
			Vector3 delta = enemy.getPosition().to(getPosition());
			targetAngle = MathTools.angle(delta);

			if (lastShot > shotFrequency) {
				lastShot = 0.0f;
				getLevel().addShot(
						new AbstractShot(getPosition(),
								anticipatePosition(enemy), getLevel()));

			}

		}
		float turnVec = targetAngle - angle;
		float turnDir = Math.signum(turnVec);
		float target = angle + turnDir * turningSpeed * time;
		if (Math.signum(targetAngle - target) != turnDir) {
			// reached
			angle = targetAngle;
		} else {
			angle = target;
		}

		// angle += time * 10;
	}

	private Position anticipatePosition(EnemyUnit enemy) {
		float enemyDistance = getPosition().to(enemy.getPosition()).len();
		float enemyMoveDistance = (enemyDistance / AbstractShot.speed)
				* enemy.getSpeed();

		Vector3 result = enemy.getPosition().toVector()
				.add(enemy.getMovingDirection().mul(enemyMoveDistance));
		return new Position(result.x, result.y, RefSystem.Level);
	}
}
