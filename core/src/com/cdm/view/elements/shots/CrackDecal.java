package com.cdm.view.elements.shots;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;

public class CrackDecal extends Decal {
	private final static float CRACKING_TIME = 1.5f;
	private final static int CRACK_COUNT = 4;
	private final static int CRACK_LEN = 5;

	private static final float PI = 3.1415f;
	private PolySprite ps;
	private Position pos;
	private float time;

	private Vector3[][] cracks;
	private final static Random r = new Random();
	private static final float PART_LEN = 1.5f;

	public CrackDecal(Position p) {
		pos = p;
		time = 0;

		cracks = new Vector3[CRACK_COUNT][CRACK_LEN];
		for (int crackIndex = 0; crackIndex < CRACK_COUNT; crackIndex++) {
			float startAngle = crackIndex * 2 * PI / CRACK_COUNT;
			float curAngle = startAngle;

			for (int crackDepth = 0; crackDepth < CRACK_LEN; crackDepth++) {
				curAngle += fuzzAngle(4);
				Vector3 last;
				if (crackDepth == 0)
					last = new Vector3(0, 0, 0);
				else
					last = cracks[crackIndex][crackDepth - 1];
				Vector3 currentVector = new Vector3(last);
				float curLength = r.nextFloat() * PART_LEN;
				currentVector.x += Math.sin(curAngle) * curLength;
				currentVector.y += Math.cos(curAngle) * curLength;

				cracks[crackIndex][crackDepth] = currentVector;
			}
		}

	}

	private static float fuzzAngle(float factor) {
		float rnd = (r.nextFloat() - 0.5f);
		return rnd * rnd * Math.signum(rnd) * 3;
	}

	public void draw(IRenderer renderer) {
		if (time < CRACKING_TIME || ps == null) {
			// System.out.println("INIT "+time);
			float advance = time / CRACKING_TIME;
			if (advance > 1)
				advance = 1;
			ps = new PolySprite();

			for (int crackCount = 0; crackCount < CRACK_COUNT; crackCount++) {
				Vector3 last = new Vector3(0, 0, 0);
				float thickLast = 0.7f * advance;
				for (int crackDepth = 0; crackDepth < CRACK_LEN; crackDepth++) {
					Vector3 current = cracks[crackCount][crackDepth];
					float thickCurrent = thickLast * 0.7f;
					ps.drawThickLine(last, current, thickLast, thickCurrent,
							Color.BLACK, Color.BLACK);
					thickLast = thickCurrent;
					last = current;
				}

			}

			ps.init();

		}

		renderer.render(ps, pos, 0.2f, 0, GL20.GL_TRIANGLES);

	}

	public void move(float delta) {
		time += delta;
	}
}
