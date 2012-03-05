package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;

public class Level {
	private List<Unit> units=new ArrayList<Unit>();

	public Level() {
		units.add(new Cannon(3, 3));
		units.add(new SmallShip(1, 1));
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
