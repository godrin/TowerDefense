package com.cdm.view.elements;

import com.cdm.view.IRenderer;

public abstract class Unit {
	float x, y;
	
	public static final int CELL_WIDTH=32;

	public Unit(int px, int py) {
		x = px;
		y = py;
	}

	public abstract void move(float time);

	public abstract void draw(IRenderer renderer);
}
