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

	Position from;
	Position pos;
	Position target;
	Position inbetween;
	float angle;
	private Level level;
	private Vector3 deltaV = new Vector3();
	private float impact;
	private float allTime = -1;
	private float curTime = 0;

	private final static Vector3 a = new Vector3();
	private final static Vector3 b = new Vector3();
	private final static Vector3 c = new Vector3();

	public MovingShot(Position pfrom, Position to, Level plevel, float pImpact) {

		from = new Position(pfrom);
		pos = new Position(pfrom);
		a.set(to.x - from.x, to.y - from.y, 0);
		a.nor();
		a.crs(0, 0, -1);
		a.mul(0.5f);

		inbetween = new Position(a.x + (to.x * 0.75f + from.x * 0.25f), a.y
				+ (to.y * 0.75f + from.y * 0.25f), Position.LEVEL_REF);
		target = new Position(to);
		level = plevel;
		impact = pImpact;

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
		if (allTime < 0) {
			allTime = from.lengthTo(target) / getSpeed();
		}
		curTime += time;

		if (curTime > allTime) {
			pos = target;
			EnemyUnit unit = level.getEnemyAt(target);
			if (unit != null)
				unit.wasHitBy(this);
			level.removeShot(this);
		} else {
			float g = curTime / allTime;
			float g1 = 1 - g;

			a.set(from.x * g1 + inbetween.x * g, from.y * g1 + inbetween.y * g,
					0);
			b.set(inbetween.x * g1 + target.x * g, inbetween.y * g1 + target.y
					* g, 0);
			c.set(a.x * g1 + b.x * g, a.y * g1 + b.y * g, 0);
			pos.x = c.x;
			pos.y = c.y;
			
			if(!deltaV.isZero()) {
				a.set(pos.x-deltaV.x,pos.y-deltaV.y,0);
				angle=MathTools.angle(a);
			}
			deltaV.set(pos.x,pos.y,0);
			
			
		}
		afterMove(pos);
	}

	protected void afterMove(Position pos2) {
	}

	public void moveOld(float time) {
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

	public float getImpact() {
		return impact;
	}

	@Override
	public int compareTo(Element arg0) {
		return arg0.hashCode() - this.hashCode();
	}

}
