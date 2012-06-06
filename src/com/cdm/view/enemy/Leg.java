package com.cdm.view.enemy;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class Leg {
	private static final Vector3 a = new Vector3(0f, 0.3f, 0);
	private static final Vector3 b = new Vector3(-0.9f, 1.1f, 0);
	private static final Vector3 c = new Vector3(-0.9f, 1.1f, 0);

	private static final List<Vector3> lines = Arrays.asList(new Vector3[] { a,
			b, b,c });

	public void drawLeg(IRenderer renderer, Position pos, float angle,
			Color outerColor, float scale, float startX,float endX,
			float alpha,float yDir) {
		
		a.x=startX;
		a.y=-0.3f*yDir;
		b.x=(startX+endX)*0.5f+(float)Math.sin(alpha+0.4f)*0.6f;
		b.y=-0.5f*yDir+(float)Math.cos(alpha+0.4f)*0.1f;
		c.x=endX+(float)Math.sin(alpha)*0.9f;
		c.y=-1.3f*yDir+(float)Math.cos(alpha)*0.3f;
		
		renderer.drawLines(pos, lines, angle, outerColor, scale);

	}
}
