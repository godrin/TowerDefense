package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.elements.Element;

// review1
public class Selector implements Element {
	private Position pos;
	private List<Vector3> lines = new ArrayList<Vector3>();
	float w = 1, pad = 0.16f;

	float angle = 0;
	float g = 0.8f;
	private Color color = new Color(g, g, g, 1);

	public Selector(Position p) {
		pos = p;
		// top left
		lines.add(new Vector3(-w, -w, 0));
		lines.add(new Vector3(-w + pad, -w, 0));
		lines.add(new Vector3(-w, -w, 0));
		lines.add(new Vector3(-w, -w + pad, 0));
		// top right
		lines.add(new Vector3(+w, -w, 0));
		lines.add(new Vector3(w - pad, -w, 0));
		lines.add(new Vector3(w, -w, 0));
		lines.add(new Vector3(w, -w + pad, 0));
		// bottom left
		lines.add(new Vector3(-w, w, 0));
		lines.add(new Vector3(-w + pad, w, 0));
		lines.add(new Vector3(-w, w, 0));
		lines.add(new Vector3(-w, w - pad, 0));
		// bottom right
		lines.add(new Vector3(w, w, 0));
		lines.add(new Vector3(w - pad, w, 0));
		lines.add(new Vector3(w, w, 0));
		lines.add(new Vector3(w, w - pad, 0));

	}

	public void draw(IRenderer renderer) {

		renderer.drawLines(pos, lines, angle, color, 0.5f);
	}

	@Override
	public void setPosition(Position p) {
		pos = p;
	}

	@Override
	public void move(float time) {

	}

	@Override
	public int compareTo(Element arg0) {
		return arg0.hashCode() - this.hashCode();
	}

	@Override
	public void drawAfter(IRenderer renderer) {

	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
