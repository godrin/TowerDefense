package com.cdm.gui.effects;

import com.cdm.view.IRenderer;

public interface Effect {
	public void draw(IRenderer renderer);

	public void addTime(float time);
}
