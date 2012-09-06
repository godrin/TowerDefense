package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class SimplePoly {
	public static class Point {
		public Point(Vector3 vector, Color color) {
			c = color;
			v = vector;
		}

		Vector3 v;
		Color c;
		int i; // attaching to..
	}

	private List<Point> vs = new ArrayList<Point>();

	public void add(Vector3 v, Color c) {
		vs.add(new Point(v, c));
	}

	public Point get(int index) {
		if (index < 0) {
			return vs.get(vs.size() + index);
		}
		if (index >= vs.size())
			return vs.get(index % vs.size());
		return vs.get(vs.size());
	}

	public SimplePoly extruded(float d, Color nuColor) {
		SimplePoly nuPoly = new SimplePoly();
		Vector3 zVec = new Vector3(0, 0, -1);

		for (int current = 0; current < vs.size(); current++) {
			Point prev = get(current - 1);
			Point cur = get(current);

			Point next = get(current + 1);

			Vector3 da = new Vector3(cur.v);
			Vector3 db = new Vector3(next.v);
			da.sub(prev.v);
			db.sub(cur.v);
			if (da.dot(db) < 0) {
				da.nor();
				db.nor();
				da.add(db);
				da.nor();
				da.crs(zVec);
			} else {
				// use da and db to build arc
			}
		}

		return nuPoly;
	}
}
