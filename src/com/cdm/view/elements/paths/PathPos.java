package com.cdm.view.elements.paths;

import java.util.Arrays;
import java.util.List;

import com.cdm.view.Position;

public class PathPos implements Comparable<PathPos> {
	public int x, y;
	public int value;

	public PathPos(Position pos) {
		x = (int) pos.x;
		y = (int) pos.y;
		value = 0;
	}

	public PathPos(int _x, int _y, int _v) {
		x = _x;
		y = _y;
		value = _v;
	}

	public PathPos() {
		x = y = value = -1;
	}

	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public boolean equals(PathPos p) {
		if(p==null)
			return false;
		return x == p.x && y == p.y;
	}

	public List<PathPos> next() {
		return Arrays.asList(new PathPos[] { new PathPos(x + 1, y, value + 1),
				new PathPos(x - 1, y, value + 1),
				new PathPos(x, y + 1, value + 1),
				new PathPos(x, y - 1, value + 1) });
	}

	@Override
	public int compareTo(PathPos o) {
		if (x < o.x)
			return -1;
		if (x > o.x)
			return 1;
		if (y < o.y)
			return -1;
		if (y > o.y)
			return 1;
		return 0;
	}

	public int distanceTo(PathPos target) {
		float dx = x - target.x;
		float dy = y - target.y;
		int val = (int) Math.sqrt(dx * dx + dy * dy);
		return val;
	}

	public void assign(PathPos t) {
		x = t.x;
		y = t.y;
		value = t.value;

	}

}
