package com.cdm.view.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.shots.MovingShot;
import com.cdm.view.elements.units.Unit;

public abstract class EnemyUnit extends Unit {

	private static final float FREEZE_FACTOR = 0.5f;
	private static final Color BORDER_COLOR = new Color(1, 0, 0, 0.8f);
	private static final Color FILL_COLOR = new Color(1, 0, 0, 0.7f);

	private float energy = -1;
	private float orignalEnergy = -1;
	private float frozenTime = 0.0f;
	private float speed = -10.0f;
	private ShakingLines shaking = new ShakingLines();

	public EnemyUnit(Position pos) {
		super(pos);
	}

	public void setEnergy(float e) {

		if (energy < 0) {
			orignalEnergy = e;

			energy = e;
		}
	}

	public ShakingLines getShakingLines() {
		return shaking;
	}

	public abstract int getMoney();

	public abstract int getPoints();

	public abstract int getBonus();

	@Override
	public void draw(IRenderer renderer) {
		/*
		 * if (true) return;
		 */
		drawEnergyBar(renderer);
	}

	public void drawAfter(IRenderer renderer) {

	}

	public void drawEnergyBar(IRenderer renderer) {
		float pad = 0.05f;
		float height = 0.1f;
		Position pos = getPosition();
		float x = pos.x;
		float y = pos.y;
		float start = 0.5f;

		float x0 = x + pad - 0.5f;
		float y0 = y - start;
		float y1 = y - start + height;

		float x1 = x - pad + 0.5f;

		float nx1 = x0 + (x1 - x0) * (energy / orignalEnergy);

		renderer.drawRect(x0, y0, x1, y1, BORDER_COLOR);
		renderer.fillRect(x0, y0, nx1, y1, FILL_COLOR);
	}

	@Override
	public void setPosition(Position p) {
		super.setPosition(p);
		PathPos pp = new PathPos(p);
		Position endPos = new Position(getLevel().getEnemyEndPosition().get(0),
				Position.LEVEL_REF);
		if (getLevel().getEnemyEndPosition().contains(pp) || p.x > endPos.x) {
			getLevel().enemyReachedEnd(this);
		}
	}

	public void wasHitBy(MovingShot shot) {
		getShakingLines().shake();
		Class<? extends MovingShot> type = shot.getClass();
		float impact = getImpact(type, shot.getLevel());
		// FIXME: randomize impact
		energy -= impact;
		if (energy <= 0.0f) {
			getLevel().enemyDestroyed(this);
		}
	}

	public void freeze(float time) {
		if (frozenTime < time)
			frozenTime = time;
	}

	@Override
	public void move(float time) {
		shaking.move(time);
		if (frozenTime > 0.0f) {
			setSpeed(getOriginalSpeed() * FREEZE_FACTOR);
			frozenTime -= time;
		} else
			setSpeed(getOriginalSpeed());
	}

	public final float getSpeed() {
		if (speed < 0)
			speed = getOriginalSpeed();
		return speed;
	}

	public final void setSpeed(float s) {
		speed = s;
	}

	public abstract float getOriginalSpeed();

	public abstract Vector3 getMovingDirection();

	public float getImpact(Class<? extends MovingShot> shotType, float shotLevel) {
		// FIXME: different impact depending on shottype
		return shotLevel;
	}

}
