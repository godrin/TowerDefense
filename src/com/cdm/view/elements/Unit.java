package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public abstract class Unit implements Element {

	public enum UnitType {
		CANNON, ROCKET, STUNNER, PHAZER
	};

	Position pos;
	float size;

	public Unit(Position p) {
		pos = p;
		size = 1.0f;
	}

	public abstract void move(float time);

	public abstract void draw(IRenderer renderer);

	@Override
	public void setPosition(Position p) {
		pos = p;
	}

	public Position getPosition() {
		return pos;
	}

	public void setSize(float f) {
		size = f;
	}

}
