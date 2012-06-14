package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Grid.GridElement;

public class BackgroundElement implements Element {

	private List<Vector3> vecsOuter = new ArrayList<Vector3>();
	private List<Vector3> vecsInner = new ArrayList<Vector3>();

	private List<Vector3> boxes = new ArrayList<Vector3>();
	private List<Vector3> boxes0 = new ArrayList<Vector3>();

	private float size = .5f;
	private Position pos;
	private final Color c0 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private final Color c1 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private static final float OUTER = 1.0f;
	private static final float INNER = 0.8f;
	private static final float ROT_WIDTH = 0.2f;
	private static final float LIGHTING_SPEED = 0.6f;

	private boolean rotating = true;
	private float rotateAngle = 0;
	private float lightness = 0.0f;
	private GridElement gridElement;
	private float rotatingSpeed = 0.8f;

	public BackgroundElement(Position p, GridElement e) {
		pos = p;
		rotatingSpeed = ((p.x + p.y) * 0.3f + 5) * 0.1f;
		gridElement = e;
		// if (boxes == null) {

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
		// }
	}

	public void move(float t) {
		if (rotating) {
			rotateAngle += t * rotatingSpeed;
			if (rotateAngle > 3.1415f) {
				rotateAngle = 0;
				rotating = false;
			}
			setVecs();
		}
		setColor(t);
	}

	private void setColor(float t) {

		float v = (float) Math.sin(rotateAngle);

		if (gridElement != null) {
			if (gridElement.getList().size() > 0) {
				lightness += t * LIGHTING_SPEED;
				if (lightness > 1)
					lightness = 1;

			} else {
				lightness -= t * LIGHTING_SPEED;
				if (lightness < 0)
					lightness = 0;

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

		float k = 0.3f;
		c0.set(r, g, b, 1.0f);
		c1.set(r2, g2, b2, 1.0f);
		// c1.set(0.5f, 0.5f, 0.8f, 0.2f);

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
		renderer.drawPoly(pos, boxes, 0, c0, size);

		renderer.drawPoly(pos, boxes0, 0, c1, size);
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
		// TODO Auto-generated method stub
		
	}

}
