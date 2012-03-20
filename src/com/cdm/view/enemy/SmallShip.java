package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.shots.AbstractShot;

public class SmallShip extends EnemyUnit implements Element {

	public Position nextStep = null;
	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);
	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;

	private float speed = 0.3f;

	public SmallShip(Position position) {
		super(position);
		Vector3 a = new Vector3(-0.75f, 0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0.0f, 0);
		Vector3 c = new Vector3(-0.75f, -0.4f, 0);
		Vector3 d = new Vector3(-0.25f, 0, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, a, });
		poly = Arrays.asList(new Vector3[] { a, b, d, b, c, d });
		setSize(0.5f);

	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);

		renderer.drawPoly(getPosition(), poly, angle, new Color(0, 0, 0, 0),
				getSize(), RefSystem.Level);
		renderer.drawLines(getPosition(), lines, angle, new Color(0.9f, 0, 0,
				1.0f), getSize(), RefSystem.Level);

	}

	@Override
	public void move(float time) {
		while (time > 0) {
			if (nextStep == null) {
				nextStep = getLevel().getNextPos(getPosition().alignToGrid());
			}
			Position nuPos = new Position(getPosition());

			Vector3 diff = getPosition().to(nextStep);
			float len = diff.len();
			float delta = time * speed;

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
	public float getOriginalSpeed() {
		return speed;
	}

	@Override
	public Vector3 getMovingDirection() {
		return DIRECTION;
	}

	@Override
	public float getImpact(Class<? extends AbstractShot> shotType,
			float shotLevel) {
		return shotLevel / 3.0f;
	}

	@Override
	public int getMoney() {
		return 3;
	}

	@Override
	public int getPoints() {
		return 10;
	}

	@Override
	public int getBonus() {
		return 1;
	}
}
