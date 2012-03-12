package com.cdm.view.enemy;

public class EnemyDef {
	public float time;
	public EnemyType type;

	public EnemyDef(EnemyType pType, float pStartTime) {
		type = pType;
		time = pStartTime;
	}
}
