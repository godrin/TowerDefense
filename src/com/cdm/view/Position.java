package com.cdm.view;

import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;
import com.cdm.view.Position.RefSystem;

public class Position {
	public enum RefSystem {
		Screen, Level;

		public boolean isLevel() {
			return Level.equals(this);
		}
	};

	private RefSystem system;

	public float x;

	public float y;
	public static Vector3 tmpVector = new Vector3();

	public Position(Position p) {
		x = p.x;
		y = p.y;
		system = p.system;
	}

	public Position(float px, float py, RefSystem s) {
		x = px;
		y = py;
		system = s;
	}

	public boolean equals(Position o) {
		return Math.abs(x - o.x) < 0.01f && Math.abs(y - o.y) < 0.01f;
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
		return Settings.getCellWidth();
	}

	public boolean screenPos() {
		return system.equals(RefSystem.Screen);
	}

	public Position toLevelPos() {
		if (!screenPos())
			return this;
		Vector3 p = Settings.getPosition();
		return new Position(x / Settings.getCellWidth() - p.x, y
				/ Settings.getCellWidth() - p.y, RefSystem.Level);
	}

	public Position alignedToGrid() {
		return new Position(Math.round(x), Math.round(y), system);
	}

	public String toString() {
		return "[Pos:" + x + "," + y + "]";
	}

	public void assignFrom(Position pos) {
		x = pos.x;
		y = pos.y;
		system = pos.system;

	}

	public Vector3 to(Position nextStep) {
		return tmpVector.set(nextStep.x - x, nextStep.y - y, 0);
	}

	public float distance(Position nextStep) {
		float dx = nextStep.x - x;
		float dy = nextStep.y - y;

		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public Vector3 toVector() {
		return tmpVector.set(x, y, 0);
	}

	public RefSystem getSystem() {
		return system;
	}

	public void set(int x2, int y2, RefSystem system) {
		this.x = x2;
		this.y = y2;
		this.system = system;

	}
}
