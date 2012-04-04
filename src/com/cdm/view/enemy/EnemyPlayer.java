package com.cdm.view.enemy;

import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.view.Position;
import com.cdm.view.elements.EnemyUnits;
import com.cdm.view.elements.Level;

public class EnemyPlayer {
	public final float WAITING_TIME = 3.0f;

	enum Mode {
		ATTACK, WAIT
	};

	private Mode mode = Mode.WAIT;
	private int levelNo = 1;
	private float enemyStrength = 3.0f;
	private Level level;
	private float timeToNextWave = WAITING_TIME;
	private float timeInWave = 0.0f;
	private SortedSet<EnemyDef> defs = new TreeSet<EnemyDef>();
	private boolean alreadySent = false;

	public Level getLevel() {
		return level;
	}

	public int getLevelNo() {
		return levelNo;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void addTime(float t) {
		if (mode.equals(Mode.ATTACK)) {
			if (level.hasEnemies() || !alreadySent) {
				timeToNextWave = WAITING_TIME;
				timeInWave += t;
				while (defs.size() > 0 && defs.first().time < timeInWave) {
					EnemyDef def = defs.first();
					defs.remove(def);
					alreadySent = true;
					Position x = new Position(level.getEnemyStartPosition());
					EnemyUnit e = EnemyUnits.create(def.type, x);
					level.add(e);
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
		levelNo += 1;
		mode = Mode.WAIT;
		timeToNextWave = WAITING_TIME;
	}

	private void startNewWave() {
		timeInWave = 0.0f;
		mode = Mode.ATTACK;
		alreadySent = false;

		defs.clear();

		if (true) {
			// strength-based randomized enemy creation
			enemyStrength+=1.5f;
			Float currentStrength = enemyStrength;
			Float lastTime = 0.0f;

			while (currentStrength > 0) {
				EnemyType t = EnemyType.random();
				if (t.getStrength() < currentStrength
						+ EnemyType.STRENGTH_THRESHOLD) {
					currentStrength -= t.getStrength();
					lastTime += (float) Math.random()*5.0f + 0.6f;
					System.out.println("ADD " + t + " " + lastTime);
					defs.add(new EnemyDef(t, lastTime));
				}
			}
		} else {

			// elements must be sorted !
			defs.add(new EnemyDef(EnemyType.TANK, 1.0f));
			// if(false)
			for (int i = 3; i < 5; i++)
				defs.add(new EnemyDef(EnemyType.SMALL_SHIP, 1.0f * i));
		}
	}
}
