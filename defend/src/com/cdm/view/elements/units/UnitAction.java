package com.cdm.view.elements.units;

import com.cdm.view.PolySprite;
import com.cdm.view.SpriteReader;

public abstract class UnitAction {

	private PolySprite sprite = null;

	public abstract String spriteFile();

	//public abstract String valueName();

	public PolySprite getSprite() {
		if (sprite == null) {
			sprite = SpriteReader.read("/com/cdm/view/elements/units/"
					+ spriteFile());
		}
		return sprite;
	}

	public abstract void doAction(PlayerUnit selectedUnit);

	public abstract Integer getCostForNext();

	public abstract Integer getCurrentLevel();

	public abstract UnitAction getNextUprade();

	public abstract void apply(PlayerUnit playerUnit);

	//public abstract Float value();

}
