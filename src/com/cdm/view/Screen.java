package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.TowerGame;

public class Screen extends InputAdapter {

	public static final String LEVEL_SCREEN = "levelScreen";
	public static final String MENU_SCREEN = "menuScreen";
	public static final String OPTIONS_SCREEN = "optionsScreen";

	public void removed() {
		
	}

	public void wait(TowerGame towerGame) {
		// TODO Auto-generated method stub

	}

	public void tick(Input input) {
		// TODO Auto-generated method stub

	}

	public void render(float accum) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	}

	public void dispose() {

	}

}
