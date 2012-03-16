package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class AbstractShot implements Element {

	Position pos;
	public static final float speed = 1.5f;
	Position target;
	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle;
	private Level level;

	public AbstractShot(Position from, Position to, Level plevel) {
		pos = new Position(from);
		target = new Position(to);
		level = plevel;

		angle = MathTools.angle(from.to(to));

		Vector3 a = new Vector3(-0.75f, 0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0.0f, 0);
		Vector3 c = new Vector3(-0.75f, -0.4f, 0);
		// Vector3 d = new Vector3(-0.25f, 0, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, a });
		poly = Arrays.asList(new Vector3[] { a, b, c });

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, angle, new Color(0.5f, 0, 0,
				1.0f), getSize());
		renderer.drawLines(getPosition(), lines, angle, new Color(0.9f, 0, 0,
				1.0f), getSize());

	}

	private float getSize() {
		return 0.3f;
	}

	private Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public void move(float time) {
		Vector3 deltaV = pos.to(target);
		float distance = time * speed;
		if (distance > deltaV.len()) {
			pos = target;
			SoundFX.hit.play();
			// FIXME: hit target
			level.removeShot(this);
		} else {

			Vector3 nu = deltaV.nor().mul(distance);
			pos.x += nu.x;
			pos.y += nu.y;
		}

	}

}
