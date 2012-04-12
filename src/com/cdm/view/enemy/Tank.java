package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.MathTools;
import com.cdm.view.elements.RotatingThing;
import com.cdm.view.elements.shots.MovingShot;

public class Tank extends EnemyUnit {

	public Position nextStep = null;
	public static final float SPEED = 0.2f;

	private static final Vector3 c0 = new Vector3(-1, -1, 0);
	private static final Vector3 c1 = new Vector3(1, -1, 0);
	private static final Vector3 c2 = new Vector3(1, 1, 0);
	private static final Vector3 c3 = new Vector3(-1, 1, 0);
	private static final Vector3 d0 = new Vector3(-0.5f, -0.5f, 0);
	private static final Vector3 d1 = new Vector3(-0.5f, 0.5f, 0);
	private static final Vector3 d2 = new Vector3(0.5f, 0, 0);
	private static final Vector3 k0 = new Vector3(-0.9f, -1.5f, 0);
	private static final Vector3 k1 = new Vector3(0.8f, -1.5f, 0);
	private static final Vector3 k2 = new Vector3(0.8f, -1.1f, 0);
	private static final Vector3 k3 = new Vector3(-0.9f, -1.1f, 0);
	private static final Vector3 x0 = new Vector3(-0.9f, 1.5f, 0);
	private static final Vector3 x1 = new Vector3(0.8f, 1.5f, 0);
	private static final Vector3 x2 = new Vector3(0.8f, 1.1f, 0);
	private static final Vector3 x3 = new Vector3(-0.9f, 1.1f, 0);

	private static final List<Vector3> lines = Arrays.asList(new Vector3[] {
			c0, c1, c1, c2, c2, c3, c3, c0, d0, d1, d1, d2, d2, d0, k0, k1, k1,
			k2, k2, k3, k3, k0, x0, x1, x1, x2, x2, x3, x3, x0 });

	private static final List<Vector3> poly = Arrays.asList(new Vector3[] { c0,
			c1, c2, c0, c2, c3 });

	private static final List<Vector3> chainLines = Arrays
			.asList(new Vector3[] { new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0),

					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0), });
	private float chainPhase = 0.0f;

	private static final Color innerColor = new Color(0.7f, 0, 0.6f, 1.0f);
	private static final Color outerColor = new Color(0.7f, 0.2f, 1.0f, 1.0f);
	private static final Vector3 DEFAULT_DIRECTION = new Vector3(1, 0, 0);

	private Vector3 diff = new Vector3();
	private Vector3 movingDir = new Vector3();
	private RotatingThing rotation = new RotatingThing();

	public Tank(Position pos) {
		super(pos);
		setSize(0.2f);

	}

	@Override
	public void move(float time) {
		super.move(time);
		chainPhase += time;

		while (time > 0) {
			if (nextStep == null) {
				nextStep = getLevel().getNextPos(getPosition().alignedToGrid());
			}
			Position nuPos = new Position(getPosition());

			diff.set(getPosition().to(nextStep));

			float targetAngle = MathTools.angle(diff);
			rotation.setTargetAngle(targetAngle);

			time -= rotation.move(time);

			if (time < 0.00001f)
				return;

			float len = diff.len();
			float delta = time * getSpeed();

			if (delta >= len) {
				setPosition(nextStep);
				time -= len / delta;
				nextStep = null;
			} else {
				diff.mul(delta / diff.len());
				nuPos.x += diff.x;
				nuPos.y += diff.y;
				setPosition(nuPos);
				time = 0;
			}

		}
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, getAngle(), innerColor,
				getSize());
		renderer.drawLines(getPosition(), lines, getAngle(), outerColor,
				getSize());

		drawChain(renderer);

		super.draw(renderer);
	}

	private void drawChain(IRenderer renderer) {
		float x;
		float startX = -0.9f;
		float delta = 0.7f + 0.9f;
		int size = 4;
		float speed = 0.5f;
		for (int i = 0; i < size; i++) {
			x = ((float) i) / size * 3.1415f * 0.5f;
			x += chainPhase * speed + 3.1415;
			x %= 3.1415 * 0.5;
			x = (float) Math.sin(x);
			x *= delta;
			x += startX;
			for (int lr = 0; lr < size * 4; lr += size * 2) {
				Vector3 a = chainLines.get(lr + i * 2);
				Vector3 b = chainLines.get(lr + i * 2 + 1);
				a.x = x;
				b.x = x;
			}
		}
		renderer.drawLines(getPosition(), chainLines, getAngle(), outerColor,
				getSize());

	}

	private float getAngle() {
		return rotation.getCurrentAngle();
	}

	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

	@Override
	public Vector3 getMovingDirection() {
		if (nextStep != null)
			return movingDir.set(getPosition().to(nextStep).nor());
		return DEFAULT_DIRECTION;
	}

	@Override
	public int getMoney() {
		return 2;
	}

	@Override
	public int getPoints() {
		return 5;
	}

	@Override
	public int getBonus() {
		return 1;
	}

	@Override
	public int getZLayer() {
		return 0;
	}

}
