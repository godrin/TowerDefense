package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public interface Element extends Comparable<Element>{
	void draw(IRenderer renderer);

	void setPosition(Position pos);

	void move(float time);

	void drawAfter(IRenderer renderer);

}
