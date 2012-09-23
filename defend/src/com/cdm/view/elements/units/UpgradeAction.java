package com.cdm.view.elements.units;

import java.util.Map;
import java.util.TreeMap;

import com.cdm.view.elements.units.upgrades.UpgradeConfig;

public class UpgradeAction extends UnitAction {

	private Integer costs;
	private Integer levelNo;
	private String unitName;
	private Map<String, Float> values = new TreeMap<String, Float>();
	private Integer sellPrice;

	public UpgradeAction(Integer levelNo, String unitName,
			Map<String, Float> pValues) {

		costs = Math.round(pValues.get("price"));
		sellPrice = Math.round(pValues.get("sellPrice"));
		this.levelNo = levelNo;
		this.unitName = unitName;
		values = pValues;
	}

	@Override
	public void doAction(PlayerUnit selectedUnit) {

		selectedUnit.incLevel(this);
	}

	@Override
	public String spriteFile() {
		return "power.sprite";
	}

	@Override
	public Integer getCostForNext() {
		return costs;
	}

	@Override
	public UnitAction getNextUprade() {
		return UpgradeConfig.getUpgrade(unitName, levelNo + 1);
	}

	@Override
	public void apply(PlayerUnit playerUnit) {
		for (Map.Entry<String, Float> e : values.entrySet()) {
			playerUnit.setValue(e.getKey(), e.getValue());
		}
		playerUnit.setValue(Math.round(values.get("sellPrice")));

	}

	public Integer getSellPrice() {
		return sellPrice;
	}

}
