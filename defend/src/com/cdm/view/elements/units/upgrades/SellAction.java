package com.cdm.view.elements.units.upgrades;

import com.cdm.view.elements.Level;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.UnitAction;
import com.cdm.view.elements.units.UpgradeAction;

public class SellAction extends UnitAction {
	private PlayerUnit unit;

	public SellAction(PlayerUnit playerUnit) {
		unit = playerUnit;
	}

	@Override
	public String spriteFile() {
		return "cooling.sprite";
	}

	@Override
	public void doAction(PlayerUnit selectedUnit) {
		Level level = selectedUnit.getLevel();
		level.remove(selectedUnit);
	}

	@Override
	public Integer getCostForNext() {
		return unit.getCurrentValue();
	}
	
	private UpgradeAction getUpgradeAction() {
		for (UnitAction a : unit.getPossibleUpgrades()) {
			if (a instanceof UpgradeAction) {
				UpgradeAction x = (UpgradeAction) a;
				return x;
			}
		}
		return null;

	}

	@Override
	public UnitAction getNextUprade() {
		return new SellAction(unit);
	}

	@Override
	public void apply(PlayerUnit playerUnit) {
		// TODO Auto-generated method stub

	}

}
