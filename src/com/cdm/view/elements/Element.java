package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public interface Element {
	void draw(IRenderer renderer);
	void setPosition(Position pos);

}
