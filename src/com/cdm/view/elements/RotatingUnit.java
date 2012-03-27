package com.cdm.view.elements;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.shots.SimpleShot;
import com.cdm.view.enemy.EnemyUnit;

public abstract class RotatingUnit extends Unit {

	private RotatingThing rotation = new RotatingThing();
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
				rotation.setTargetAngle(MathTools.angle(delta));
			}
		}

		ableToShoot = rotation.move(time)<time;
	}

	public float getAngle() {
		return rotation.getCurrentAngle();
	}

	public void setTurningSpeed(float s) {
		rotation.setTurningSpeed(s);
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