package com.cdm.gui.anim;

import com.badlogic.gdx.graphics.Color;
import com.cdm.gui.BigButton;

public class ColorAnimation implements Animation {

	private float t, interval;
	private Easing ease;
	private BigButton button;
	private Color tc,c0;
	private static Color c=new Color(); 

	public ColorAnimation(Easing easing, float pinterval, Color targetColor,
			BigButton b) {
		ease = easing;
		interval = pinterval;
		t = 0;
		c=new Color();
		button = b;
		tc=targetColor;
		c0=new Color(b.getColor());
	}

	@Override
	public void tick(float time) {
		t += time;
		
		c.r = ease.ease(c0.r, tc.r, t / interval);
		c.g = ease.ease(c0.g, tc.g, t / interval);
		c.b = ease.ease(c0.b, tc.b, t / interval);
		c.a = ease.ease(c0.a, tc.a, t / interval);
		button.setColor(c);
	}

	@Override
	public boolean finished() {
		return t >= interval;
	}


}
