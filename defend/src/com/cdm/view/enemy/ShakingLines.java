package com.cdm.view.enemy;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class ShakingLines {

	private static final double FACTOR = 0.1f;
	private static final float SPEED = 3.1415f * 4;
	private float shakeTime = -1;
	private Position tmp = new Position(0, 0, Position.LEVEL_REF);
	private float shakeAngle;

	public void shake() {
		shakeTime = 1.1f;
		shakeAngle = (float) Math.random() * 3.1415f;
	}

	public void move(float t) {
		shakeTime -= t;
	}

	public void draw(IRenderer renderer, Position position,
			List<Vector3> lines, float angle, Color outercolor, float size) {
		if (shakeTime > 0) {
			tmp.set(position);

			float dx = (float) (Math.sin(shakeAngle)
					* Math.sin(shakeTime * SPEED) * shakeTime * FACTOR);
			float dy = (float) (Math.cos(shakeAngle)
					* Math.sin(shakeTime * SPEED) * shakeTime * FACTOR);
			tmp.x -= dx;
			tmp.y -= dy;
			renderer.drawLines(tmp, lines, angle, outercolor, size);
			tmp.set(position);

			tmp.x += dx;
			tmp.y += dy;
			renderer.drawLines(tmp, lines, angle, outercolor, size);
		} else {
			renderer.drawLines(position, lines, angle, outercolor, size);
		}
	}
}
