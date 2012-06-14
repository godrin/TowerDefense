package com.cdm.view.elements.units.upgrades;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Upgrade;

public class SpeedUpgrade extends Upgrade {

	public final static Vector3 p = new Vector3(0, 1, 0);

	@Override
	public Vector3 menuPos() {
		return p;
	}

	@Override
	public int upgradeLevel(PlayerUnit u) {
		return 0;
	}

	@Override
	public String spriteFile() {
		return "speed.sprite";
	}
	@Override
	public int viewType() {
		return GL10.GL_LINES;
	}

}
