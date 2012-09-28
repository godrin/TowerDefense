package com.cdm.view.elements.shots;

import com.badlogic.gdx.Gdx;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class ZoomInEffect implements DisplayEffect {

	private static final int DEFAULT_SCALE = 64;
	private static final float SPEED = 3;
	private float scale = 1;
	private Level level;

	public ZoomInEffect(Level l) {
		level = l;
	}

	@Override
	public void move(float time) {
		scale += time * scale * SPEED;
		if (scale > DEFAULT_SCALE) {
			scale = DEFAULT_SCALE;

			level.removeShot(this);
		}
		Position.LEVEL_REF.setScale(scale);
		Position.LEVEL_REF.setTranslate(-4.55f + Gdx.graphics.getWidth()
				/ scale / 3.0f, -Position.LEVEL_REF.getH() / 2 + 1.5f
				+ Gdx.graphics.getHeight() / scale / 3.0f);
	}

	@Override
	public void draw(IRenderer renderer) {

	}

	@Override
	public boolean onScreen() {
		return true;
	}

}
