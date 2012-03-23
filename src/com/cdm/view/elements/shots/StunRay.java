package com.cdm.view.elements.shots;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Level;
import com.cdm.view.enemy.EnemyUnit;

public class StunRay implements AbstractShot {

	private static final float M_PI = 3.1415f;
	private static final float RADIUS = 0.06f;
	private float time;
	private Position fromPos;
	private Level level;
	private float phase = 0.0f;
	private EnemyUnit enemyUnit;
	private Vector3 f = new Vector3();
	private Vector3 t = new Vector3();
	private Vector3 delta = new Vector3();
	private Vector3 dir = new Vector3();
	private Vector3 normal= new Vector3();

	public StunRay(float pTime, Position from, Level plevel, EnemyUnit enemy) {
		time = pTime;
		fromPos = from;
		level = plevel;
		enemy.freeze(pTime);
		enemyUnit = enemy;
	}

	@Override
	public void move(float ptime) {

		time -= ptime;
		if (time < 0) {
			level.removeShot(this);
		} else {
			phase += ptime * 3;
		}

	}

	@Override
	public void draw(IRenderer renderer) {
		f.set(fromPos.toVector());
		 t.set( enemyUnit.getPosition().toVector());

		delta.set(t);
		delta.sub(f);
		dir.set(delta);
		dir.nor();
		normal.set(dir);
		normal.crs(new Vector3(0, 0, 1));
		float len = delta.len();
		float wavelen = 0.6f;
		int samplesPerWave = 10;
		int count = (int) (len / wavelen) * samplesPerWave;
		float opacity = 0.6f;
		if (time < 0.5f)
			opacity = time;

		for (int ph = 0; ph < 3; ph++) {
			List<Vector3> vs = new ArrayList<Vector3>();
			float curPhase = phase * (ph + 1) + ph * M_PI / 2;

			float factor = len / count;
			for (int i = 0; i < count; i++) {
				float radFactor = 1.0f;
				if (i < 5)
					radFactor = i / 5.0f;
				else if (i > count - 5)
					radFactor = (i - count + 5) * 0.7f;

				Vector3 p0 = new Vector3(dir).mul(i * len / count);
				Vector3 p1 = new Vector3(normal).mul((float) Math.sin(i
						/ wavelen / samplesPerWave * 2.0f * M_PI + curPhase)
						* RADIUS * radFactor);
				if (i > count - 5) {
					p0.add(new Vector3(dir).mul((float) Math.sin(radFactor) * 0.05f));
				}

				Vector3 nv = p0.add(p1);
				vs.add(nv);
				if (i > 0 && i < count - 1)
					vs.add(nv);
			}
			Color c = new Color(1, 0.7f, 0.7f, opacity);

			switch (ph) {
			case 0:
				c = new Color(1, 0.7f, 0.7f, opacity);
			case 1:
				c = new Color(1, 0.8f, 0, opacity);
			case 2:
				c = new Color(0.7f, 1.0f, 0, opacity);

			}
			renderer.drawLines(fromPos, vs, 0, c, 2.0f, RefSystem.Level);

		}
	}
}
