package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.elements.Unit;

public class Selector {
	public void draw(IRenderer renderer) {
		float w=Unit.CELL_WIDTH,pad=6;
		float scale = 1;
		float x = 3*w;
		float y = 3*w;
		List<Vector3> lines = new ArrayList<Vector3>();
		// top left
		lines.add(new Vector3(-w,-w,0));
		lines.add(new Vector3(-w+pad,-w,0));
		lines.add(new Vector3(-w,-w,0));
		lines.add(new Vector3(-w,-w+pad,0));
		// top right
		lines.add(new Vector3(+w,-w,0));
		lines.add(new Vector3(w-pad,-w,0));
		lines.add(new Vector3(w,-w,0));
		lines.add(new Vector3(w,-w+pad,0));
		// bottom left
		lines.add(new Vector3(-w,w,0));
		lines.add(new Vector3(-w+pad,w,0));
		lines.add(new Vector3(-w,w,0));
		lines.add(new Vector3(-w,w-pad,0));
		// bottom right
		lines.add(new Vector3(w,w,0));
		lines.add(new Vector3(w-pad,w,0));
		lines.add(new Vector3(w,w,0));
		lines.add(new Vector3(w,w-pad,0));
		
		float angle=0;
		renderer.drawLines(x, y, lines, angle, scale);
	}
}
