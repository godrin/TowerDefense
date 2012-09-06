package com.cdm.view.elements.shots;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class Shake implements DisplayEffect {

	private static final float SPEED = 12;
	private static final float MAXTIME = 12;

	private float phase = 0;
	private Level level;

	public Shake(Level l) {
		level = l;
	}

	@Override
	public void move(float time) {
		float radius = (MAXTIME - phase) / 10;
		float v0 = (float) Math.sin(phase) * radius;
		phase += time * SPEED;
		if (phase >= MAXTIME) {
			level.removeShot(this);
		}
		float v1 = (float) Math.sin(phase) * radius;

		Position.LEVEL_REF.setScale(Position.LEVEL_REF.getScale() - v0 + v1);
	}

	@Override
	public void draw(IRenderer renderer) {

	}

}
