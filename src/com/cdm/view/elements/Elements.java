package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.elements.units.Cannon;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.RocketLauncher;
import com.cdm.view.elements.units.Stunner;
import com.cdm.view.elements.units.Unit;

public class Elements {
	public static Unit getElementBy(Unit.UnitType t, Position p) {
		PlayerUnit u = null;
		if (t == Unit.UnitType.CANNON) {
			u = new Cannon(p);
		} else if (t == Unit.UnitType.ROCKET_THROWER) {
			u = new RocketLauncher(p);
		} else if (t == Unit.UnitType.STUNNER) {
			u = new Stunner(p);
		}
		if (u != null)
			u.init();
		return u;
	}
}
