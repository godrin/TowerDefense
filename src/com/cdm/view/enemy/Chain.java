package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class Chain {

	private float chainPhase;
	private static final List<Vector3> chainLines = Arrays
			.asList(new Vector3[] { new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0), new Vector3(0, -1.5f, 0),
					new Vector3(0, -1.1f, 0),

					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0),
					new Vector3(0, 1.5f, 0), new Vector3(0, 1.1f, 0), });

	public void drawChain(IRenderer renderer, Position pos, float angle,
			Color outerColor, float scale) {
		drawChain(renderer, pos, angle, outerColor, scale, -0.9f,0.7f + 0.9f);
	}

	public void drawChain(IRenderer renderer, Position pos, float angle,
			Color outerColor, float scale, float startX,float delta) {
		float x;
		
		int size = 4;
		float speed = 0.5f;
		for (int i = 0; i < size; i++) {
			x = ((float) i) / size * 3.1415f * 0.5f;
			x += chainPhase * speed + 3.1415;
			x %= 3.1415 * 0.5;
			x = (float) Math.sin(x);
			x *= delta;
			x += startX;
			for (int lr = 0; lr < size * 4; lr += size * 2) {
				Vector3 a = chainLines.get(lr + i * 2);
				Vector3 b = chainLines.get(lr + i * 2 + 1);
				a.x = x;
				b.x = x;
			}
		}
		renderer.drawLines(pos, chainLines, angle, outerColor, scale);

	}

	public void move(float time) {
		chainPhase += time;

	}
}
