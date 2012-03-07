package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public class Level {
	private List<Unit> units = new ArrayList<Unit>();

	public Level() {
		units.add(new Cannon(new Position(3, 3, RefSystem.Level)));
		units.add(new SmallShip(new Position(1, 1, RefSystem.Level)));
	}

	public void move(float time) {
		for (Unit unit : units) {
			unit.move(time);
		}
	}

	public void draw(IRenderer renderer) {
		for (Unit unit : units) {
			unit.draw(renderer);
		}

	}

}
