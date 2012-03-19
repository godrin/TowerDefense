package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.view.elements.Level;

public class LevelDisplays {
	private Level level;
	private Color moneyColor=new Color(0.7f,0.7f,1.0f,1.0f);

	public LevelDisplays() {

	}
	


	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	void draw(IRenderer renderer) {
		renderer.drawText(0, Gdx.graphics.getHeight(), "$"+Integer.toString(level.getMoney()),moneyColor);
	}
}
