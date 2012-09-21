package com.cdm.gui.effects;

// review1
public class AnimatorStatic implements Animator {
	float start;

	public AnimatorStatic(float value) {
		this.start = value;
	}

	@Override
	public void addTime(float t, AnimationValueStore s) {
	}

	@Override
	public float getValue(AnimationValueStore s) {
		return s.value;
	}

	@Override
	public float startValue() {
		return start;
	}
}
