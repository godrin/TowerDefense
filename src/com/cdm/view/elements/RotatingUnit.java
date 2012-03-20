package com.cdm.view.elements;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.enemy.EnemyUnit;

public abstract class RotatingUnit extends Unit {
	float turningSpeed = 45.0f; // degrees per second

	protected float angle = 0.0f;
	protected float targetAngle = angle;
	protected boolean ableToShoot = false;

	public RotatingUnit(Position p) {
		super(p);
	}

	protected abstract EnemyUnit getEnemy();

	protected abstract float getMaxDist();

	@Override
	public void move(float time) {

		EnemyUnit enemy = getEnemy();

		if (enemy != null) {
			Vector3 delta = enemy.getPosition().to(getPosition());
			if (delta.len() < getMaxDist()) {
				targetAngle = MathTools.angle(delta);
			}
		}

		float turnVec = targetAngle - angle;
		float turnDir = Math.signum(turnVec);
		float target = angle + turnDir * turningSpeed * time;
		if (Math.signum(targetAngle - target) != turnDir) {
			// reached
			angle = targetAngle;
			ableToShoot = true;

		} else {
			angle = target;
			ableToShoot = false;
		}

		// angle += time * 10;
	}

}