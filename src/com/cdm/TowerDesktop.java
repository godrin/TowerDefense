package com.cdm;

import com.badlogic.gdx.backends.jogl.JoglApplication;


public class TowerDesktop {
	public static void main(String[] argv) {
		new JoglApplication(new TowerGame(), "Tower defense", 320, 240, false);
	}
}
