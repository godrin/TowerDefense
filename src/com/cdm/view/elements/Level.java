package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.Selector;
import com.cdm.view.elements.Unit.UnitType;

public class Level {
	private List<Unit> units = new ArrayList<Unit>();
	private Selector selector = null;

	public Level() {
		units.add(new Cannon(new Position(3, 3, RefSystem.Level)));
		units.add(new SmallShip(new Position(1, 1, RefSystem.Level)));
	}

	public void add(Position pos, UnitType type) {
		units.add(Elements.getElementBy(type, pos));
	}

	public void hover(Position pos) {
		if (pos.screenPos()) {
			pos = pos.toLevelPos().alignToGrid();
		}
		selector = new Selector(pos);
	}

	public void stopHover() {
		selector = null;
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
		if (selector != null)
			selector.draw(renderer);

	}

	public void add(Unit dragElement) {
		dragElement.setPosition(dragElement.getPosition().toLevelPos()
				.alignToGrid());
		units.add(dragElement);
	}

}
