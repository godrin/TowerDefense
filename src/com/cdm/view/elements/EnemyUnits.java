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

public class EnemyUnits {
	public static EnemyUnit create(EnemyType t, Position position, int waveNo) {
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
			//if ( >= 3)
			u = new Rocket(position);
		}
		if (EnemyType.TRUCK.equals(t)) {
			u = new Truck(position);
		}
		if (EnemyType.TANK2.equals(t)) {
			u = new Tank2(position);
		}
		if (EnemyType.ROTOR.equals(t)){
			u = new Rotor(position);
		}
		if (EnemyType.BUG.equals(t)){
			u = new Bug(position);
		}
		if (u != null)
			u.setEnergy(t.getEnergy(waveNo));
		return u;
	}
}
