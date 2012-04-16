package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class Tank extends GroundMovingEnemy {
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
	private static final Color innerColor = new Color(0.7f, 0, 0.6f, 1.0f);
	private static final Color outerColor = new Color(0.7f, 0.2f, 1.0f, 1.0f);
	private final Chain chains = new Chain();

	public Tank(Position pos) {
		super(pos);
		setSize(0.2f);

	}

	@Override
	public void move(float time) {
		super.move(time);
		chains.move(time);

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, getAngle(), innerColor,
				getSize());
		renderer.drawLines(getPosition(), lines, getAngle(), outerColor,
				getSize());

		chains.drawChain(renderer, getPosition(), getAngle(), outerColor,
				getSize());
		super.draw(renderer);
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
