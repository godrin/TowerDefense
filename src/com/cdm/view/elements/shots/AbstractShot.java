package com.cdm.view.elements.shots;

import com.cdm.view.IRenderer;

public interface AbstractShot {

	void move(float time);

	void draw(IRenderer renderer);

}
