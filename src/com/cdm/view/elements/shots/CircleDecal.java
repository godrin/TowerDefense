package com.cdm.view.elements.shots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;

public class CircleDecal extends Decal {
	private static final Color INNER_COLOR = new Color(0, 0, 0, 0.1f);
	private static final Color OUTER_COLOR = new Color(0, 0, 0, 0.1f);
	private PolySprite ps = new PolySprite();
	private Position pos;

	public CircleDecal(Position p) {
		pos = p;
		ps.makeArc(0, 0, 1, 0, 3.1415f * 2, 16, INNER_COLOR, OUTER_COLOR);
		ps.init();
	}

	public void draw(IRenderer renderer) {
		renderer.render(ps, pos, 0.2f, 0, GL10.GL_TRIANGLES);
	}
}
