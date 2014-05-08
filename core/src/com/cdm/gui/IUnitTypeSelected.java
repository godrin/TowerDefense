package com.cdm.gui;

import com.cdm.view.Position;
import com.cdm.view.elements.units.Unit;

// review1
public interface IUnitTypeSelected {
	void unitTypeSelected(Unit.UnitType type, Position screenPos, int cost);
}
