package com.cdm.view.enemy.types;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.enemy.EnemyUnit;

public class SmallShip extends EnemyUnit implements Element {

	public Position nextStep = null;
	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);

	private static final Vector3 a = new Vector3(-0.75f, 0.4f, 0);
	private static final Vector3 b = new Vector3(0.75f, 0.0f, 0);
	private static final Vector3 c = new Vector3(-0.75f, -0.4f, 0);
	private static final Vector3 d = new Vector3(-0.25f, 0, 0);

	private static List<Vector3> lines = Arrays.asList(new Vector3[] { a, b, b,
			c, c, d, d, a, });
	private static List<Vector3> poly = Arrays.asList(new Vector3[] { a, b, d,
			b, c, d });

	private static List<Vector3> ray = Arrays.asList(new Vector3[] {
			new Vector3(), new Vector3(), new Vector3(), new Vector3(),
			new Vector3(), new Vector3(), new Vector3(), new Vector3() });
	private float rayPhase = 0.0f;

	float angle = 0.0f;

	private static final float SPEED = 0.3f;
	private static final Color lineColor = new Color(0.9f, 0, 0, 1.0f);

	private static final float RAY_START = 0.5f;
	private static final float RAY_DISTANCE = 0.5f;
	private static final float RAY_LENGTH = RAY_DISTANCE * 4;
	private float raySpeed;
	private static final Color BG_COLOR = new Color(0, 0, 0, 0.6f);

	public SmallShip(Position position) {
		super(position);
		setRayspeed(1.1f);

		setSize(0.5f);

	}

	public void setRayspeed(float s) {
		raySpeed = s;
	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);

		renderer.drawPoly(getPosition(), poly, angle, BG_COLOR, getSize() * 1f);
		getShakingLines().draw(renderer, getPosition(), lines, angle,
				lineColor, getSize());
		getShakingLines().draw(renderer, getPosition(), ray, angle, lineColor,
				getSize());
	}

	@Override
	public void move(float time) {
		super.move(time);
		Position p = getPosition();
		p.x += time * getSpeed();
		setPosition(p); // react to position change

		rayPhase += raySpeed * time;

		for (int rayI = 0; rayI < ray.size() / 2; rayI++) {
			Vector3 a = ray.get(rayI * 2);
			Vector3 b = ray.get(rayI * 2 + 1);

			float ph = rayI * RAY_DISTANCE + rayPhase;

			ph %= RAY_LENGTH;
			float size = (1 - ph / RAY_LENGTH) * 0.15f;
			a.set(-ph - RAY_START, -size, 0);
			b.set(-ph - RAY_START, size, 0);
		}
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
	public int getMoney() {
		return 3;
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
		return 2;
	}
}
