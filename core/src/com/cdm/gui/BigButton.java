package com.cdm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.gui.anim.Animation;
import com.cdm.gui.anim.AnimationList;
import com.cdm.gui.anim.ColorAnimation;
import com.cdm.gui.anim.Easings;
import com.cdm.gui.anim.ParallelAnimation;
import com.cdm.gui.anim.SizeAnimation;
import com.cdm.view.IRenderer;
import com.cdm.view.Rectangle;

// review1
public class BigButton extends Widget {

	private Color bg = new Color(0.7f, 0.7f, 0.0f, 0.7f);
	private Color bg2 = new Color(0.7f, 0.7f, 0.0f, 0.9f);
	private Color bgHighlight = new Color(0.9f, 0.8f, 0.0f, 0.8f);
	private Color curColor = new Color(bg);
	private Color textColor = new Color(0, 0, 0, 0.4f);
	private Rectangle topBox = new Rectangle(-1, -1, -1, -1);
	private String caption;
	private String name;
	private IButtonPressed buttonPressedListener;
	private AnimationList animation = new AnimationList();
	private float origW, origH;

	public BigButton(int mx, int my, int w, int h, String text,
			String buttonName, IButtonPressed listener) {
		caption = text;
		buttonPressedListener = listener;
		name = buttonName;
		origW = w;
		origH = h;
		setPos(mx, my, w, h);
	}

	public void setPos(float mx, float my, float w, float h) {
		setBBox(mx - w / 2, my - h / 2, w, h);
		topBox.set(mx - w / 2 + 5, my - h / 2 + 5, w - 10, 10);
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
		renderer.fillRect(box.x(), box.y(), box.x2(), box.y2(), curColor);
		renderer.fillRect(topBox.x(), topBox.y(), topBox.x2(), topBox.y2(), bg2);
		renderer.drawRect(box.x() - 5, box.y() - 5, box.x2() + 5, box.y2() + 5,
				curColor);
		renderer.drawText((int) box.x() + 5, (int) box.y2(), caption, textColor);
	}

	@Override
	public boolean opaque(int x, int y) {
		return getBBox().contains(x, y);
	}

	@Override
	public void clicked(int x, int y, int pointer, int button) {
		Gdx.input.vibrate(10);
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

	protected void onTouchDown() {
		add(new ParallelAnimation(new SizeAnimation(Easings.QUAD, 0.2f,
				origW * 1.1f, origH * 1.1f, this), new ColorAnimation(
				Easings.QUAD, 0.1f, bgHighlight, this)));

	}

	protected void onTouchUp() {
		add(new ParallelAnimation(new SizeAnimation(Easings.QUAD, 0.2f, origW,
				origH, this), new ColorAnimation(Easings.QUAD, 0.1f, bg, this)));
	}

	public void setColor(Color c) {
		curColor.set(c);
	}

	public Color getColor() {
		return curColor;
	}
}
