package com.cdm.gui;

import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.UnitTypeSelected;
import com.cdm.view.elements.Unit;

public class UnitTypeButton extends Button {

	private Unit.UnitType type;
	private UnitTypeSelected listener;

	public UnitTypeButton(int px, int py, int pradius, Unit.UnitType ptype) {
		super(px, py, pradius);
		type = ptype;
	}

	public void touchDown(int x, int y, int pointer, int button) {
		if (listener != null)
			listener.unitTypeSelected(type,
					new Position(x, y, RefSystem.Screen));
	}

	public UnitTypeSelected getListener() {
		return listener;
	}

	public void setListener(UnitTypeSelected listener) {
		this.listener = listener;
	}

}
