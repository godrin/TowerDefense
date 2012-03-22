package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.shots.AbstractShot;
import com.cdm.view.elements.shots.StunRay;
import com.cdm.view.enemy.EnemyUnit;

public class Stunner extends RotatingUnit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float shotFrequency = 2.5f;
	float lastShot = 0.0f;
	float maxDist = 3.0f;
	AbstractShot currentShot = null;
	Color innerColor = new Color(0, 0, 0.6f, 1.0f);
	Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);


	public Stunner(Position p) {
		super(p);
		Vector3 a = new Vector3(-0.95f, 0.25f, 0);
		Vector3 b = new Vector3(-0.25f, 0.75f, 0);
		Vector3 c = new Vector3(0.25f, 0.75f, 0);
		Vector3 d = new Vector3(0.85f, 0.25f, 0);
		Vector3 d2 = new Vector3(0.85f, -0.25f, 0);
		Vector3 e = new Vector3(0.25f, -0.75f, 0);
		Vector3 f = new Vector3(-0.25f, -0.75f, 0);
		Vector3 g = new Vector3(-0.95f, -0.25f, 0);
		Vector3 h = new Vector3(-0.5f, -0.25f, 0);
		Vector3 i = new Vector3(-0.5f, 0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2, e,
				e, f, f, g, g, h, h, i, i, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d, h,
				d2, g, f, d2, f, d2, e });
		setTurningSpeed(15.0f);
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, angle, innerColor, getSize(),
				RefSystem.Level);
		renderer.drawLines(getPosition(), lines, angle, outerColor, getSize(),
				RefSystem.Level);
	}

	void shoot(EnemyUnit enemy) {
		if (enemy != null) {

			if (lastShot > shotFrequency) {
				lastShot = 0.0f;
				Position startingPos = new Position(getPosition());

				getLevel().addShot(
						currentShot = new StunRay(1.5f, startingPos,
								getLevel(), enemy));
				SoundFX.shot2.play();

			}

		}
	}

	@Override
	protected EnemyUnit getEnemy() {
		EnemyUnit u = getLevel().getNextEnemy(getPosition());
		if (u == null)
			return null;
		if (getPosition().to(u.getPosition()).len() > maxDist)
			return null;
		return u;
	}

	@Override
	public void move(float time) {
		super.move(time);
		EnemyUnit enemy = getEnemy();
		lastShot += time;
		if (ableToShoot)
			shoot(enemy);
		else {
			if (currentShot != null)
				getLevel().removeShot(currentShot);
			currentShot = null;
		}
	}

	protected float getMaxDist() {
		return maxDist;
	}

}
