package com.cdm.view.elements;

import com.badlogic.gdx.graphics.Color;
import com.cdm.view.IRenderer;

public abstract class EnemyUnit extends Unit {

	public EnemyUnit(int px, int py) {
		super(px, py);
	}

	@Override
	public void draw(IRenderer renderer) {
		int pad = 2;
		int w = CELL_WIDTH, hw = w / 2;
		int height = 10;
		renderer.drawRect(x * w + pad - hw, y * w - w, x * w + w - pad - hw, y
				* w + height - w);
		Color c = new Color(1, 0, 0, 1);
		renderer.fillRect(x * w + pad + 1 - hw, y * w + 1 - w, x * w + w - pad
				- 1 - hw, y * w + height - 1 - w, c);
	}

}