package com.cdm.gui.effects;

public interface Animator {
	public void addTime(float t,AnimationValueStore s);
	float getValue(AnimationValueStore s);
	float startValue();
}
