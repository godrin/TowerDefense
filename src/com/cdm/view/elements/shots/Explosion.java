package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Unit;

public class Explosion extends Unit implements Element {

	// private Position pos;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private List<Vector3> poly2;
	private float angle = 0;
	private float size = getSize();
	private float size2 = getSize();
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

			Color outerColor = new Color(0.5f, 0.5f, 0, 1.0f);

			// @undermink: this is how it was meant ??
			renderer.drawLines(getPosition(), lines, 180, outerColor, size);

			Color innerColor = new Color(1, 0, 0, 1);
			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor,
					size);
			renderer.drawPoly(getPosition(), poly, 180, outerColor, size
					);
			size = shrink(size);
			//System.out.println("Positions: " + mpos + " " + spos + " " + npos + " "
				//	+ tpos + " " + upos + " " + vpos);
		}
		if (size2 >= -10){
			Color innerColor = new Color(1, 0, 0, 1);
			Color innerColor2 = new Color(0.75f, 0.5f, 0.1f, 1);
		renderer.drawPoly(mpos, poly2, angle, innerColor, size2);
		renderer.drawPoly(spos, poly2, angle + 180, innerColor, size2);
		renderer.drawPoly(npos, poly2, angle + 270, innerColor2,
				size2 + 0.24f);
		renderer.drawPoly(tpos, poly2, angle, innerColor, size2 + 0.3f);
		renderer.drawPoly(upos, poly2, angle, innerColor2, size2 + 0.25f);
		renderer.drawPoly(vpos, poly2, angle + 180, innerColor2,
				size2 + 0.2f);
		//this.move(10f);
		size2 -= 0.03f;

			renderer.drawLines(getPosition(), lines, 180 - 45, innerColor, size);
			renderer.drawPoly(getPosition(), poly, 180 - 45, innerColor2, size);
			
			
			// @undermink - moved to move()
			// size = shrink(size);

		}
	}

		public void move(float time) {

		angle -= 10;
		mpos.x += Explosion.random();
		mpos.y -= Explosion.random();
		spos.x -= Explosion.random();
		spos.y += Explosion.random();
		npos.x += Explosion.random();
		npos.y += Explosion.random();
		tpos.x -= Explosion.random();
		tpos.y -= Explosion.random();
		upos.y += Explosion.random();
		vpos.y -= Explosion.random();

		// shrink
		//size -= time * 0.2f;
		if (size < 0) {
			// remove from level
		}

	}

	@Deprecated
	public float shrink(float size) {
		size -= 0.05;
		return size;
	}

	public static float random() {
		double i = Math.random() / 33;
		return (float) i;
	}

}
