package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cdm.view.Position;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.enemy.EnemyUnit;

// review1
public class Grid {
	public enum CellType {
		FREE, BLOCK, EMPTY
	};

	public class GridElement {

		private Set<Element> currentElements;
		private int distanceToEnd;
		private int computationValue;
		private int distanceToNextUnit;

		private CellType cellType = CellType.EMPTY;
		int x, y;

		public GridElement(int x, int y) {
			this.x = x;
			this.y = y;
			currentElements = new TreeSet<Element>();
			distanceToEnd = -1;
			distanceToNextUnit = -1;

		}

		public GridElement(GridElement gridElement) {
			this.x = gridElement.x;
			this.y = gridElement.y;
			currentElements = new TreeSet<Element>();
			distanceToEnd = -1;
			distanceToNextUnit = -1;
			cellType = gridElement.cellType;
		}

		public Set<Element> getList() {
			return currentElements;
		}

		public int getTempValue() {
			return computationValue;
		}

		public void setTempValue(int tempValue) {
			this.computationValue = tempValue;
		}

		public int getDistToEnd() {
			return distanceToEnd;
		}

		public int getDistToUnit() {
			return distanceToNextUnit;
		}

		public void setDistToEnd(int distToEnd) {
			this.distanceToEnd = distToEnd;
		}

		public CellType getCellType() {
			return cellType;
		}

		public void setCellType(CellType cellType) {
			this.cellType = cellType;
		}

		public boolean isFree() {
			return cellType.equals(CellType.FREE);
		}

		public void setDistToUnit(int distToUnit) {
			this.distanceToNextUnit = distToUnit;
		}

		public boolean isPassable() {
			if (currentElements == null)
				return false;
			if (!isFree())
				return false;
			// FIXME (?)
			for (Element ce : currentElements) {
				if (!(ce instanceof EnemyUnit))
					return false;
			}
			return true;

		}

		public boolean isEmpty() {
			return currentElements.isEmpty();
		}

		public boolean contains(Unit unit) {
			return currentElements.contains(unit);
		}

		public void remove(Unit unit) {
			if (unit != null)
				currentElements.remove(unit);
		}

		public void add(Unit unit) {
			currentElements.add(unit);
			if (!currentElements.contains(unit))
				throw new RuntimeException("adding failed");
		}

		public EnemyUnit getFirstEnemyUnit() {
			// FIXME (?)
			for (Element u : currentElements) {
				if (u instanceof EnemyUnit) {
					return (EnemyUnit) u;
				}
			}
			return null;
		}

		public Unit getFirstUnit() {
			return getFirstUnit(EnemyUnit.class);
		}

		public PlayerUnit getPlayerUnit() {
			// FIXME (?)
			for (Element u : currentElements) {
				if (u instanceof PlayerUnit) {
					return (PlayerUnit) u;
				}
			}
			return null;
		}

		public Unit getFirstUnit(Class<? extends Unit> klass) {
			// FIXME (?)
			for (Element u : currentElements) {
				if (klass.isAssignableFrom(u.getClass())) {
					return (Unit) u;
				}
			}
			return null;
		}

	}

	private GridElement[] cells;
	private int w, h;
	private List<PathPos> startPositions;
	private List<PathPos> endPositions;

	public Grid(int w2, int h2) {
		w = w2;
		h = h2;
		cells = new GridElement[w * h];
		int x, y;
		for (x = 0; x < w; x++)
			for (y = 0; y < h; y++)
				cells[x + y * w] = new GridElement(x, y);
	}

	public Grid(Grid grid) {
		w = grid.w;
		h = grid.h;

		cells = new GridElement[w * h];
		int x, y;
		for (x = 0; x < w; x++)
			for (y = 0; y < h; y++)
				cells[x + y * w] = new GridElement(grid.cells[x + y * w]);
		startPositions = new ArrayList<PathPos>();
		endPositions = new ArrayList<PathPos>();
		startPositions.addAll(grid.startPositions);
		endPositions.addAll(grid.endPositions);
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public GridElement getElement(int x, int y) {
		if (x >= 0 && y >= 0 && x < w && y < h)
			return cells[x + y * w];
		return null;
	}

	public boolean passable(int x, int y) {
		return getElement(x, y).isPassable();
	}

	public void print() {
		if (true)
			return;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				System.out.print(" " + getElement(x, y).getDistToEnd());
			}
			System.out.println();
		}
		System.out.println();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				System.out.print(" " + getElement(x, y).getDistToUnit());
			}
			System.out.println();
		}

	}

	public GridElement get(Position target) {
		return getElement(Math.round(target.x), Math.round(target.y));
	}

	public List<PathPos> getEnemyStartPositions() {
		return startPositions;
	}

	public List<PathPos> getEnemyEndPosition() {
		return endPositions;
	}

	public void setStartPositions(List<PathPos> startPositions) {
		this.startPositions = startPositions;
	}

	public void setEndPositions(List<PathPos> endPositions) {
		this.endPositions = endPositions;
	}

	public boolean isFree(PathPos p) {
		if (getElement(p) != null)
			return getElement(p).isFree() && !getEnemyEndPosition().contains(p);
		else
			return false;
	}

	private GridElement getElement(PathPos p) {
		return getElement(p.x, p.y);
	}

}
