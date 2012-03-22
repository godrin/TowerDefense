package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.enemy.EnemyUnit;

public class Grid {
	public class GridElement {
		private List<Element> e;
		private int distToEnd;

		public GridElement() {
			e = new ArrayList<Element>();
			distToEnd = -1;
		}

		public List<Element> getList() {
			return e;
		}

		public int getDistToEnd() {
			return distToEnd;
		}

		public void setDistToEnd(int distToEnd) {
			this.distToEnd = distToEnd;
		}

	}

	private GridElement[] cells;
	private int w, h;
	private int endy;

	@SuppressWarnings("unchecked")
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
		GridElement ge=getElement(x, y);
		if(ge!=null)
			return ge.getList();
		return null;
	}

	public boolean isEndPlace(int x, int y) {
		return (x == w - 1 && y == endy);
	}

	public boolean passable(int x, int y) {
		/*
		 * if (x == w - 1) { return (y == endy); }
		 */
		List<Element> l = get(x, y);
		if (l == null)
			return false;
		for (Element e : l) {
			if (!(e instanceof EnemyUnit))
				return false;
		}
		return true;
	}
}
