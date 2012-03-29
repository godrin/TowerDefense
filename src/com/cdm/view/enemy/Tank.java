package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
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
	private static final Vector3 k0 = new Vector3(-0.7f, -1.5f, 0);
	private static final Vector3 k1 = new Vector3(0.9f, -1.5f, 0);
	private static final Vector3 k2 = new Vector3(0.9f, -1.1f, 0);
	private static final Vector3 k3 = new Vector3(-0.7f, -1.1f, 0);
	private static final Vector3 x0 = new Vector3(-0.7f, 1.5f, 0);
	private static final Vector3 x1 = new Vector3(0.9f, 1.5f, 0);
	private static final Vector3 x2 = new Vector3(0.9f, 1.1f, 0);
	private static final Vector3 x3 = new Vector3(-0.7f, 1.1f, 0);

	private static final List<Vector3> lines = Arrays.asList(new Vector3[] {
			c0, c1, c1, c2, c2, c3, c3, c0, d0, d1, d1, d2, d2, d0, k0, k1, k1,
			k2, k2, k3, k3, k0, x0, x1, x1, x2, x2, x3, x3, x0 });

	private static final List<Vector3> poly = Arrays.asList(new Vector3[] { c0,
			c1, c2, c0, c2, c3 });

	private static final Color innerColor = new Color(0.7f, 0, 0.6f, 1.0f);
	private static final Color outerColor = new Color(0.7f, 0.2f, 1.0f, 1.0f);
	private static final Vector3 DEFAULT_DIRECTION = new Vector3(1, 0, 0);


	private Vector3 diff = new Vector3();
	private Vector3 movingDir = new Vector3();
	private RotatingThing rotation = new RotatingThing();


	public Tank(Position pos) {
		super(pos);
		setSize(0.4f);

	}

	@Override
	public void move(float time) {
		super.move(time);
		while (time > 0) {
			if (nextStep == null) {
				nextStep = getLevel().getNextPos(getPosition().alignedToGrid());
			}
			Position nuPos = new Position(getPosition());

			diff.set(getPosition().to(nextStep));

			float targetAngle = MathTools.angle(diff);

			rotation.setTargetAngle(targetAngle);

			time-=rotation.move(time);

			if (time<0.00001f)
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
				getSize(), RefSystem.Level);
		renderer.drawLines(getPosition(), lines, getAngle(), outerColor,
				getSize(), RefSystem.Level);
		super.draw(renderer);
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
	public float getImpact(Class<? extends MovingShot> shotType, float shotLevel) {
		return 0.1f;
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

}
