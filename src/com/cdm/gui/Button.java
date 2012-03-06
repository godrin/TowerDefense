package com.cdm.gui;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Color;
import com.cdm.view.IRenderer;

public class Button extends Widget {
	private int x, y, radius;

	public Button(int px, int py, int pradius) {
		x = px;
		y = py;
		radius = pradius;
		setBBox(new Rectangle(x-radius, y-radius, radius*2, radius*2));
	}

	@Override
	public void draw(IRenderer renderer) {
		Color c = new Color(1, 1, 0, 1);
		renderer.fillRect(x - radius, y - radius, x + radius, y + radius, c);
	}

	@Override
	public boolean opaque(int x, int y) {
		System.out.println("OP");
		Rectangle r = new Rectangle(x - radius, y - radius, x + radius, y
				+ radius);
		return r.contains(x, y);
	}

	public void touchDown(int x, int y, int pointer, int button) {
		System.out.println("BUTTON TOUCH DOWN");
	}

}
