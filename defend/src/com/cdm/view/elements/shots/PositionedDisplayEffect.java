package com.cdm.view.elements.shots;

import com.cdm.view.Position;

public abstract class PositionedDisplayEffect implements DisplayEffect {

	public abstract Position getPosition();

	@Override
	public boolean onScreen() {
		return getPosition().onScreen();
	}

}
