package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.MathTools;

/**
 * FIXME: Die Klasse sollte raus - Abstract shot ist eine abstrakte Basisklasse für alle Shot-Arten
 * @author david
 *
 */
@Deprecated
public class AbstractShot2 implements Element {

	Position pos;
	public static final float speed2 = 0.7f;
	Position target;
	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle;
	private Level level;

	public AbstractShot2(Position from, Position to, Level plevel) {
		pos = new Position(from);
		target = new Position(to);
		level = plevel;

		angle = MathTools.angle(from.to(to));

		Vector3 a = new Vector3(0, -0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0, 0);
		Vector3 c = new Vector3(0, 0.4f, 0);
		Vector3 d = new Vector3(0, 0.2f, 0);
		Vector3 e = new Vector3(-0.5f, 0.2f, 0);
		Vector3 f = new Vector3(-0.75f, 0.5f, 0);
		Vector3 g = new Vector3(-0.75f, -0.5f, 0);
		Vector3 h = new Vector3(-0.5f, -0.2f, 0);
		Vector3 i = new Vector3(0, -0.2f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, e, e, f, f,
				g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c });

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, angle, new Color(0.5f, 0, 0,
				1.0f), getSize(), RefSystem.Level);
		renderer.drawLines(getPosition(), lines, angle, new Color(0.9f, 0, 0,
				1.0f), getSize(), RefSystem.Level);

	}

	private float getSize() {
		return 0.5f;
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
		float distance = time * speed2;
		if (distance > deltaV.len()) {
			pos = target;
			// FIXME: hit target
			// DO NOT ANYMORE level.removeShot2(this);
			SoundFX.hit.play();
		} else {

			Vector3 nu = deltaV.nor().mul(distance);
			pos.x += nu.x;
			pos.y += nu.y;
		}

	}
	public float getLevel() {
		return 1;
	}

}
