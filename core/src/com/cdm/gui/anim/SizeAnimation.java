package com.cdm.gui.anim;

import com.cdm.gui.BigButton;

public class SizeAnimation implements Animation {

	private float t, interval;
	private Easing ease;
	private BigButton button;
	private float tw, th;
	private float w0, h0;

	public SizeAnimation(Easing easing, float pinterval, float pw, float ph,
			BigButton b) {
		ease = easing;
		interval = pinterval;
		t = 0;
		button = b;
		tw=pw;
		th=ph;
		w0 = b.getBBox().width();
		h0 = b.getBBox().height();
	}

	@Override
	public void tick(float time) {
		t += time;
		float x=button.getBBox().getXMiddle();
		float y=button.getBBox().getYMiddle();
		float w = ease.ease(w0, tw, t / interval);
		float h = ease.ease(h0, th, t / interval);
		button.setPos(x, y, w, h);
	}

	@Override
	public boolean finished() {
		return t >= interval;
	}


}
