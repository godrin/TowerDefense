package com.cdm.view.elements.paths;

import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.view.elements.Grid;

public class PathFinder {
	public static Path findPath(Grid grid, PathPos from, PathPos to) {

		SortedSet<Path> paths = new TreeSet<Path>();

		paths.add(new Path(from, to));

		Path found = null;

		while (paths.size() > 0 && found == null) {
			Path current = paths.first();

			paths.remove(current);
			for (PathPos pos : current.last().next()) {
				if (pos.equals(to)) {
					return current.extendedBy(pos);
				} else if (current.extendableBy(pos)) {
					if (grid.passable(pos.x, pos.y)) {
						paths.add(current.extendedBy(pos));
					}
				}
			}
		}
		return found;
	}
}
