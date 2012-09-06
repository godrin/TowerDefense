package com.cdm.view.enemy.types;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.MathTools;
import com.cdm.view.enemy.EnemyUnit;

public class SmallShip extends EnemyUnit implements Element {

	public Position nextStep = null;
	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);

	private static final Vector3 a = new Vector3(-0.75f, 0.4f, 0);
	private static final Vector3 b = new Vector3(0.75f, 0.0f, 0);
	private static final Vector3 c = new Vector3(-0.75f, -0.4f, 0);
	private static final Vector3 d = new Vector3(-0.25f, 0, 0);

	private static PolySprite bg = null;

	private static List<Vector3> lines = Arrays.asList(new Vector3[] { a, b, b,
			c, c, d, d, a, });
	private static List<Vector3> poly = Arrays.asList(new Vector3[] { a, b, d,
			b, c, d });

	private List<Vector3> ray = Arrays.asList(new Vector3[] { new Vector3(),
			new Vector3(), new Vector3(), new Vector3(), new Vector3(),
			new Vector3(), new Vector3(), new Vector3() });
	private float rayPhase = 0.0f;

	float angle = 0.0f;

	private static final float SPEED = 0.3f;
	private static final Color lineColor = new Color(0.9f, 0, 0, 1.0f);

	private static final float RAY_START = 0.5f;
	private static final float RAY_DISTANCE = 0.5f;
	private static final float RAY_LENGTH = RAY_DISTANCE * 4;
	private float raySpeed;
	private Position endPosition;
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

	// fly diagonal to end position
	@Override
	public void move(float time) {
		super.move(time);

		if (endPosition == null)
			endPosition = getLevel().getSomeEnemyEndPosition();

		Position p = getPosition();

		Vector3 delta = p.to(endPosition);
		delta.nor();

		angle = MathTools.angle(delta);
		float speed = getSpeed();
		p.x += delta.x * speed * time;
		p.y += delta.y * speed * time;
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

	public void drawInLayer(int zLayer, IRenderer renderer) {
		super.drawInLayer(zLayer, renderer);
		if (zLayer == 1) {
			if (bg == null) {
				bg = new PolySprite();

				Color c0 = new Color(0.7f, 0, 0, 0.8f);
				Color c1 = new Color(0, 0, 0, 0);

				bg.drawClosedPolyWithBorder(new Vector3[] { a, b, c, d }, c0,
						c1, 0.5f);
				bg.init();

			}
			renderer.render(bg, getPosition(), getSize(), angle,
					GL10.GL_TRIANGLES, new Color(0, 0, 0, 0));
		}
	}
}
