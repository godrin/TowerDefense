package com.cdm.view.elements.shots;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.MathTools;
import com.cdm.view.enemy.EnemyUnit;

/**
 * @author godrin
 * 
 *         abstract base class of all shots
 */
public abstract class MovingShot extends PositionedDisplayEffect implements
		Element {

	private Position from;
	protected Position pos;
	private Position target;
	private Position inbetween;
	protected float angle;
	private Level level;
	private Vector3 deltaV = new Vector3();
	private float impact;
	private float allTime = -1;
	private float curTime = 0;
	private EnemyUnit enemy;

	private final static Vector3 a = new Vector3();
	private final static Vector3 b = new Vector3();
	private final static Vector3 c = new Vector3();
	private List<Vector3> raySprites = new ArrayList<Vector3>();

	PolySprite s = new PolySprite();

	public MovingShot(Position pfrom, Position to, Level plevel, float pImpact,
			EnemyUnit penemy) {
		enemy = penemy;
		from = new Position(pfrom);
		pos = new Position(pfrom);

		a.set(penemy.getMovingDirection()).nor();
		a.scale(-1, -1, -1);

		float scale = from.distance(to) / 8.0f;
		a.scale(scale, scale, scale);

		inbetween = new Position(a.x + (to.x * 0.75f + from.x * 0.25f), a.y
				+ (to.y * 0.75f + from.y * 0.25f), Position.LEVEL_REF);
		target = new Position(to);
		level = plevel;
		impact = pImpact;

		angle = MathTools.angle(from.to(to));
		if (false) {
			Vector3 x0 = new Vector3(from.toVector());
			Vector3 x1 = new Vector3(from.toVector());
			Vector3 x2 = new Vector3(target.toVector());
			Vector3 x3 = new Vector3(target.toVector());

			Vector3 n = new Vector3(x2);
			n.sub(x0);
			x0.sub(n);
			x1.add(n);
			x2.sub(n);
			x3.add(n);
			s.fillPoly(new Vector3[] { x0, x1, x2, x3 }, Color.WHITE);
			s.init();
		}
	}

	protected float getSize() {
		return 0.15f;
	}

	public Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public abstract float getSpeed();

	public void move(float time) {
		target = enemy.getPosition();
		if (allTime < 0) {
			allTime = from.lengthTo(target) / getSpeed();
		}
		curTime += time;

		if (curTime > allTime) {
			pos = target;
			EnemyUnit unit = level.getEnemyAt(target);
			if (unit != null)
				unit.wasHitBy(this);
			level.removeShot(this);
			explode();
		} else {
			float g = curTime / allTime;
			float g1 = 1 - g;

			a.set(from.x * g1 + inbetween.x * g, from.y * g1 + inbetween.y * g,
					0);
			b.set(inbetween.x * g1 + target.x * g, inbetween.y * g1 + target.y
					* g, 0);
			c.set(a.x * g1 + b.x * g, a.y * g1 + b.y * g, 0);
			pos.x = c.x;
			pos.y = c.y;

			if (!deltaV.isZero()) {
				a.set(pos.x - deltaV.x, pos.y - deltaV.y, 0);
				angle = MathTools.angle(a);
			}
			deltaV.set(pos.x, pos.y, 0);

		}
		afterMove(pos);
	}

	protected void explode() {
		
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

	protected void drawBurn(IRenderer renderer) {
		if (true) {
			// renderer.render(s, getPosition(), getSize(), 0,
			// GL20.GL_TRIANGLES);
			return;
		}
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

			float s0 = 0.2f + (i * 0.5f / raySprites.size());
			float s1 = 0.2f + ((i + 1) * 0.5f / raySprites.size());
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
		renderer.render(s, Position.NULL, 1, 0, GL10.GL_TRIANGLES);

	}


	public float getImpact() {
		return impact;
	}

	@Override
	public int compareTo(Element arg0) {
		return arg0.hashCode() - this.hashCode();
	}
	
	public Level getLevel() {
		return level;
	}

}
