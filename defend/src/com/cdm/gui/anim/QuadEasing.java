package com.cdm.gui.anim;

// review1
public class QuadEasing implements Easing {

	@Override
	public float ease(float a, float b, float time) {
		if (time > 1)
			time = 1;
		else if (time < 0)
			time = 0;
		time=1-time;
		time*=time;
		time=1-time;
		
		return a * (1 - time) + b * time;
	}

}
