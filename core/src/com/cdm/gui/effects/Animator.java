package com.cdm.gui.effects;

// review1
public interface Animator {
	public void addTime(float t,AnimationValueStore s);
	float getValue(AnimationValueStore s);
	float startValue();
}
