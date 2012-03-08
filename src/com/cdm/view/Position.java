package com.cdm.view;

import com.cdm.Settings;

public class Position {
	public enum RefSystem {
		Screen, Level
	};

	private RefSystem system;

	public float x;

	public float y;

	public Position(float px, float py, RefSystem s) {
		x = px;
		y = py;
		system = s;
	}

	public float getX() {
		return x * getScale();
	}

	public float getY() {
		return y * getScale();
	}

	public float getScale() {
		if (screenPos())
			return 1;
		return Settings.CELL_WIDTH;
	}

	public boolean screenPos() {
		return system.equals(RefSystem.Screen);
	}

	public Position toLevelPos() {
		if (!screenPos())
			return this;
		return new Position(x / Settings.CELL_WIDTH, y / Settings.CELL_WIDTH,
				RefSystem.Level);
	}

	public Position alignToGrid() {
		return new Position(Math.round(x), Math.round(y), system);
	}
}
