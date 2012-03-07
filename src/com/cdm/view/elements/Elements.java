package com.cdm.view.elements;

import com.cdm.view.Position;

public class Elements {
	public static Element getElementBy(Unit.UnitType t, Position p) {
		if (t == Unit.UnitType.CANNON) {
			return new Cannon(p);
		}
		return null;
	}
}
