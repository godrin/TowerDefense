package com.cdm.gui;

import com.badlogic.gdx.graphics.Color;
import com.cdm.SString;
import com.cdm.gui.anim.Animation;
import com.cdm.gui.anim.AnimationList;
import com.cdm.view.IRenderer;
import com.cdm.view.Rectangle;

public class BigButton extends Widget {

	private Color bg = new Color(0.7f, 0.7f, 0.0f, 0.7f);
	private Color bg2 = new Color(0.7f, 0.7f, 0.0f, 0.9f);
	private Color textColor = new Color(0, 0, 0, 0.4f);
	private Rectangle topBox;
	private String caption;
	private SString name;
	private IButtonPressed buttonPressedListener;
	private AnimationList animation = new AnimationList();

	public BigButton(int mx, int my, int w, int h, String text,
			SString buttonName, IButtonPressed listener) {
		caption = text;
		buttonPressedListener = listener;
		name = buttonName;

		setPos(mx, my, w, h);
	}

	public void setPos(float mx, float my, float w, float h) {
		setBBox(new Rectangle(mx - w / 2, my - h / 2, w, h));
		topBox = new Rectangle(mx - w / 2 + 5, my - h / 2 + 5, w - 10, 10);
	}

	public float getX() {
		return getBBox().getXMiddle();
	}

	public float getY() {
		return getBBox().getYMiddle();
	}

	@Override
	public void draw(IRenderer renderer) {

		Rectangle box = getBBox();
		renderer.fillRect(box.x(), box.y(), box.x2(), box.y2(), bg);
		renderer.fillRect(topBox.x(), topBox.y(), topBox.x2(), topBox.y2(), bg2);
		renderer.drawRect(box.x() - 5, box.y() - 5, box.x2() + 5, box.y2() + 5,
				bg);
		renderer.drawText((int) box.x() + 5, (int) box.y2(), caption, textColor);
	}

	@Override
	public boolean opaque(int x, int y) {
		return getBBox().contains(x, y);
	}

	@Override
	public void clicked(int x, int y, int pointer, int button) {
		if (buttonPressedListener != null)
			buttonPressedListener.buttonPressed(name);
	}

	public void add(Animation a) {
		animation.add(a);
	}

	public void addTime(float time) {
		super.addTime(time);
		animation.tick(time);
	}
}
