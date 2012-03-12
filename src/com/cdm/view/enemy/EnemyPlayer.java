package com.cdm.view.enemy;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.Position;
import com.cdm.view.elements.EnemyUnit;
import com.cdm.view.elements.EnemyUnits;
import com.cdm.view.elements.Level;

public class EnemyPlayer {
	public final float WAITING_TIME = 3.0f;

	enum Mode {
		ATTACK, WAIT
	};

	private Mode mode = Mode.WAIT;
	private int levelNo = 1;
	private Level level;
	private Float timeToNextWave = WAITING_TIME;
	private Float timeInWave = 0.0f;
	private List<EnemyDef> defs = new ArrayList<EnemyDef>();
	private int listIndex = 0;

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void addTime(float t) {
		if (mode.equals(Mode.ATTACK)) {
			if (level.hasEnemies()) {
				timeToNextWave = WAITING_TIME;
				timeInWave += t;
				while (listIndex < defs.size()
						&& defs.get(listIndex).time < timeInWave) {
					Position x = new Position(level.getEnemyStartPosition());
					EnemyUnit e = EnemyUnits
							.create(defs.get(listIndex).type, x);
					level.add(e);
					listIndex += 1;
				}
			} else
				startWait();
		} else {
			if (timeToNextWave < 0) {
				startNewWave();
			} else {
				timeToNextWave -= t;

			}
		}
	}

	private void startWait() {
		mode = Mode.WAIT;
		timeToNextWave = WAITING_TIME;
	}

	private void startNewWave() {
		timeInWave = 0.0f;
		mode = Mode.ATTACK;
		listIndex = 0;

		defs.clear();
		defs.add(new EnemyDef(EnemyType.TANK, 1.0f ));
		for (int i = 3; i < 5; i++)
			defs.add(new EnemyDef(EnemyType.SMALL_SHIP, 1.0f * i));
	}
}
