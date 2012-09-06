package com.cdm.gui.anim;

public class LinearEasing implements Easing {

	@Override
	public float ease(float a, float b, float time) {
		if (time > 1)
			time = 1;
		else if (time < 0)
			time = 0;
		return a * (1 - time) + b * time;
	}

}
