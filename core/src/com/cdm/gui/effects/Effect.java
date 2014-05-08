package com.cdm.gui.effects;

import com.cdm.view.IRenderer;

// review1
public interface Effect {
	public void draw(IRenderer renderer);

	public void addTime(float time);
}
