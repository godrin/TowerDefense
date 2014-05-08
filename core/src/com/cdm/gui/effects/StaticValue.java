package com.cdm.gui.effects;

// review1
public class StaticValue implements SingleValue {

	private float value;

	public StaticValue(float v) {
		value = v;
	}

	@Override
	public void addTime(float t) {
	}

	@Override
	public float getValue() {
		return value;
	}

}
