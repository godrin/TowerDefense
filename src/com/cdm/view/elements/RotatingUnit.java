package com.cdm.view.elements;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.shots.SimpleShot;
import com.cdm.view.enemy.EnemyUnit;

public abstract class RotatingUnit extends Unit {
	float turningSpeed = 45.0f; // degrees per second

	protected float angle = 0.0f;
	protected float targetAngle = angle;
	protected boolean ableToShoot = false;
	private Vector3 delta = new Vector3();
	private Vector3 result = new Vector3();

	public RotatingUnit(Position p) {
		super(p);
	}

	protected abstract EnemyUnit getEnemy();

	protected abstract float getMaxDist();

	@Override
	public void move(float time) {

		EnemyUnit enemy = getEnemy();

		if (enemy != null) {
			delta.set(enemy.getPosition().to(getPosition()));
			if (delta.len() < getMaxDist()) {
				targetAngle = MathTools.angle(delta);

				if (targetAngle - 180 > angle)
					targetAngle -= 360;
				if (targetAngle + 180 < angle)
					targetAngle += 360;
			}
		}

		float turnVec = targetAngle - angle;
		float turnDir = Math.signum(turnVec);
		float target = angle + turnDir * turningSpeed * time;
		if (Math.signum(targetAngle - target) != turnDir
				|| Math.abs(targetAngle - target) < 0.5f) {
			// reached
			angle = targetAngle;
			ableToShoot = true;

		} else {
			angle = target;
			ableToShoot = false;
		}
	}

	public float getTurningSpeed() {
		return turningSpeed;
	}

	public void setTurningSpeed(float turningSpeed) {
		this.turningSpeed = turningSpeed;
	}

	protected Position anticipatePosition(EnemyUnit enemy) {
		float enemyDistance = getPosition().distance(enemy.getPosition());
		float enemyMoveDistance = (enemyDistance / SimpleShot.speed)
				* enemy.getSpeed();

		result.set(enemy.getPosition().toVector());
		result.add(enemy.getMovingDirection().mul(enemyMoveDistance));
		return new Position(result.x, result.y, RefSystem.Level);
	}

}