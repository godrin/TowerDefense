package com.cdm.gui.anim;

import com.cdm.gui.BigButton;

//review1

public class MoveAnimation implements Animation {

	private float t, interval;
	private Easing ease;
	private BigButton button;
	private float tx, ty;
	private float x0, y0;

	public MoveAnimation(Easing easing, float pinterval, float px, float py,
			BigButton b) {
		ease = easing;
		interval = pinterval;
		t = 0;
		button = b;
		tx = px;
		ty = py;
		x0 = b.getBBox().getXMiddle();
		y0 = b.getBBox().getYMiddle();
	}

	@Override
	public void tick(float time) {
		t += time;
		float mx = ease.ease(x0, tx, t / interval);
		float my = ease.ease(y0, ty, t / interval);
		float w = button.getBBox().width();
		float h = button.getBBox().height();
		button.setPos(mx, my, w, h);
	}

	@Override
	public boolean finished() {
		return t >= interval;
	}

}
