package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class BackgroundElement implements Element {

	private static List<Vector3> boxes = null;
	private static List<Vector3> boxes0 = null;
	private float size = 0.7f;
	private Position pos;
	private final static Color c0 = new Color(0.5f, 0.5f, 0.8f, 0.2f);
	private final static Color c1 = new Color(0.5f, 0.5f, 0.8f, 0.2f);

	public BackgroundElement(Position p) {
		pos = p;
		if (boxes == null) {
			float o = 0.7f;

			boxes = new ArrayList<Vector3>();
			boxes.add(new Vector3(-o, -o, 0));
			boxes.add(new Vector3(o, -o, 0));
			boxes.add(new Vector3(-o, o, 0));
			boxes.add(new Vector3(o, -o, 0));
			boxes.add(new Vector3(o, o, 0));
			boxes.add(new Vector3(-o, o, 0));

			float i = 0.67f;
			boxes0 = new ArrayList<Vector3>();
			boxes0.add(new Vector3(-i, -i, 0));
			boxes0.add(new Vector3(i, -i, 0));
			boxes0.add(new Vector3(-i, i, 0));
			boxes0.add(new Vector3(i, -i, 0));
			boxes0.add(new Vector3(i, i, 0));
			boxes0.add(new Vector3(-i, i, 0));
		}
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
