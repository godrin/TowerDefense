package com.cdm.gui;

import com.badlogic.gdx.graphics.Color;
import com.cdm.SString;
import com.cdm.gui.effects.AnimatedColor;
import com.cdm.gui.effects.AnimatedRect;
import com.cdm.gui.effects.AnimatorSin;
import com.cdm.gui.effects.AnimatorStatic;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Rectangle;
import com.cdm.view.Position.RefSystem;

public class Button extends Widget {
	private int x, y, radius;
	private Position position;

	private IButtonPressed pressedListener;

	private AnimatedRect rect;

	private SString buttonName;

	private boolean enabled = true;
	private Color currentColor = new Color(1, 1, 0, 0.7f);

	public Button(int px, int py, int pradius) {
		x = px;
		y = py;
		position = new Position(x, y, RefSystem.Screen);
		radius = pradius;
		setBBox(new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius));
		initAnimation();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		boolean changed = (enabled != this.enabled);
		this.enabled = enabled;
		if (changed)
			initAnimation();
	}

	private void initAnimation() {

		AnimatedColor c;
		if (enabled)
			c = new AnimatedColor(new AnimatorStatic(0.9f), new AnimatorStatic(
					0.9f), new AnimatorStatic(0), new AnimatorSin(0.7f, 0.1f,
					1.0f, 0.0f));
		else
			c = new AnimatedColor(new AnimatorStatic(0.8f), new AnimatorStatic(
					0.8f), new AnimatorStatic(0.8f), new AnimatorSin(0.7f,
					0.1f, 1.0f, 0.0f));

		rect = new AnimatedRect(new AnimatorStatic(x), new AnimatorStatic(y),
				new AnimatorSin(radius, radius * 0.1f, 1.4f, 0),
				new AnimatorSin(radius, radius * 0.1f, 1.4f, 3.14f), c);
	}

	@Override
	public void addTime(float t) {
		super.addTime(t);
		rect.addTime(t);
	}

	public SString getButtonName() {
		return buttonName;
	}

	public void setButtonName(SString buttonName) {
		this.buttonName = buttonName;
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.fillRect(x - radius, y - radius, x + radius, y + radius,
				currentColor);
		rect.draw(renderer);
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

	public Position getPosition() {
		return position;
	}
}
