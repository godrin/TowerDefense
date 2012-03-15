package com.cdm.view.elements;

import com.badlogic.gdx.graphics.Color;
import com.cdm.Settings;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public abstract class EnemyUnit extends Unit {

	public EnemyUnit(Position pos) {
		super(pos);
	}

	@Override
	public void draw(IRenderer renderer) {
		int pad = 2;
		int w = Settings.CELL_WIDTH, hw = w / 2;
		int height = 10;
		Position pos = getPosition();
		float x = pos.getX();
		float y = pos.getY();

		renderer.drawRect(x * w + pad - hw, y * w - w, x * w + w - pad - hw, y
				* w + height - w);
		Color c = new Color(1, 0, 0, 1);
		renderer.fillRect(x * w + pad + 1 - hw, y * w + 1 - w, x * w + w - pad
				- 1 - hw, y * w + height - 1 - w, c);
	}

	@Override
	public void setPosition(Position p) {
		super.setPosition(p);
		if (p.equals(getLevel().getEnemyEndPosition())) {
			getLevel().enemyReachedEnd(this);
		}
	}

}
