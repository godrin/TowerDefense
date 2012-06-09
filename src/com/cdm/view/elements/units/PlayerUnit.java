package com.cdm.view.elements.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;

public abstract class PlayerUnit extends Unit {

	private PolySprite circle;
	private boolean selected = false;

	public PlayerUnit(Position p) {
		super(p);

		circle = new PolySprite();
		circle.fillCircle(0, 0, 4 * 2, new Color(0, 0, 1, 0.3f), new Color(0,
				0, 0, 0), 32);
		circle.init();
	}

	public void draw(IRenderer renderer) {
		if (selected)
			renderer.render(circle, getPosition(), getSize(), 0.0f,
					GL10.GL_TRIANGLES);
	}

	public void selected(boolean b) {
		selected = b;

	}
}
