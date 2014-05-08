package com.cdm.gui.effects;

// review1
public class AnimatorSin implements Animator {
	float mean, radius, speed, start;

	public AnimatorSin(float pmean, float pradius, float pspeed, float start) {
		speed = pspeed;
		mean = pmean;
		radius = pradius;
		this.start = start;
	}

	@Override
	public void addTime(float t, AnimationValueStore s) {
		s.value += t * speed;

	}

	@Override
	public float getValue(AnimationValueStore s) {
		return (float) (Math.sin(s.value * speed) * radius + mean);
	}

	@Override
	public float startValue() {
		return start;
	}
}
