package com.cdm.view.elements.units;

import com.badlogic.gdx.math.Vector3;
import com.cdm.view.PolySprite;
import com.cdm.view.SpriteReader;

public abstract class Upgrade {
	private PolySprite sprite = null;

	public abstract Vector3 menuPos();

	public abstract int upgradeLevel(PlayerUnit u);

	public abstract String spriteFile();

	public PolySprite getSprite() {
		if (sprite == null) {
			sprite = SpriteReader.read("/com/cdm/view/elements/units/"
					+ spriteFile());
		}
		return sprite;
	}

	public abstract int viewType();

}
