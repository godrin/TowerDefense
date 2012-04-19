package com.cdm.view.enemy.types;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.enemy.Chain;
import com.cdm.view.enemy.GroundMovingEnemy;

public class Truck extends GroundMovingEnemy {

	public Position nextStep = null;
	public static final float SPEED = 0.33f;

	private static final Vector3 c0 = new Vector3(-1.5f, -1, 0);
	private static final Vector3 c1 = new Vector3(1, -1, 0);
	private static final Vector3 c2 = new Vector3(1, 1, 0);
	private static final Vector3 c3 = new Vector3(-1.5f, 1, 0);
	private static final Vector3 c01 = new Vector3(-1.25f, -0.75f, 0);
	private static final Vector3 c11 = new Vector3(0.75f, -0.75f, 0);
	private static final Vector3 c21 = new Vector3(0.75f, 0.75f, 0);
	private static final Vector3 c31 = new Vector3(-1.25f, 0.75f, 0);
	private static final Vector3 d0 = new Vector3(1f, -0.8f, 0);
	private static final Vector3 d1 = new Vector3(2f, -0.8f, 0);
	private static final Vector3 d2 = new Vector3(2f, 0.81f, 0);
	private static final Vector3 d3 = new Vector3(1f, 0.8f, 0);
	private static final Vector3 k0 = new Vector3(-1f, -1.5f, 0);
	private static final Vector3 k1 = new Vector3(-0.3f, -1.5f, 0);
	private static final Vector3 k2 = new Vector3(-0.3f, -1.1f, 0);
	private static final Vector3 k3 = new Vector3(-1f, -1.1f, 0);
	private static final Vector3 x0 = new Vector3(-1f, 1.5f, 0);
	private static final Vector3 x1 = new Vector3(-0.3f, 1.5f, 0);
	private static final Vector3 x2 = new Vector3(-0.3f, 1.1f, 0);
	private static final Vector3 x3 = new Vector3(-1f, 1.1f, 0);
	private static final Vector3 xx0 = new Vector3(1.7f, 1.5f, 0);
	private static final Vector3 xx1 = new Vector3(1f, 1.5f, 0);
	private static final Vector3 xx2 = new Vector3(1f, 1.1f, 0);
	private static final Vector3 xx3 = new Vector3(1.7f, 1.1f, 0);
	private static final Vector3 xy0 = new Vector3(1.7f, -1.5f, 0);
	private static final Vector3 xy1 = new Vector3(1f, -1.5f, 0);
	private static final Vector3 xy2 = new Vector3(1f, -1.1f, 0);
	private static final Vector3 xy3 = new Vector3(1.7f, -1.1f, 0);

	private static final List<Vector3> lines = Arrays.asList(new Vector3[] {
			c0, c1, c1, c2, c2, c3, c3, c0, c01, c11, c11, c21, c21, c31, c31,
			c01, d0, d1, d1, d2, d2, d3, d3, d0, k0, k1, k1, k2, k2, k3, k3,
			k0, x0, x1, x1, x2, x2, x3, x3, x0, xx0, xx1, xx1, xx2, xx2, xx3,
			xx3, xx0, xy0, xy1, xy1, xy2, xy2, xy3, xy3, xy0 });

	private static final List<Vector3> poly = Arrays.asList(new Vector3[] { c0,
			c1, c2, c0, c2, c3, d0, d1, d2, d2, d3, d0 });

	private static final Color innerColor = new Color(0.3f, 0.2f, 0.0f, 1.0f);
	private static final Color outerColor = new Color(0.8f, 0.7f, 0f, 1.0f);

	private final Chain chains = new Chain();

	public Truck(Position pos) {
		super(pos);
		setSize(0.25f);
		setTurningSpeed2(90);
	}

	@Override
	public void move(float time) {
		super.move(time);
		chains.move(time * SPEED / GroundMovingEnemy.SPEED);

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, getAngle(), innerColor,
				getSize());
		
		getShakingLines().draw(renderer,getPosition(), lines, getAngle(), outerColor,
				getSize());
		/*
		renderer.drawLines(getPosition(), lines, getAngle(), outerColor,
				getSize());
*/
		chains.drawChain(renderer, getPosition(), getAngle(), outerColor,
				getSize(), -0.9f, 0.55f);
		chains.drawChain(renderer, getPosition(), getAngle(), outerColor,
				getSize(), 1f, 0.55f);

		super.draw(renderer);
	}

	@Override
	public int getMoney() {
		return 1;
	}

	@Override
	public int getPoints() {
		return 1;
	}

	@Override
	public int getBonus() {
		return 0;
	}

	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

}
