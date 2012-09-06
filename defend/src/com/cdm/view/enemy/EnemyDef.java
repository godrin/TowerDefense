package com.cdm.view.enemy;

public class EnemyDef implements Comparable<EnemyDef> {
	public Float time;
	public EnemyType type;

	public EnemyDef(EnemyType pType, float pStartTime) {
		type = pType;
		time = pStartTime;
	}

	@Override
	public int compareTo(EnemyDef arg0) {
		return time.compareTo(arg0.time);
	}
}
