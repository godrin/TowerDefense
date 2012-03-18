package com.cdm;

import com.badlogic.gdx.math.Vector3;

public class Settings {
	private static int CELL_WIDTH = 64;
	private static Vector3 position = new Vector3(0, 0, 0);

	public static float getScale() {
		return CELL_WIDTH;
	}

	public static void setScale(int scale) {
		CELL_WIDTH = scale;
	}

	public static Vector3 getPosition() {
		return position;
	}

	public static Vector3 getTranslation() {
		return new Vector3(position.x + 0.5f, position.y + 0.5f, 0);
	}

	public static void setPosition(Vector3 p) {
		position = p;
	}

	public static float getCellWidth() {
		return CELL_WIDTH;
	}

}
