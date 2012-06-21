package com.cdm.view.elements.units;

import com.cdm.view.PolySprite;
import com.cdm.view.SpriteReader;

public abstract class Upgrade {

	private PolySprite sprite = null;

	public abstract String spriteFile();

	public abstract String valueName();

	public PolySprite getSprite() {
		if (sprite == null) {
			sprite = SpriteReader.read("/com/cdm/view/elements/units/"
					+ spriteFile());
		}
		return sprite;
	}

	public void upgrade(PlayerUnit selectedUnit) {
		selectedUnit.incLevel(this);
	}

	public abstract Integer getCostForNext();

	public abstract Integer getCurrentLevel();

	public abstract Upgrade getNextUprade();

	public abstract Float value();

}
