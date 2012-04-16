package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.elements.units.Cannon;
import com.cdm.view.elements.units.RocketLauncher;
import com.cdm.view.elements.units.Stunner;
import com.cdm.view.elements.units.Unit;

public class Elements {
	public static Unit getElementBy(Unit.UnitType t, Position p) {
		if (t == Unit.UnitType.CANNON) {
			return new Cannon(p);
		} else if (t == Unit.UnitType.ROCKET_THROWER) {
			return new RocketLauncher(p);
		} else if (t == Unit.UnitType.STUNNER) {
			return new Stunner(p);
		} 
		return null;
	}
}
