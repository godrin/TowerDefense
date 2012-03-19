package com.cdm.gui;

import com.cdm.view.Position;
import com.cdm.view.elements.Unit;

public interface IUnitTypeSelected {
	void unitTypeSelected(Unit.UnitType type, Position screenPos, int cost);
}
