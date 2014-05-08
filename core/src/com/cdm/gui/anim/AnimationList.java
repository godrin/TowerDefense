package com.cdm.gui.anim;

import java.util.ArrayList;
import java.util.List;

//review1
public class AnimationList implements Animation {
	List<Animation> animations = new ArrayList<Animation>();
	List<Animation> toremove = new ArrayList<Animation>();

	@Override
	public void tick(float time) {

		for (int i = 0; i < animations.size(); i++) {
			Animation a = animations.get(i);
			a.tick(time);
			if (a.finished())
				toremove.add(a);
		}
		for (int i = 0; i < toremove.size(); i++) {

			animations.remove(toremove.get(i));
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
