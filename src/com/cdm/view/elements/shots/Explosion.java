package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Unit;

public class Explosion extends Unit implements Element {

	// private Position pos;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private List<Vector3> poly2;
	private float angle = 0;
	private float size = getSize();
	private Position mpos = new Position(getPosition());
	private Position npos = new Position(getPosition());
	private Position spos = new Position(getPosition());
	private Position tpos = new Position(getPosition());
	private Position upos = new Position(getPosition());
	private Position vpos = new Position(getPosition());

	public Explosion(Position pos) {
		super(pos);
		Vector3 a = new Vector3(-1, -1, 0);
		Vector3 a1 = new Vector3(0, -0.5f, 0);
		Vector3 b = new Vector3(1, -1, 0);
		Vector3 b1 = new Vector3(0.5f, 0, 0);
		Vector3 c = new Vector3(1, 1, 0);
		Vector3 c1 = new Vector3(0, 0.5f, 0);
		Vector3 d = new Vector3(-1, 1, 0);
		Vector3 d1 = new Vector3(-0.5f, 0, 0);
		Vector3 e = new Vector3(-0.5f, 0, 0);

		Vector3 s1 = new Vector3(-0.5f, -0.75f, 0);
		Vector3 s2 = new Vector3(-0.75f, -0.55f, 0);
		Vector3 s3 = new Vector3(-0, -0.25f, 0);

		lines = Arrays.asList(new Vector3[] { a, a1, a1, b, b, b1, b1, c, c,
				c1, c1, d, d, d1, d1, e, e, a });
		poly = Arrays.asList(new Vector3[] { a1, b1, c1, a1, c1, d1 });
		poly2 = Arrays.asList(new Vector3[] { s1, s2, s3 });

	}

	public void draw(IRenderer renderer) {
		if (size >= 0.0f) {
			Color outerColor = new Color(0.5f, 0.5f, 0, 0.7f);
			renderer.drawLines(getPosition(), lines, 0, outerColor, size,
					RefSystem.Level);
			Color innerColor = new Color(1, 0, 0, 0.6f);
			Color innerColor2 = new Color(0.75f, 0, 0.1f, 1);
			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor,
					size, RefSystem.Level);
			renderer.drawPoly(getPosition(), poly, 180 , outerColor,
			size,RefSystem.Level);
			renderer.drawPoly(mpos, poly2, angle, innerColor, size,
					RefSystem.Level);
			renderer.drawPoly(spos, poly2, angle + 180, innerColor, size,
					RefSystem.Level);
			renderer.drawPoly(npos, poly2, angle + 270, innerColor2, size+0.24f,
					RefSystem.Level);
			renderer.drawPoly(tpos, poly2, angle, innerColor, size+0.3f,
					RefSystem.Level);
			renderer.drawPoly(upos, poly2, angle, innerColor2, size+0.25f,
					RefSystem.Level);
			renderer.drawPoly(vpos, poly2, angle + 180, innerColor2, size+0.2f,
					RefSystem.Level);
			this.move(10f);
			size = shrink(size);
		}
	}

	@Override
	public void setPosition(Position pos) {
	}

	@Override
	public void move(float time) {
		angle -= time;
		mpos.x += 0.02f;
		mpos.y -= 0.02f;
		spos.x -= 0.02f;
		spos.y += 0.02f;
		npos.x += 0.015f;
		npos.y += 0.015f;
		tpos.x -= 0.01f;
		tpos.y -= 0.01f;
		upos.y += 0.02f;
		vpos.y -= 0.015f;
	}

	public float shrink(float size) {
		size -= 0.05;
		return size;
	}

}
