package com.cdm.view.elements.shots;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.MathTools;
import com.cdm.view.enemy.EnemyUnit;

/**
 * @author godrin
 * 
 *         abstract base class of all shots
 */
public abstract class MovingShot implements Element, DisplayEffect {

	Position pos;
	Position target;
	float angle;
	private Level level;
	private Vector3 deltaV = new Vector3();

	public MovingShot(Position from, Position to, Level plevel) {
		pos = new Position(from);
		target = new Position(to);
		level = plevel;

		angle = MathTools.angle(from.to(to));

	}

	protected float getSize() {
		return 0.15f;
	}

	protected Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public abstract float getSpeed();

	public void move(float time) {
		deltaV.set(pos.to(target));
		float distance = time * getSpeed();
		if (distance > deltaV.len()) {
			pos = target;
			EnemyUnit unit = level.getEnemyAt(target);
			if (unit != null)
				unit.wasHitBy(this);
			level.removeShot(this);
		} else {

			Vector3 nu = deltaV.nor().mul(distance);
			pos.x += nu.x;
			pos.y += nu.y;
		}

	}

	public float getLevel() {
		if (this.getClass()== SomeShot.class)
		return 3;
		if (this.getClass()== SimpleShot.class)
			return 1;
		else return 1;
	}

}
