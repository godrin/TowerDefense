/*package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Level;

public class Rocket extends AbstractShot {

	public static float speed = 0.7f;
	private List<Vector3> lines;
	private List<Vector3> poly;

	public Rocket(Position from, Position to, Level plevel) {
		super(from, to, plevel);
		Vector3 a = new Vector3(-1.5f, 0.25f, 0);
		Vector3 b = new Vector3(-0.75f, 0.75f, 0);
		Vector3 c = new Vector3(0.75f, 0.75f, 0);
		Vector3 d = new Vector3(0.05f, 0.25f, 0);
		Vector3 d2 = new Vector3(0.05f, -0.25f, 0);
		Vector3 e = new Vector3(0.75f, -0.75f, 0);
		Vector3 f = new Vector3(-0.75f, -0.75f, 0);
		Vector3 g = new Vector3(-1.5f, -0.25f, 0);
		Vector3 h = new Vector3(-0.85f, -0.25f, 0);
		Vector3 i = new Vector3(-0.85f, 0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2, e,
				e, f, f, g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d, h,
				d2, g, f, d2, f, d2, e });
		// setSize(1.0f / 1.5f);
	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0, 0, 0, 0);
		renderer.drawPoly(getPosition(), poly, 180 + angle, innerColor,
				getSize(), RefSystem.Level);
		Color outerColor = new Color(0, 0, 1, 1.0f);
		renderer.drawLines(getPosition(), lines, 180 + angle, outerColor,
				getSize(), RefSystem.Level);
	}

	@Override
	public float getSpeed() {
		return speed;
	}

}


package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Level;

public class Rocket extends AbstractShot {

	public static float speed = 0.7f;
	private List<Vector3> lines;
	private List<Vector3> poly;

	public Rocket(Position from, Position to, Level plevel) {
		super(from, to, plevel);
		Vector3 a = new Vector3(-1.5f, 0.25f, 0);
		Vector3 b = new Vector3(-0.75f, 0.75f, 0);
		Vector3 c = new Vector3(0.75f, 0.75f, 0);
		Vector3 d = new Vector3(0.05f, 0.25f, 0);
		Vector3 d2 = new Vector3(0.05f, -0.25f, 0);
		Vector3 e = new Vector3(0.75f, -0.75f, 0);
		Vector3 f = new Vector3(-0.75f, -0.75f, 0);
		Vector3 g = new Vector3(-1.5f, -0.25f, 0);
		Vector3 h = new Vector3(-0.85f, -0.25f, 0);
		Vector3 i = new Vector3(-0.85f, 0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2, e,
				e, f, f, g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d, h,
				d2, g, f, d2, f, d2, e });
		// setSize(1.0f / 1.5f);
	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0, 0, 0, 0);
		renderer.drawPoly(getPosition(), poly, 180 + angle, innerColor,
				getSize(), RefSystem.Level);
		Color outerColor = new Color(0, 0, 1, 1.0f);
		renderer.drawLines(getPosition(), lines, 180 + angle, outerColor,
				getSize(), RefSystem.Level);
	}

	@Override
	public float getSpeed() {
		return speed;
	}

}


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

public class oldRocket2 extends AbstractShot {

	public static float speed = 0.7f;
	private List<Vector3> lines;
	private List<Vector3> poly;

	public Rocket(Position from, Position to, Level plevel) {
		super(from, to, plevel);
	
	
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
			level.removeShot2(this);
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
*/