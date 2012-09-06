package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class SimpleShot extends MovingShot {
	public static final float SPEED = 1.5f;// 1.5f;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private static PolySprite backray;

	private static final Color INNER_COLOR = new Color(0.5f, 0, 0, 1.0f);
	private static final Color OUTER_COLOR = new Color(0.9f, 0, 0, 1.0f);
	private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
	private static final Color FRAME_COLOR = new Color(0.5f, 0.4f, 0, 0.7f);

	public SimpleShot(Position from, Position to, Level plevel, float impact,
			Vector3 movingDir) {
		super(from, to, plevel, impact, movingDir);
		Vector3 a = new Vector3(-0.75f, 0.4f, 0);
		Vector3 b = new Vector3(0.75f, 0.0f, 0);
		Vector3 c = new Vector3(-0.75f, -0.4f, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, a });
		poly = Arrays.asList(new Vector3[] { a, b, c });
		if (backray == null) {
			backray = new PolySprite();
			Vector3 v0 = new Vector3(-0.75f, -0.1f, 0);
			Vector3 v1 = new Vector3(-1.1f, -0.3f, 0);
			Vector3 v2 = new Vector3(-2.6f, 0f, 0);
			Vector3 v3 = new Vector3(-1.1f, 0.3f, 0);
			Vector3 v4 = new Vector3(-0.75f, 0.1f, 0);
			Vector3[] bpoly = new Vector3[] { v0, v1, v2, v3, v4 };

			backray.fillPoly(bpoly, FRAME_COLOR);
			backray.drawClosedPolyWithBorder(bpoly, INNER_COLOR,
					TRANSPARENT_COLOR, 0.4f);
			backray.init();
		}
	}

	@Override
	public float getSpeed() {
		return SPEED;
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawPoly(getPosition(), poly, angle, INNER_COLOR, getSize());
		renderer.drawLines(getPosition(), lines, angle, OUTER_COLOR, getSize());
		renderer.render(backray, getPosition(), getSize(), angle,
				GL10.GL_TRIANGLES);
		drawBurn(renderer);

	}

	@Override
	public void drawAfter(IRenderer renderer) {
	}

}
