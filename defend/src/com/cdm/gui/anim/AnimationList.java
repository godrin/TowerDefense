package com.cdm.gui.anim;

import java.util.ArrayList;
import java.util.List;

public class AnimationList implements Animation {
	List<Animation> animations = new ArrayList<Animation>();
	List<Animation> toremove = new ArrayList<Animation>();

	@Override
	public void tick(float time) {

		for (Animation a : animations) {
			a.tick(time);
			if (a.finished())
				toremove.add(a);
		}
		for (Animation a : toremove) {
			animations.remove(a);
		}
		toremove.clear();
	}

	@Override
	public boolean finished() {
		return false;
	}

	public void add(Animation a) {
		animations.add(a);
	}
}
