package com.cdm.gui.anim;

public class ParallelAnimation implements Animation {

	private Animation[] animations;

	public ParallelAnimation(Animation... as) {
		animations = as;
	}

	@Override
	public void tick(float time) {
		for (int i = 0; i < animations.length; i++)
			animations[i].tick(time);

	}

	@Override
	public boolean finished() {
		for (int i = 0; i < animations.length; i++)
			if(!animations[i].finished())
				return false;

		return true;
	}

}
