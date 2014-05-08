package com.cdm.view.elements.paths;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path> {
	private List<PathPos> positions;
	private PathPos target;

	public Path(List<PathPos> ps, PathPos ptarget) {
		positions = ps;
		target = ptarget;
	}

	public Path(PathPos from, PathPos ptarget) {
		target = ptarget;
		positions = new ArrayList<PathPos>();
		positions.add(from);
	}

	// heuristic
	public Integer value() {
		return positions.size() + last().distanceTo(target);
	}

	public PathPos last() {
		return positions.get(positions.size() - 1);
	}

	public boolean extendableBy(PathPos p) {
		for (PathPos x : positions) {
			if (x.equals(p))
				return false;
		}
		return true;
	}

	public Path extendedBy(PathPos p) {
		List<PathPos> nps = new ArrayList<PathPos>();
		nps.addAll(positions);
		nps.add(p);
		return new Path(nps, target);
	}

	public PathPos second() {
		return positions.get(1);
	}

	@Override
	public int compareTo(Path o) {
		Integer c = value().compareTo(o.value());
		if (c != 0)
			return c;

		for (int i = 0; i < positions.size(); i++) {
			c = positions.get(i).compareTo(o.positions.get(i));
			if (c != 0)
				return c;
		}
		return 0;
	}

	public String toString() {
		String x = "";
		for (PathPos p : positions) {
			x += " " + p;
		}
		return x;
	}
}
