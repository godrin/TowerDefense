package com.cdm.gui.effects;

import com.badlogic.gdx.graphics.Color;

public class AnimatedColor {
	private SingleValue r, g, b, a;
	private Color c = new Color();

	public AnimatedColor(Animator r, Animator g, Animator b, Animator a) {
		this.r = new AnimatedValue(new AnimationValueStore(r.startValue()), r);
		this.g = new AnimatedValue(new AnimationValueStore(g.startValue()), g);
		this.b = new AnimatedValue(new AnimationValueStore(b.startValue()), b);
		this.a = new AnimatedValue(new AnimationValueStore(a.startValue()), a);
	}

	public Color getValue() {
		c.set(r.getValue(), g.getValue(), b.getValue(), a.getValue());
		return c;
	}

	public void addTime(float time) {
		r.addTime(time);
		g.addTime(time);
		b.addTime(time);
		a.addTime(time);

	}
}
