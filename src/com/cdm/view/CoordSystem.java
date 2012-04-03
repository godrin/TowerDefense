package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class CoordSystem {
	private float x = 0.0f, y = 0.0f, scale = 1.0f;
	private static final Vector3 tmp = new Vector3();

	public CoordSystem(float px, float py, float s) {
		x = px;
		y = py;
		scale = s;
	}

	public void apply() {
		MGL.gl.glScalef(scale, scale, scale);
		MGL.gl.glTranslatef(x, y, 0);
		Gdx.gl10.glLineWidth(scale* 0.04f);
	}

	public void setScale(float s) {
		scale = s;
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

	public void moveBy(int dx, int dy) {
		x += dx / scale;
		y += dy / scale;
	}

}
