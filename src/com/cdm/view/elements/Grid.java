package com.cdm.view.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cdm.view.Position;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.enemy.EnemyUnit;

public class Grid {
	public enum CellType {
		FREE, BLOCK, EMPTY
	};

	public class GridElement {

		private Set<Element> e;
		private int distToEnd;
		private int tempValue;
		private int distToUnit;

		private CellType cellType = CellType.FREE;
		int x, y;

		public GridElement(int x, int y) {
			this.x = x;
			this.y = y;
			e = new TreeSet<Element>();
			distToEnd = -1;
			distToUnit = -1;
		}

		public Set<Element> getList() {
			return e;
		}

		public int getTempValue() {
			return tempValue;
		}

		public void setTempValue(int tempValue) {
			this.tempValue = tempValue;
		}

		public int getDistToEnd() {
			return distToEnd;
		}

		public int getDistToUnit() {
			return distToUnit;
		}

		public void setDistToEnd(int distToEnd) {
			this.distToEnd = distToEnd;
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
			this.distToUnit = distToUnit;
		}

		public boolean isPassable() {
			if (e == null)
				return false;
			if (!isFree())
				return false;
			for (Element ce : e) {
				if (!(ce instanceof EnemyUnit))
					return false;
			}
			return true;

		}

		public boolean isEmpty() {
			return e.isEmpty();
		}

		public boolean contains(Unit unit) {
			return e.contains(unit);
		}

		public void remove(Unit unit) {
			if (unit != null)
				e.remove(unit);
		}

		public void add(Unit unit) {
			e.add(unit);
			if (!e.contains(unit))
				throw new RuntimeException("adding failed");
		}

		public EnemyUnit getFirstEnemyUnit() {
			for (Element u : e) {
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
			for (Element u : e) {
				if (u instanceof PlayerUnit) {
					return (PlayerUnit) u;
				}
			}
			return null;
		}

		public Unit getFirstUnit(Class<? extends Unit> klass) {
			for (Element u : e) {
				if (klass.isAssignableFrom(u.getClass())) {
					return (Unit) u;
				}
			}
			return null;
		}

	}

	private GridElement[] cells;
	private int w, h;
	private int endy;
	private List<PathPos> startPositions;
	private List<PathPos> endPositions;

	public int getEndy() {
		return endy;
	}

	public void setEndy(int endy) {
		this.endy = endy;
	}

	public Grid(int w2, int h2) {
		w = w2;
		h = h2;
		cells = new GridElement[w * h];
		int x, y;
		for (x = 0; x < w; x++)
			for (y = 0; y < h; y++)
				cells[x + y * w] = new GridElement(x, y);
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

	public boolean isEndPlace(int x, int y) {
		return (x == w - 1 && y == endy);
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

}
