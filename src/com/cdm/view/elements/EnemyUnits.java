package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.enemy.BigShip;
import com.cdm.view.enemy.EnemyType;
import com.cdm.view.enemy.EnemyUnit;
import com.cdm.view.enemy.Rocket;
import com.cdm.view.enemy.SmallShip;
import com.cdm.view.enemy.Tank;
import com.cdm.view.enemy.Tank2;
import com.cdm.view.enemy.Truck;

public class EnemyUnits {
	public static EnemyUnit create(EnemyType t, Position position, int levelNo) {
		EnemyUnit u = null;
		if (EnemyType.BIG_SHIP.equals(t)) {
			u = new BigShip(position);
		}
		if (EnemyType.SMALL_SHIP.equals(t)) {
			u = new SmallShip(position);
		}
		if (EnemyType.TANK.equals(t)) {
			u = new Tank(position);
		}
		if (EnemyType.ROCKET.equals(t)) {
			u = new Rocket(position);
		}
		if (EnemyType.TRUCK.equals(t)) {
			u = new Truck(position);
		}
		if (EnemyType.TANK2.equals(t)) {
			u = new Tank2(position);
		}
		if (u != null)
			u.setEnergy(t.getEnergy(levelNo));
		return u;
	}
}
