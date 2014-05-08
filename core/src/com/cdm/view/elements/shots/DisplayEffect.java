package com.cdm.view.elements.shots;

import com.cdm.view.IRenderer;

public interface DisplayEffect {

	void move(float time);

	void draw(IRenderer renderer);

	boolean onScreen();

}
