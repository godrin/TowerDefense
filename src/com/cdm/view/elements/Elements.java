package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.elements.Unit.UnitType;

public class Elements {
	public static Unit getElementBy(Unit.UnitType t, Position p) {
		if (t == Unit.UnitType.CANNON) {
			return new Cannon(p);
		}
		return null;
	}
}
