package com.cdm.view.elements.shots;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class RocketShot extends MovingShot {

	public static float speed = 1.5f;
	private PolySprite sprite = null;

	private List<Vector3> raySprites = new ArrayList<Vector3>();

	public RocketShot(Position from, Position to, Level plevel, int pImpact) {
		super(from, to, plevel, pImpact);
		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillRectangle(-0.75f, -0.2f, 1.5f, 0.4f, new Color(0.7f,
					0.5f, 0.2f, 0.9f));
			sprite.fillRectangle(-0.75f, -0.3f, 0.6f, 0.6f, new Color(0.4f,
					0.2f, 0.08f, 0.9f));
			sprite.fillRectangle(0.75f, -0.1f, 0.2f, 0.2f, new Color(0.0f, 0,
					0, 0.9f));
			sprite.init();
		}

	}

	public float getSize() {
		return 0.3f;
	}

	protected void afterMove(Position ppos) {
		Vector3 pos = new Vector3(ppos.toVector());

		Vector3 last = null;
		if (raySprites.size() > 0) {
			last = raySprites.get(raySprites.size() - 1);

			Vector3 dist = new Vector3(pos);
			dist.sub(last);
			if (dist.len() > 0.3f) {
				raySprites.add(pos);
			}
		} else
			raySprites.add(pos);

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.render(sprite, pos, getSize(), angle, GL10.GL_TRIANGLES);

		PolySprite s = new PolySprite();
		Vector3 ln = null;
		for (int i = 0; i < raySprites.size() - 1; i++) {
			Vector3 xa = raySprites.get(i);
			Vector3 xb = raySprites.get(i + 1);
			Vector3 delta = new Vector3(xb.x - xa.x, xb.y - xa.y, 0);
			Vector3 n = new Vector3(delta);
			n.nor();
			n.crs(0, 0, -1);
			n.mul(0.2f);
			if (ln == null)
				ln = n;

			float s0 = 0.2f+(i * 0.5f / raySprites.size());
			float s1 = 0.2f+((i + 1) * 0.5f / raySprites.size());
			Vector3 a = new Vector3(xa.x - ln.x * s0, xa.y - ln.y * s0, 0);
			Vector3 b = new Vector3(xa.x + ln.x * s0, xa.y + ln.y * s0, 0);
			Vector3 c = new Vector3(xb.x + n.x * s1, xb.y + n.y * s1, 0);
			Vector3 d = new Vector3(xb.x - n.x * s1, xb.y - n.y * s1, 0);
			Color c0 = new Color(1, 0, 0, 0.5f);
			Color c1 = new Color(0, 0, 1, 0.5f);
			s.addVertex(a, c0);
			s.addVertex(b, c1);
			s.addVertex(c, c1);

			s.addVertex(a, c0);
			s.addVertex(c, c1);
			s.addVertex(d, c0);
			ln = n;

		}
		s.init();
		renderer.render(s, Position.NULL, 1, speed, GL10.GL_TRIANGLES);

	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public void drawAfter(IRenderer renderer) {
	}
}
