package com.cdm.view.elements.shots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;

public class CircleDecal extends Decal {
	private static final Color INNER_COLOR = new Color(0, 0, 0, 1f);
	private static final Color OUTER_COLOR = new Color(0.1f, 0.1f, 0.1f, 1f);
	private static final Color SHADOW_COLOR = new Color(0, 0, 0, 0);
	private static final float PI = 3.1415f;
	private PolySprite ps = new PolySprite();
	private Position pos;

	public CircleDecal(Position p) {
		pos = p;
		ps.makeArc(0, 0, 1, 0, PI * 2, 16, INNER_COLOR, OUTER_COLOR);

		ps.drawClosedPolyWithBorder(
				ps.makeArcVecs(0, PI * 2, 8, 1).toArray(new Vector3[8]),
				OUTER_COLOR, SHADOW_COLOR, 1.5f);

		ps.init();
	}

	public void draw(IRenderer renderer) {
		renderer.render(ps, pos, 0.2f, 0, GL20.GL_TRIANGLES);
	}
}
