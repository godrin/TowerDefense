package com.cdm.view.enemy;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.elements.MathTools;
import com.cdm.view.elements.RotatingThing;
import com.cdm.view.elements.units.Unit;

public abstract class AirMovingEnemy extends EnemyUnit {

	private static Position invalidPos = new Position(-1, -1,
			Position.LEVEL_REF);
	private static Position FightPos = new Position(0, 0, Position.LEVEL_REF);
	private Position nextStep = new Position(invalidPos);
	public static final float SPEED = 0.2f;

	private static final Vector3 DEFAULT_DIRECTION = new Vector3(1, 0, 0);

	private Vector3 diff = new Vector3();
	private Vector3 movingDir = new Vector3();
	private RotatingThing rotation = new RotatingThing();
	private float attackfreq = 0.0f;

	public AirMovingEnemy(Position pos) {
		super(pos);
	}

	@Override
	public void move(float time) {
		super.move(time);
		while (time > 0) {
			if (nextStep.equals(invalidPos)) {
				nextStep.set(getLevel().getNextStepToUnit(
						getPosition().tmp().alignedToGrid()));
				return;
			}
			if (nextStep.equals(FightPos)) {
				attack(getLevel()
						.getPlayerUnitAt(getPosition().alignedToGrid()));
				// getLevel().removeMeFromGrid(this.getPosition(), this);
				nextStep.set(getPosition().x + 20, getPosition().y,
						Position.LEVEL_REF);
				return;
			}
			if (!nextStep.valid()) {
				nextStep.set(invalidPos);
				// getLevel().removeMeFromGrid(getPosition(), this);
				return;
			}

			if (getPosition().x >= 20) {
				getLevel().enemyReachedEnd(this);
			}
			Position nuPos = new Position(getPosition());

			diff.set(getPosition().to(nextStep));

			float targetAngle = MathTools.angle(diff);
			rotation.setTargetAngle(targetAngle);
			time -= rotation.move(time);

			if (time < 0.00001f)
				return;

			float len = diff.len();
			float delta = time * getSpeed();

			if (delta >= len) {
				setPosition(nextStep);
				time -= len / delta;
				nextStep.set(invalidPos);
			} else {
				diff.mul(delta / diff.len());
				nuPos.x += diff.x;
				nuPos.y += diff.y;
				setPosition(nuPos);
				time = 0;

			}
		}
	}

	protected float getAngle() {
		return rotation.getCurrentAngle();
	}

	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

	public void setTurningSpeed2(float speed) {
		rotation.setTurningSpeed(speed);
	}

	@Override
	public Vector3 getMovingDirection() {
		if (nextStep != null)
			return movingDir.set(getPosition().to(nextStep).nor());
		return DEFAULT_DIRECTION;
	}

	@Override
	public int getZLayer() {
		return 0;
	}

	public void attack(Unit unit) {

		// if (attackfreq > 2.0f) {
		// attackfreq = 0.0f;
		if (unit != null) {
			getLevel().unitDestroyed(unit.getPosition(), unit);
		} else
			nextStep.set(invalidPos);
	}
}
