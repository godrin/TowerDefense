package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.enemy.EnemyUnit;

public class RocketThrower extends Unit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;
	float targetAngle = angle;
	float shotFrequency = 5.0f;
	float lastShot = 0.0f;
	float turningSpeed = 90.0f;
	private float targetDist;
	private float maxDist = 3.0f;
	private double startingRadius = 0.4f;

	public RocketThrower(Position p) {
		super(p);
		Vector3 c0 = new Vector3(-1, -1, 0);
		Vector3 c1 = new Vector3(1, -1, 0);
		Vector3 c2 = new Vector3(1, 1, 0);
		Vector3 c3 = new Vector3(-1, 1, 0);

		lines = Arrays.asList(new Vector3[] { c0, c1, c1, c2, c2, c3, c3, c0 });
		poly = Arrays.asList(new Vector3[] { c0, c1, c2, c0, c2, c3 });

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
			if (delta.len() < maxDist) {
				targetAngle = MathTools.angle(delta);
				targetDist = delta.len();
			}
		}

		float turnVec = targetAngle - angle;
		float turnDir = Math.signum(turnVec);
		float target = angle + turnDir * turningSpeed * time;
		if (Math.signum(targetAngle - target) != turnDir) {
			// reached
			angle = targetAngle;

			shoot(enemy);
		} else {
			angle = target;
		}

		// angle += time * 10;
	}
	private void shoot(EnemyUnit enemy) {
		if (enemy != null) {

			if (lastShot > shotFrequency && targetDist < maxDist) {
				lastShot = 0.0f;
				Position startingPos = new Position(getPosition());
				float angle = targetAngle;
				startingPos.x -= Math.cos(angle * MathTools.M_PI / 180.0f)
						* startingRadius;
				startingPos.y -= Math.sin(angle * MathTools.M_PI / 180.0f)
						* startingRadius;
				getLevel().addShot(
						new AbstractShot(startingPos,
								anticipatePosition(enemy), getLevel()));
				SoundFX.shot.play();

			}

		}
	}

	private Position anticipatePosition(EnemyUnit enemy) {
		float enemyDistance = getPosition().to(enemy.getPosition()).len();
		float enemyMoveDistance = (enemyDistance / AbstractShot2.speed2)
				* enemy.getSpeed();

		Vector3 result = enemy.getPosition().toVector()
				.add(enemy.getMovingDirection().mul(enemyMoveDistance));
		return new Position(result.x, result.y, RefSystem.Level);
	}

}
