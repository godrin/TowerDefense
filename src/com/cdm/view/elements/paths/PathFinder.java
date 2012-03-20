package com.cdm.view.elements.paths;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.view.elements.Grid;

public class PathFinder {
	public static boolean widthSearch(Grid grid, PathPos from, PathPos to,
			Set<PathPos> ignoreThese) {
		Set<PathPos> stored = new TreeSet<PathPos>();
		List<PathPos> todo = new LinkedList<PathPos>();
		todo.add(from);
		while (todo.size() > 0) {
			PathPos current = todo.get(0);
			todo.remove(0);
			if (current.equals(to))
				return true;
			if (!current.equals(from))
				if (!grid.passable(current.x, current.y)) {
					continue;
				}

			for (PathPos next : current.next()) {
				if (ignoreThese.contains(next))
					continue;
				if (!stored.contains(next)) {
					stored.add(next);
					todo.add(next);
				}
			}
		}
		return false;
	}

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
