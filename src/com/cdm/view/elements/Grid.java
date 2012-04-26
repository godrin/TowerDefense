package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.enemy.EnemyUnit;

public class Grid {
	public enum CellType {
		FREE, BLOCK, EMPTY
	};

	public class GridElement {

		private List<Element> e;
		private int distToEnd;
		private int tempValue;
		private int distToUnit;
		
		private CellType cellType = CellType.FREE;

		public GridElement() {
			e = new ArrayList<Element>();
			distToEnd = -1;
			distToUnit = -1;
		}

		public List<Element> getList() {
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
		
		public void setDistToUnit(int distToUnit){
			this.distToUnit = distToUnit;
		}
		
	}

	private GridElement[] cells;
	private int w, h;
	private int endy;

	public Grid(int pw, int ph, int pendy) {
		w = pw;
		h = ph;
		cells = new GridElement[w * h];
		int x, y;
		for (x = 0; x < w; x++)
			for (y = 0; y < h; y++)
				cells[x + y * w] = new GridElement();
		endy = pendy;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int endY() {
		return endy;
	}

	public GridElement getElement(int x, int y) {
		if (x >= 0 && y >= 0 && x < w && y < h)
			return cells[x + y * w];
		return null;
	}

	public List<Element> get(int x, int y) {
		GridElement ge = getElement(x, y);
		if (ge != null)
			return ge.getList();
		return null;
	}

	public boolean isEndPlace(int x, int y) {
		return (x == w - 1 && y == endy);
	}

	public boolean passable(int x, int y) {
		List<Element> l = get(x, y);
		if (l == null)
			return false;
		if (!getElement(x, y).isFree())
			return false;
		for (Element e : l) {
			if (!(e instanceof EnemyUnit))
				return false;
		}
		return true;
	}

	public void print() {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				System.out.print(" " + getElement(x, y).getDistToEnd());
			}
			System.out.println();
		}
	}
}
