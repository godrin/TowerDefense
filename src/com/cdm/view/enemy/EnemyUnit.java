package com.cdm.view.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Unit;
import com.cdm.view.elements.shots.AbstractShot;

public abstract class EnemyUnit extends Unit {

	private float energy;

	public EnemyUnit(Position pos) {
		super(pos);
		energy = 1.0f;
	}

	@Override
	public void draw(IRenderer renderer) {
		float pad = 0.05f;
		float height = 0.1f;
		Position pos = getPosition();
		float x = pos.x;
		float y = pos.y;
		float start=0.5f;

		float x0 = x + pad - 0.5f;
		float y0 = y - start;
		float y1 = y - start + height;

		float x1 = x - pad + 0.5f;

		float nx1 = x0 + (x1 - x0) * energy;

		Color c0 = new Color(1, 0, 0, 0.8f);
		Color c1 = new Color(1, 0, 0, 0.7f);
		renderer.drawRect(x0, y0, x1, y1, c0,RefSystem.Level);
		renderer.fillRect(x0, y0, nx1, y1, c1, RefSystem.Level);
	}

	@Override
	public void setPosition(Position p) {
		super.setPosition(p);
		if (p.equals(getLevel().getEnemyEndPosition())) {
			getLevel().enemyReachedEnd(this);
		}
	}

	public void wasHitBy(AbstractShot shot) {
		Class<? extends AbstractShot> type = shot.getClass();
		float impact = getImpact(type, shot.getLevel());
		// FIXME: randomize impact
		energy -= impact;
		if (energy <= 0.0f) {
			getLevel().enemyDestroyed(this);
		}
	}

	public abstract float getSpeed();

	public abstract Vector3 getMovingDirection();

	public abstract float getImpact(Class<? extends AbstractShot> shotType,
			float shotLevel);

}
