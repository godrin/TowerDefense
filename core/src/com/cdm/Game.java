package com.cdm;

import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.Screen;

// review1
public interface Game {
	void setScreen(Screen newScreen);

	void setScreen(String string);

	void play(Type soundType);
}
