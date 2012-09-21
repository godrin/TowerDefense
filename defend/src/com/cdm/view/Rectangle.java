package com.cdm.view;

// review1
public class Rectangle {
	private float x, y, w, h;

	public Rectangle(float px, float py, float pw, float ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float x2() {
		return x + w;
	}

	public float y2() {
		return y + h;
	}

	public float width() {
		return w;
	}

	public float height() {
		return h;
	}

	public boolean contains(int x2, int y2) {
		return x2 >= x && x2 <= x + w && y2 >= y && y2 <= y + h;
	}

	private float min(float a, float b) {
		return a < b ? a : b;
	}

	private float max(float a, float b) {
		return a > b ? a : b;
	}

	public void add(Rectangle bBox) {
		float x0 = min(x(), bBox.x());
		float x1 = max(x2(), bBox.x2());
		float y0 = min(y(), bBox.y());
		float y1 = max(y2(), bBox.y2());
		x = x0;
		y = y0;
		w = x1 - x0;
		h = y1 - y0;
	}

	public float getXMiddle() {
		return x + w / 2;
	}

	public float getYMiddle() {
		return y + h / 2;
	}

	public void set(float x2, float y2, float w2, float h2) {
		x = x2;
		y = y2;
		w = w2;
		h = h2;
	}

	public void set(Rectangle box) {
		x=box.x;
		y=box.y;
		w=box.w;
		h=box.h;
		
	}

}
