package com.cdm.view.elements.paths;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Grid.GridElement;

public class PathFinder {
	private static PathPos[] todoBuffer;

	public static boolean widthSearch(Grid grid, PathPos from, PathPos to,
			PathPos ignoreThis, boolean fastOut) {
		checkTodoBuffer(grid);
		cleanGrid(grid);

		// Set<PathPos> stored = new TreeSet<PathPos>();
		List<PathPos> todo = new LinkedList<PathPos>();
		boolean found = false;
		todo.add(to);
		// grid.getElement(to.x, to.y).setDistToEnd(0);
		while (todo.size() > 0) {
			PathPos current = todo.get(0);
			System.out.println(current);
			todo.remove(0);
			if (current.equals(from)) {
				found = true;
				if (fastOut)
					return found;
			}
			if (!current.equals(to))
				if (!grid.passable(current.x, current.y)) {
					continue;
				}
			int currentValue=-1;
			GridElement currentElement = grid.getElement(current.x, current.y);
			if (currentElement != null)
				currentValue = currentElement.getDistToEnd() + 1;
			else
				currentValue = 0;
			for (PathPos next : current.next()) {
				if (next.equals(ignoreThis))
					continue;
				GridElement ge = grid.getElement(next.x, next.y);
				if (ge != null)
					if (ge.getDistToEnd() < 0) {
						// if (!stored.contains(next)) {
						// stored.add(next);
						ge.setDistToEnd(currentValue);
						todo.add(next);
					}
			}
		}
		return found;
	}

	private static void cleanGrid(Grid grid) {
		int x, y;
		for (x = 0; x < grid.getW(); x++)
			for (y = 0; y < grid.getH(); y++)
				grid.getElement(x, y).setDistToEnd(-1);
	}

	private static void checkTodoBuffer(Grid grid) {
		int size = grid.getH() * grid.getW();
		if (todoBuffer == null || todoBuffer.length < size) {
			todoBuffer = new PathPos[size];
			int i;
			for (i = 0; i < size; i++) {
				todoBuffer[i] = new PathPos();
			}
		}

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
