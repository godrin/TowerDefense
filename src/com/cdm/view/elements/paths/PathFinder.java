package com.cdm.view.elements.paths;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Grid.GridElement;

public class PathFinder {
	private static RoundQueue todoBuffer = null;

	public static boolean breadthSearch(Grid grid, PathPos from, PathPos to,
			PathPos ignoreThis, boolean fastOut) {
		checkTodoBuffer(grid);

		cleanGrid(grid, fastOut);
		boolean found = false;
		todoBuffer.add(to);
		while (todoBuffer.size() > 0) {
			PathPos current = todoBuffer.first();
			todoBuffer.removeFirst();
			
			if (current.equals(from)) {
				found = true;
				if (fastOut)
					return found;
			}
			if (!current.equals(to))
				if (!grid.passable(current.x, current.y)) {
					continue;
				}

			int currentValue = current.value;
			GridElement currentElement = grid.getElement(current.x, current.y);
			if (currentElement != null) {
				if (fastOut)
					currentElement.setTempValue(currentValue);
				else
					currentElement.setDistToEnd(currentValue);
			} else
				currentValue = 0;
			for (PathPos next : current.next()) {
				if (next.equals(ignoreThis))
					continue;
				GridElement ge = grid.getElement(next.x, next.y);
				if (ge != null)
					if (fastOut) {
						if (ge.getTempValue() < 0) {
							todoBuffer.add(next);
						}
					} else {
						if (ge.getDistToEnd() < 0) {
							todoBuffer.add(next);
						}
					}
			}
		}
		return found;
	}

	private static void cleanGrid(Grid grid, boolean fastOut) {
		int x, y;
		for (x = 0; x < grid.getW(); x++)
			for (y = 0; y < grid.getH(); y++) {
				if (fastOut)
					grid.getElement(x, y).setTempValue(-1);
				else
					grid.getElement(x, y).setDistToEnd(-1);
			}
	}

	private static void checkTodoBuffer(Grid grid) {
		int size = grid.getH() * grid.getW();
		if (todoBuffer == null || todoBuffer.capacity() < size) {
			todoBuffer = new RoundQueue(size);
		} else {
			todoBuffer.cleanup();
		}

	}

}
