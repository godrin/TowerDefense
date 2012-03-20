package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.enemy.EnemyUnit;

public class Grid {
	private List<Element>[] cells;
	private int w, h;
	private int endy;

	@SuppressWarnings("unchecked")
	public Grid(int pw, int ph, int pendy) {
		w = pw;
		h = ph;
		cells = new List[w * h];
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

	public List<Element> get(int x, int y) {
		List<Element> l = null;
		if (x >= 0 && x < w && y >= 0 && y < h) {
			if ((l = cells[x + y * w]) == null) {
				l = cells[x + y * w] = new ArrayList<Element>();
			}
		}
		return l;
	}

	public boolean isEndPlace(int x, int y) {
		return (x == w - 1 && y == endy);
	}

	public boolean passable(int x, int y) {
		/*
		if (x == w - 1) {
			return (y == endy);
		}*/
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
