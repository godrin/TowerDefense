package com.cdm.view.elements;

import java.util.Comparator;

import com.cdm.view.Position;
import com.cdm.view.enemy.EnemyUnit;

public class DistanceComparator implements Comparator<EnemyUnit> {

	private Position base;

	public DistanceComparator(Position position) {
		base = position;
	}

	@Override
	public int compare(EnemyUnit arg0, EnemyUnit arg1) {
		Position a = arg0.getPosition();
		Position b = arg1.getPosition();
		Float distA = a.to(base).len();
		Float distB = b.to(base).len();
		return distA.compareTo(distB);
	}

}
