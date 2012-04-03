package com.cdm.view;

import com.badlogic.gdx.math.Vector3;

public class ZoomerInput {
	private Vector3[] pos = new Vector3[20];

	public ZoomerInput() {
		for (int i = 0; i < pos.length; i++)
			pos[i] = new Vector3(-1, -1, -1);
	}
	public void down(int i,int x,int y) {
		
	}
}
