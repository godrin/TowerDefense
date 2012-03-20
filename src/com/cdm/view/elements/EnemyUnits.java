package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.enemy.BigShip;
import com.cdm.view.enemy.EnemyType;
import com.cdm.view.enemy.EnemyUnit;
import com.cdm.view.enemy.SmallShip;
import com.cdm.view.enemy.Tank;

public class EnemyUnits {
	public static EnemyUnit create(EnemyType t, Position position) {
		System.out.println("CREATE..");
		if (EnemyType.BIG_SHIP.equals(t)) {
			return new BigShip(position);
		}
		if (EnemyType.SMALL_SHIP.equals(t)) {
			return new SmallShip(position);
		}
		if (EnemyType.TANK.equals(t)) {
			return new Tank(position);
		}
		return null;
	}
}
