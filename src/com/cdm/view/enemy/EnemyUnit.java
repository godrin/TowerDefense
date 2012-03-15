package com.cdm.view.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Unit;

public abstract class EnemyUnit extends Unit {

	public EnemyUnit(Position pos) {
		super(pos);
	}

	@Override
	public void draw(IRenderer renderer) {
		float pad = 0.05f;
		float height = 0.2f;
		Position pos = getPosition();
		float x = pos.x;
		float y = pos.y;
		
		float x0=x+pad-0.5f;
		float y0=y-0.9f;
		float y1=y-0.9f+height;
		float x1=x-pad+0.5f;

		renderer.drawRect(x0,y0,x1,y1, Settings.CELL_WIDTH);
		Color c = new Color(1, 0, 0, 1);
		renderer.fillRect(x0,y0,x1,y1, c, Settings.CELL_WIDTH);
	}

	@Override
	public void setPosition(Position p) {
		super.setPosition(p);
		if (p.equals(getLevel().getEnemyEndPosition())) {
			getLevel().enemyReachedEnd(this);
		}
	}

	public abstract float getSpeed();

	public abstract Vector3 getMovingDirection();

}
