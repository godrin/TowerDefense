package com.cdm.defend;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	private DefendGame game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		
		//AndroidApplicationConfiguration settings = new AndroidApplicationConfiguration();
        //settings.resolutionStrategy = new FillResolutionStrategy();
        cfg.touchSleepTime = 0;
        //settings.useAccelerometer = true;
        //settings.useCompass = true;
        //settings.useGL20 = true;
        cfg.useWakelock = false;

		
		initialize(game = new DefendGame(), cfg);
	}

	@Override
	public void onBackPressed() {
		if (game.backButtonPressed())
			super.onBackPressed();
	}
}