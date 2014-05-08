package com.cdm.view.elements.units;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.elements.MathTools;
import com.cdm.view.elements.RotatingThing;
import com.cdm.view.enemy.EnemyUnit;

public abstract class RotatingUnit extends PlayerUnit {

	private RotatingThing rotation = new RotatingThing();
	protected boolean ableToShoot = false;
	private Vector3 delta = new Vector3();

	public RotatingUnit(Position p) {
		super(p);
	}

	protected abstract EnemyUnit getEnemy();

	@Override
	public void move(float time) {

		EnemyUnit enemy = getEnemy();

		ableToShoot = false;
		if (enemy != null) {
			delta.set(enemy.getPosition().to(getPosition()));
			if (delta.len() < getMaxDist() && delta.len() > getMinDist()) {
				rotation.setTargetAngle(MathTools.angle(delta));
				ableToShoot = rotation.move(time) < time;
			}
		}

	}

	private float getMinDist() {
		return 0.8f;
	}

	public float getAngle() {
		return rotation.getCurrentAngle();
	}

	public void setTurningSpeed(float s) {
		rotation.setTurningSpeed(s);
	}


	@Override
	public Vector3 getMovingDirection() {
		return new Vector3(0, 0, 0);
	}

}
