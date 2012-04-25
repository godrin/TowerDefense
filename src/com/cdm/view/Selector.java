package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.elements.Element;

public class Selector implements Element {
	private Position pos;

	public Selector(Position p) {
		pos = p;
	}

	public void draw(IRenderer renderer) {
		float w = 1, pad = 0.16f;// * 1.0f / Settings.getCellWidth();
		List<Vector3> lines = new ArrayList<Vector3>();
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

		float angle = 0;
		float g = 0.8f;
		renderer.drawLines(pos, lines, angle, new Color(g, g, g, 1), 0.5f);
	}

	@Override
	public void setPosition(Position p) {
		pos = p;
	}

	@Override
	public void move(float time) {
		// TODO Auto-generated method stub
		
	}
}
