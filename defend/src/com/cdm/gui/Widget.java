package com.cdm.gui;

import java.util.BitSet;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.cdm.view.IRenderer;
import com.cdm.view.Rectangle;

// review1
public abstract class Widget {
	private Rectangle bbox=new Rectangle(-1,-1,-1,-1);
	private BitSet touching = new BitSet(20); // too much ;)

	public void setBBox(Rectangle box) {
		bbox.set(box);
	}
	public void setBBox(float x,float y,float w,float h) {
		bbox.set(x,y,w,h);
	}

	public Rectangle getBBox() {
		return bbox;
	}

	public boolean wantsOuterEvents() {
		return false;
	}

	abstract public void draw(IRenderer renderer);

	public void addTime(float t) {

	}

	abstract public boolean opaque(int x, int y);

	public void touchDown(int x, int y, int pointer, int button) {
		int id = getPointerId(pointer, button);
		touching.set(id);
	}

	public void clicked(int x, int y, int pointer, int button) {

	}

	public void touchUp(int x, int y, int pointer, int button) {
		int id = getPointerId(pointer, button);
		if (touching.get(id)) {
			clicked(x, y, pointer, button);
		}
		touching.clear(id);
	}

	private int getPointerId(int pointer, int button) {
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			return button;
		}
		return pointer;
	}

	public void touchDrag(int x, int y, int pointer, int button) {

	}

}
