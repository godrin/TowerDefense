package com.cdm.gui;


import com.badlogic.gdx.graphics.Color;
import com.cdm.SString;
import com.cdm.view.IRenderer;
import com.cdm.view.Rectangle;

public class Button extends Widget {
	private int x, y, radius;

	private IButtonPressed pressedListener;

	private SString buttonName;

	public Button(int px, int py, int pradius) {
		x = px;
		y = py;
		radius = pradius;
		setBBox(new Rectangle(x - radius, y - radius, 2*radius, 2*radius));
	}

	public SString getButtonName() {
		return buttonName;
	}

	public void setButtonName(SString buttonName) {
		this.buttonName = buttonName;
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

	@Override
	public void clicked(int x, int y, int pointer, int button) {
		if (pressedListener != null)
			pressedListener.buttonPressed(buttonName);
	}

	public IButtonPressed getPressedListener() {
		return pressedListener;
	}

	public void setPressedListener(IButtonPressed pressedListener) {
		this.pressedListener = pressedListener;
	}
}
