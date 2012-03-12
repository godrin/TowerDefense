package com.cdm.view.elements;

import com.cdm.view.Position;
import com.cdm.view.enemy.EnemyType;

public class EnemyUnits {
	public static EnemyUnit create(EnemyType t, Position position) {
		System.out.println("CREATE..");
		if (EnemyType.SMALL_SHIP.equals(t)) {
			return new SmallShip(position);
		}
		return null;
	}
}
