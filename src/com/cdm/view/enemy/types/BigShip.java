package com.cdm.view.enemy.types;

import com.cdm.view.Position;

public class BigShip extends SmallShip {

	private float speed = 0.4f;

	public BigShip(Position position) {
		super(position);
		setSize(0.95f);
		setRayspeed(0.35f);

	}

	@Override
	public float getOriginalSpeed() {
		return speed;
	}

}
