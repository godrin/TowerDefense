package com.cdm.gui.effects;

import com.cdm.view.IRenderer;

public class AnimatedRect implements Effect {

	SingleValue mx;
	SingleValue my;
	SingleValue pw;
	SingleValue ph;
	AnimatedColor c;

	public AnimatedRect(Animator mx, Animator my, Animator pw, Animator ph,
			AnimatedColor c) {
		this.mx = new AnimatedValue(new AnimationValueStore(mx.startValue()),
				mx);
		this.my = new AnimatedValue(new AnimationValueStore(my.startValue()),
				my);
		this.pw = new AnimatedValue(new AnimationValueStore(pw.startValue()),
				pw);
		this.ph = new AnimatedValue(new AnimationValueStore(ph.startValue()),
				ph);
		this.c=c;
	}

	@Override
	public void draw(IRenderer renderer) {
		float x = mx.getValue();
		float y = my.getValue();
		float w = pw.getValue();
		float h = ph.getValue();
		float x0 = x - w;
		float y0 = y - h;
		float x1 = x + w;
		float y1 = y + h;
		renderer.fillRect(x0, y0, x1, y1, c.getValue());
	}

	@Override
	public void addTime(float time) {
		mx.addTime(time);
		my.addTime(time);
		pw.addTime(time);
		ph.addTime(time);
		c.addTime(time);
	}

}
