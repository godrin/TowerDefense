package com.cdm.view.elements;

import com.cdm.view.Position;

public class Elements {
	public static Unit getElementBy(Unit.UnitType t, Position p) {
		if (t == Unit.UnitType.CANNON) {
			return new Cannon(p);
		} else if (t == Unit.UnitType.ROCKET_THROWER) {
			return new RocketThrower(p);
		} else if (t == Unit.UnitType.ROCKET) {
			return new Rocket(p);
		}
		return null;
	}
}
