package com.cdm.view.elements.units;

import com.cdm.view.elements.units.upgrades.UpgradeConfig;

public class UpgradeImpl extends Upgrade {

	private String file, value;
	private Integer costs;
	private Integer levelNo;
	private String unitName;
	private Float valuef;

	public UpgradeImpl(String pFile, String pvalueName, Integer pCosts,
			Integer levelNo, String unitName, Float pValue) {
		file = pFile;
		value = pvalueName;
		costs = pCosts;
		this.levelNo = levelNo;
		this.unitName = unitName;
		valuef = pValue;
	}

	@Override
	public String spriteFile() {
		return file;
	}

	@Override
	public String valueName() {
		return value;
	}

	@Override
	public Integer getCostForNext() {
		return costs;
	}

	@Override
	public Integer getCurrentLevel() {
		return levelNo;
	}

	@Override
	public Upgrade getNextUprade() {
		return UpgradeConfig.getUpgrade(unitName, levelNo + 1, value);
	}

	@Override
	public Float value() {
		return valuef;
	}

}
