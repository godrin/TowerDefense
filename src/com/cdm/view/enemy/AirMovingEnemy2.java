package com.cdm.view.enemy;

import com.badlogic.gdx.Gdx;
import com.cdm.view.Position;
import com.cdm.view.elements.units.PlayerUnit;

public abstract class AirMovingEnemy2 extends GroundMovingEnemy {

	public AirMovingEnemy2(Position pos) {
		super(pos);
	}

	protected Position createNextStep() {
		Position nextStep = getLevel().getNextStepToUnit(
				getPosition().tmp().alignedToGrid());
		if (!nextStep.valid()) {
			if (getLevel().getPlayerUnitAt(getPosition()) != null)
				attack(getLevel()
						.getPlayerUnitAt(getPosition().alignedToGrid()));

			return getLevel().getSomeEnemyEndPosition();
		}

		return nextStep;
	}

	public void attack(PlayerUnit punit) {

		// if (attackfreq > 2.0f) {
		// attackfreq = 0.0f;
		if (punit != null) {
			Gdx.app.log("mode", "attack");
			getLevel().unitDestroyed(punit.getPosition(), punit);
		}
	}

}
