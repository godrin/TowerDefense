package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class BackgroundElement implements Element {

	private List<Vector3> vecsOuter = new ArrayList<Vector3>();
	private List<Vector3> vecsInner = new ArrayList<Vector3>();

	private List<Vector3> boxes = new ArrayList<Vector3>();
	private List<Vector3> boxes0 = new ArrayList<Vector3>();

	private float size = 0.7f;
	private Position pos;
	private final static Color c0 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private final static Color c1 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private static final float OUTER = 0.8f;
	private static final float INNER = 0.67f;
	private static final float ROT_WIDTH = 0.2f;

	private boolean rotating = true;
	private float rotateAngle = 0;

	public BackgroundElement(Position p) {
		pos = p;
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
			rotateAngle += t;
			if(rotateAngle>3.1415f){
				rotateAngle=0;
				rotating=false;
			}
			setVecs();
		}
	}

	private void setVecs() {
		// TODO Auto-generated method stub
		float o = OUTER;
		vecsOuter.get(0).set(-o + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(1).set(+o - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(2).set(+o - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * o, 0);
		vecsOuter.get(3).set(-o + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * o, 0);
		float i = INNER;
		vecsInner.get(0).set(-i + (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(1).set(+i - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				-(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(2).set(+i - (float) Math.sin(rotateAngle) * ROT_WIDTH,
				(float) Math.cos(rotateAngle) * i, 0);
		vecsInner.get(3).set(-i + (float) Math.sin(rotateAngle) * ROT_WIDTH,
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


}
