package com.cdm.view.elements.shots;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.Position;

public interface ShotTarget {

	Vector3 getMovingDirection();

	Position getPosition();

	float getSpeed();

	void wasHitBy(MovingShot movingShot);

}
