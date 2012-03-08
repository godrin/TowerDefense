package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public abstract class Unit implements Element {

	public enum UnitType {
		CANNON, ROCKET, STUNNER, PHAZER
	};

	Position pos;

	public Unit(Position p) {
		pos = p;
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

}
