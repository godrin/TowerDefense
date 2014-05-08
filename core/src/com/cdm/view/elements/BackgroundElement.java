package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Grid.GridElement;

// review1

public class BackgroundElement implements Element {

	private List<Vector3> vecsOuter = new ArrayList<Vector3>();
	private List<Vector3> vecsInner = new ArrayList<Vector3>();

	private List<Vector3> boxes = new ArrayList<Vector3>();
	private List<Vector3> boxes0 = new ArrayList<Vector3>();

	private List<Vector3> lines = Arrays.asList(new Vector3[] {
			new Vector3(-0.7f, -0.7f, 0), new Vector3(0.7f, -0.7f, 0) });

	private float size = .5f;
	private Position pos;
	private final Color c0 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private final Color c1 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private final Color c2 = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	private static final float OUTER = 1.0f;
	private static final float INNER = 0.8f;
	private static final float ROT_WIDTH = 0.2f;
	private static final float LIGHTING_SPEED = 0.6f;

	private boolean rotating = true;
	private float rotateAngle = 0;
	private float lightness = 0.0f;
	private float targetLightness = 0.0f;
	private GridElement gridElement;
	private float rotatingSpeed = 0.8f;
	private static PolySprite sprite = null;

	public BackgroundElement(Position p, GridElement e) {
		pos = p;
		rotatingSpeed = ((p.x + p.y) * 0.3f + 5) * 0.1f;
		gridElement = e;

		vecsOuter.add(new Vector3());
		vecsOuter.add(new Vector3());
		vecsOuter.add(new Vector3());
		vecsOuter.add(new Vector3());
		vecsInner.add(new Vector3());
		vecsInner.add(new Vector3());
		vecsInner.add(new Vector3());
		vecsInner.add(new Vector3());

		boxes.add(vecsOuter.get(0));
		boxes.add(vecsOuter.get(1));
		boxes.add(vecsOuter.get(3));
		boxes.add(vecsOuter.get(1));
		boxes.add(vecsOuter.get(2));
		boxes.add(vecsOuter.get(3));

		boxes0.add(vecsInner.get(0));
		boxes0.add(vecsInner.get(1));
		boxes0.add(vecsInner.get(3));
		boxes0.add(vecsInner.get(1));
		boxes0.add(vecsInner.get(2));
		boxes0.add(vecsInner.get(3));

		setVecs();

		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillRectangle(-OUTER, -OUTER, OUTER * 2, OUTER * 2, c0);
			sprite.fillRectangle(-INNER, -INNER, INNER * 2, INNER * 2, c1);
			sprite.fillRectangle(-0.7f, -0.7f, 1.4f, 0.1f, c0);
			sprite.makeNiceRectangle(0.15f, -INNER, -INNER, INNER * 2,
					INNER * 2, c0, c2);

			sprite.init();
		}

	}

	public void move(float t) {
		if (rotating) {
			rotateAngle += t * rotatingSpeed;
			if (rotateAngle > 3.1415f) {
				rotateAngle = 0;
				rotating = false;
			}
			if (Gdx.gl20 == null)
				setVecs();
		}
		if (Gdx.gl20 == null)
			setColor(t);
	}

	private void setColor(float t) {

		float v = (float) Math.sin(rotateAngle);

		if (gridElement != null) {
			if (gridElement.getList().size() > 0) {
				targetLightness = 1;

			} else if (gridElement.getDistToUnit() < 5) {
				targetLightness = 1 - gridElement.getDistToUnit() / 8.0f;
			} else {
				targetLightness = 0;

			}
			if (targetLightness != lightness) {
				if (targetLightness < lightness) {
					lightness -= t * LIGHTING_SPEED;
					if (targetLightness > lightness)
						lightness = targetLightness;
				} else {
					lightness += t * LIGHTING_SPEED;
					if (targetLightness < lightness)
						lightness = targetLightness;
				}

			}

		}
		float v2 = v;
		if (lightness > v2)
			v2 = lightness;

		float r = 0.1f + 0.4f * v;
		float g = 0.1f + 0.4f * v;
		float b = 0.2f + 0.8f * v;
		float r2 = 0.2f + 0.4f * v2;
		float g2 = 0.2f + 0.4f * v2;
		float b2 = 0.4f + 0.8f * v2;

		c0.set(r, g, b, 1.0f);
		c1.set(r2, g2, b2, 1.0f);
	}

	private void setVecs() {
		float o = OUTER;
		vecsOuter.get(0).set(-o + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(1).set(+o + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(2).set(+o - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(3).set(-o - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * o, 0);
		float i = INNER;
		vecsInner.get(0).set(-i + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(1).set(+i + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(2).set(+i - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(3).set(-i - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * i, 0);

	}

	public void startRotation() {
		rotateAngle = 0;
		rotating = true;
	}

	@Override
	public void draw(IRenderer renderer) {
		if (Gdx.gl20 == null) {
			renderer.drawPoly(pos, boxes, 0, c0, size);

			renderer.drawPoly(pos, boxes0, 0, c1, size);

			renderer.drawLines(pos, lines, 0, c0, size);
		} else {
			renderer.render(sprite, pos, size, 0, GL20.GL_TRIANGLES);
		}
	}

	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	@Override
	public int compareTo(Element arg0) {
		return arg0.hashCode() - this.hashCode();
	}

	public float getRotatingSpeed() {
		return rotatingSpeed;
	}

	public void setRotatingSpeed(float rotatingSpeed) {
		this.rotatingSpeed = rotatingSpeed;
	}

	@Override
	public void drawAfter(IRenderer renderer) {

	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
