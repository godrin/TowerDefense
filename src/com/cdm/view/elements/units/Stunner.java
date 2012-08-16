package com.cdm.view.elements.units;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.shots.DisplayEffect;
import com.cdm.view.elements.shots.StunRay;
import com.cdm.view.enemy.EnemyUnit;

public class Stunner extends RotatingUnit implements Element {

	private static List<Vector3> lines = null;
	private static List<Vector3> poly = null;
	float coolingTime = 1.0f;
	float shotLength = 1.5f;
	float lastShot = 0.0f;
	float maxDist = 3.0f;
	DisplayEffect currentShot = null;
	Color innerColor = new Color(0, 0, 0.6f, 1.0f);
	Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
	private static PolySprite shadow;

	public Stunner(Position p) {
		super(p);

		if (lines == null) {
			float r = 0.9f;
			float ir = 0.8f;
			Vector3 q0 = new Vector3(-r, -r, 0);
			Vector3 q1 = new Vector3(r, -r, 0);
			Vector3 q2 = new Vector3(r, r, 0);
			Vector3 q3 = new Vector3(-r, r, 0);

			int l = 20;
			Vector3 circle[] = new Vector3[l];
			for (int i = 0; i < l; i++) {
				float phase = 3.1415f * 2 * i / l;
				circle[i] = new Vector3((float) Math.sin(phase) * ir,
						(float) Math.cos(phase) * ir, 0);
			}

			lines = new ArrayList<Vector3>();

			lines.add(q0);
			lines.add(q1);
			lines.add(q1);
			lines.add(q2);
			lines.add(q2);
			lines.add(q3);
			lines.add(q3);
			lines.add(q0);
			poly = new ArrayList<Vector3>();
			for (int index = 0; index < l; index++) {
				lines.add(circle[index]);
				lines.add(circle[(index + 1) % l]);
				poly.add(circle[index]);
				poly.add(circle[(index + 1) % l]);
				poly.add(new Vector3(0, 0, 0));
			}

			shadow = new PolySprite();
			shadow.drawClosedPolyWithBorder(new Vector3[] { q3, q2, q1, q0 },
					innerColor, new Color(0, 0, 0, 0), 0.6f);
			shadow.init();
		}
		setTurningSpeed(15.0f);
	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);
		renderer.render(shadow, getPosition(), getSize(), getAngle(),
				GL10.GL_TRIANGLES);
		renderer.drawPoly(getPosition(), poly, getAngle(), innerColor,
				getSize());
		renderer.drawLines(getPosition(), lines, getAngle(), outerColor,
				getSize());
	}

	void shoot(EnemyUnit enemy) {
		if (enemy != null) {

			if (lastShot > coolingTime + shotLength) {
				lastShot = 0.0f;
				Position startingPos = new Position(getPosition());

				getLevel().addShot(
						currentShot = new StunRay(shotLength, startingPos,
								getLevel(), enemy));
				SoundFX.play(Type.STUNRAY);

			}

		}
	}

	@Override
	protected EnemyUnit getEnemy() {
		EnemyUnit u = getLevel().getNextEnemy(getPosition());
		if (u == null)
			return null;
		if (getPosition().distance(u.getPosition()) > maxDist)
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

	@Override
	public int getZLayer() {
		return 0;
	}

	@Override
	protected void setValue(String key, Float value) {
		if ("distance".equals(key))
			maxDist = value;
		else if ("shot".equals(key))
			shotLength = value;
		else if ("cooling".equals(key))
			coolingTime = value;
		else if ("rotation".equals(key))
			setTurningSpeed(value);
	}

}
