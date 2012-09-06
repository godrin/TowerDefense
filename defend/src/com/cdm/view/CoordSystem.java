package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class CoordSystem {
	private float x = 0.0f, y = 0.0f, scale = 1.0f, w = -1, h = -1;
	private static final Vector3 tmp = new Vector3();
	private static final float MARGIN = 1.0f / 2.0f;

	public CoordSystem(float px, float py, float s) {
		x = px;
		y = py;
		scale = s;
	}

	public void apply() {
		if (Gdx.gl10 != null) {
			Gdx.gl10.glScalef(scale, scale, scale);
			Gdx.gl10.glTranslatef(x, y, 0);
			Gdx.gl10.glLineWidth(scale * 0.04f);
		} else {
			Gdx.gl20.glLineWidth(scale * 0.04f);
			Renderer.scaleMatrix(scale,scale,scale);
			Renderer.translateMatrix(x,y,0);
		}
	}

	public void setScale(float s) {
		scale = s;
		checkTranslation();
	}

	public Vector3 getTranslate() {
		return tmp.set(x, y, 0);
	}

	public void setTranslate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector3 translateFromThisTo(Vector3 v, CoordSystem s) {
		return tmp.set((v.x - x) / scale * s.scale + s.x, (v.y - y) / scale
				* s.scale + s.y, 0);

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getScale() {
		return scale;
	}

	public void checkTranslation() {
		if (x > 0.5f + MARGIN)
			x = 0.5f + MARGIN;
		if (y > 0.5f + MARGIN)
			y = 0.5f + MARGIN;
		if (h > 0 && w > 0) {

			float mw = w - Gdx.graphics.getWidth() / getScale();
			float mh = h + 1 - Gdx.graphics.getHeight() / getScale();

			if (x < -mw)
				x = -mw;
			if (y < -mh)
				y = -mh;
		}
	}

	public void moveBy(int dx, int dy) {
		x += dx / scale;
		y += dy / scale;
		checkTranslation();
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public void setHeight(int h2) {
		this.h = h2;
	}

	public float getW() {
		return w;
	}
	public float getH() {
		return h;
	}

}
