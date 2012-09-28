package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.enemy.EnemyType;
import com.cdm.view.enemy.EnemyUnit;
import com.cdm.view.enemy.types.BigShip;
import com.cdm.view.enemy.types.Bug;
import com.cdm.view.enemy.types.Rocket;
import com.cdm.view.enemy.types.Rotor;
import com.cdm.view.enemy.types.SmallShip;
import com.cdm.view.enemy.types.Tank;
import com.cdm.view.enemy.types.Tank2;
import com.cdm.view.enemy.types.Truck;

// review1
public class EnemyUnits {
	public static EnemyUnit create(EnemyType t, Position position, int waveNo,
			int levelNo) {
		EnemyUnit u = null;
		if (EnemyType.BIG_SHIP.equals(t)) {
			if (levelNo != 2)
				u = new BigShip(position);
			else
				u = new Bug(position);
		}
		if (EnemyType.SMALL_SHIP.equals(t)) {
			if (levelNo != 2)
				u = new SmallShip(position);
			else
				u = new Tank(position);
		}
		if (EnemyType.TANK.equals(t)) {
			u = new Tank(position);
		}
		if (EnemyType.ROCKET.equals(t)) {
			if (levelNo >= 3)
				u = new Rocket(position);
			else
				u = new Tank2(position);
		}
		if (EnemyType.TRUCK.equals(t)) {
			u = new Truck(position);
		}
		if (EnemyType.TANK2.equals(t)) {
			u = new Tank2(position);
		}
		if (EnemyType.ROTOR.equals(t)) {
			if (levelNo >= 5)
				u = new Rotor(position);
			else
				u = new Bug(position);
		}
		if (EnemyType.BUG.equals(t)) {
			u = new Bug(position);
		}
		if (u != null)
			u.setEnergy(t.getEnergy(levelNo));
		return u;
	}
}
