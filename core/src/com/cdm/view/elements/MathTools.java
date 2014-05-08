package com.cdm.view.elements;

import com.badlogic.gdx.math.Vector3;

public class MathTools {
	public static final float M_PI = 3.141592f;

	private static float getAngle(float x, float y) {
		if (y == 0.0) {
			if (x < 0.0)
				return -M_PI / 2.0f;
			else
				return M_PI / 2.0f;
		} else if (y < 0.0) {
			float a = M_PI + (float) Math.atan(x / y);
			if (a > M_PI)
				a -= M_PI * 2.0f;
			return a;
		} else
			return (float) Math.atan(x / y);
	}

	public static float angle(Vector3 delta) {

		return 90.0f - getAngle(delta.x, delta.y) * 180.0f / M_PI;

	}
}
