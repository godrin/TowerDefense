package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.elements.units.Unit.UnitType;

public class PlayerUnitDef {
	public PlayerUnitDef(Position position, UnitType unitType) {
		pos = position;
		type = unitType;
	}

	public Position pos;
	public Unit.UnitType type;
}
