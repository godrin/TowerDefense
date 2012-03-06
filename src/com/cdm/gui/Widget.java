package com.cdm.gui;

import java.awt.Rectangle;
import com.cdm.view.IRenderer;

public abstract class Widget {
	private Rectangle bbox;

	public void setBBox(Rectangle box) {
		bbox = box;
	}

	public Rectangle getBBox() {
		return bbox;
	}

	public boolean wantsOuterEvents() {
		return false;
	}

	abstract public void draw(IRenderer renderer);

	abstract public boolean opaque(int x, int y);

	public void touchDown(int x, int y, int pointer, int button) {

	}

	public void touchUp(int x, int y, int pointer, int button) {

	}

	public void touchDrag(int x, int y, int pointer, int button) {

	}

}
