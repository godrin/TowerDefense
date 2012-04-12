package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.shots.MovingShot;

public class Rocket extends EnemyUnit implements Element {

	public Position nextStep = null;
	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);
	boolean up = true;
	public static final Vector3 a = new Vector3(-1.25f, 0.25f, 0);
	public static final Vector3 b = new Vector3(-0.5f, 0.75f, 0);
	public static final Vector3 c = new Vector3(1.0f, 0.85f, 0);
	public static final Vector3 d = new Vector3(0.3f, 0.25f, 0);
	public static final Vector3 d2 = new Vector3(0.3f, -0.25f, 0);
	public static final Vector3 e = new Vector3(1.0f, -0.85f, 0);
	public static final Vector3 f = new Vector3(-0.5f, -0.75f, 0);
	public static final Vector3 g = new Vector3(-1.25f, -0.25f, 0);
	public static final Vector3 h = new Vector3(-0.6f, -0.25f, 0);
	public static final Vector3 i = new Vector3(-0.6f, 0.25f, 0);
	public static final Vector3 j = new Vector3(-0.6f, 0, 0);
	public static final Vector3 k = new Vector3(-1.35f, 0, 0);
	public static final Vector3 l = new Vector3(0.65f, 0.5f, 0);
	public static final Vector3 m = new Vector3(1.0f, 0.5f, 0);
	public static final Vector3 n = new Vector3(0.65f, -0.5f, 0);
	public static final Vector3 o = new Vector3(1.0f, -0.5f, 0);

	private static List<Vector3> lines = Arrays.asList(new Vector3[] { a, b, b,
			c, c, d, d, d2, d2, e, e, f, f, g, g, h, h, i, i, a, j, k, l, m, n,
			o });
	private static List<Vector3> poly = Arrays.asList(new Vector3[] { i, b, c,
			a, i, b, i, c, d, h, g, f, f, h, e, h, d2, e, d2, d, h, h, i, d });
	// setSize(1.0f / 1.5f);
	private static final float SPEED = 0.5f;
	private static final Color outerColor = new Color(0, 0, 1, 1.0f);
	private static final Color innerColor = new Color(0, 0, 0.5f, 1f);
	float angle = 180;

	public Rocket(Position position) {
		super(position);

		setSize(0.5f);

	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);

		renderer.drawPoly(getPosition(), poly, angle, innerColor, getSize());
		renderer.drawLines(getPosition(), lines, angle, outerColor, getSize());
		if (up) {
			if (innerColor.b >= 0) {
				innerColor.b -= 0.0025f;
				innerColor.r += 0.0025f;
			} else {
				up = false;
			}
		} else {
			if (innerColor.r >= 0) {
				innerColor.b += 0.0025f;
				innerColor.r -= 0.0025f;
			} else {
				up = true;
			}
		}
	}

	@Override
	public void move(float time) {
		super.move(time);
		Position p = getPosition();
		p.x += time * getSpeed();
		setPosition(p); // react to position change
	}

	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

	@Override
	public Vector3 getMovingDirection() {
		return DIRECTION;
	}

	@Override
	public float getImpact(Class<? extends MovingShot> shotType, float shotLevel) {
		// FIXME: different impact depending on shottype
		return 0.3f;
	}

	@Override
	public int getMoney() {
		return 5;
	}

	@Override
	public int getPoints() {
		return 10;
	}

	@Override
	public int getBonus() {
		return 1;
	}

	@Override
	public int getZLayer() {
		return 3;
	}

}
