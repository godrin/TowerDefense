package com.cdm.view.enemy;

public enum EnemyType {
	SMALL_SHIP, TANK, BIG_SHIP, BIG_SHIP2, ROCKET, TRUCK, TANK2;

	public static final Float STRENGTH_THRESHOLD = 1.1f;

	public static EnemyType random() {
		double r = Math.random();
		if (r < 0.1)
			return TANK2;
		if (r < 0.2)
			return ROCKET;
		else if (r < 0.4)
			return TANK;
		else if (r < 0.6)
			return BIG_SHIP;
		else if (r < 0.8f)
			return TRUCK;
		else
			return SMALL_SHIP;
	}

	public float getStrength() {
		switch (this) {
		case SMALL_SHIP:
			return 1.0f;
		case TANK:
			return 1.5f;
		case BIG_SHIP:
			return 2.5f;
		case ROCKET:
			return 4.5f;
		case TRUCK:
			return 1.5f;
		case TANK2:
			return 2.0f;
		}
		return 0.0f;
	}
}
