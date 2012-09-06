package com.cdm.view;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.elements.paths.PathPos;

public class Position {


	private CoordSystem system;

	public static CoordSystem SCREEN_REF = new CoordSystem(0, 0, 1);
	public static CoordSystem BUTTON_REF = new CoordSystem(0, 0, 1);
	public static CoordSystem LEVEL_REF = new CoordSystem(0, 0, 48);
	public static final Position NULL = new Position(0,0,LEVEL_REF);

	public float x;
	public float y;
	public static Vector3 tmpVector = new Vector3();
	public static Position tmpPos = new Position(0, 0, null);

	public Position(Position p) {
		x = p.x;
		y = p.y;
		system = p.system;
	}

	public Position(float px, float py, CoordSystem s) {
		x = px;
		y = py;
		system = s;
	}

	public Position(PathPos px, CoordSystem s) {
		x = px.x;
		y = px.y;
		system = s;
	}

	public boolean equals(Position o) {
		return Math.abs(x - o.x) < 0.01f && Math.abs(y - o.y) < 0.01f;
	}

	public boolean screenPos() {
		return system.equals(SCREEN_REF);
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

	public CoordSystem getSystem() {
		return system;
	}

	public Position set(float x2, float y2, CoordSystem system) {
		this.x = x2;
		this.y = y2;
		this.system = system;
		return this;
	}

	public Position set(Position dragPosition) {
		if (dragPosition == null) {
			x = y = -1;
			return this;
		}
		x = dragPosition.x;
		y = dragPosition.y;
		system = dragPosition.system;
		return this;
	}

	public Position to(CoordSystem s) {
		if (s.equals(system)) {

			return tmpPos.set(this);
		} else {
			return tmpPos.set(
					((x + system.getX()) * system.getScale()) / s.getScale()
							- s.getX(),
					((y + system.getY()) * system.getScale()) / s.getScale()
							- s.getY(), s);
		}
	}

	public boolean buttonPos() {
		return system.equals(BUTTON_REF);
	}

	public Position tmp() {
		return tmpPos.set(this);
	}

	public boolean valid() {
		return x >= 0 && y >= 0;
	}

	public float lengthTo(Position target) {
		float dx = x - target.x;
		float dy = y - target.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
}
