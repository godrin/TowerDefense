package com.cdm.gui.effects;

public class AnimatedValue implements SingleValue {
	private AnimationValueStore s;
	private Animator modified;

	public AnimatedValue(AnimationValueStore ps, Animator pmodified) {
		s = ps;
		modified = pmodified;
	}

	@Override
	public void addTime(float t) {
		modified.addTime(t, s);
	}

	@Override
	public float getValue() {
		return modified.getValue(s);
	}
}
