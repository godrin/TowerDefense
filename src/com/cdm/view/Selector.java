package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.Position.RefSystem;

public class Selector {
	public void draw(IRenderer renderer) {
		float w = 1, pad = 3*1.0f/Settings.CELL_WIDTH;
		Position p=new Position(3,3,RefSystem.Level);
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
		renderer.drawLines(p, lines, angle, new Color(g, g, g, 1));
	}
}
