package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Grid.CellType;
import com.cdm.view.elements.Grid.GridElement;

public class GridDrawing {
	private Grid grid;
	List<Element> es = new ArrayList<Element>();

	public GridDrawing(Grid pgrid) {
		grid = pgrid;
		for (int y = 0; y < grid.getH(); y++)
			for (int x = 0; x < grid.getW(); x++) {
				GridElement e = grid.getElement(x, y);

				Element de = null;
				if (e.getCellType().equals(CellType.FREE)) {
					BackgroundElement be = new BackgroundElement(new Position(
							x, y, Position.LEVEL_REF),e);
					if (x == 2 && y == 3)
						be.startRotation();
					de = be;
				} else if (e.getCellType().equals(CellType.BLOCK)) {
					SingleBox sb = new SingleBox();
					sb.setPosition(new Position(x, y-1, Position.LEVEL_REF));
					de = sb;
				}
				if (de != null)
					es.add(de);
			}
	}

	public void draw(IRenderer renderer) {
		for (Element e : es) {
			e.draw(renderer);
		}
	}

	public void move(float time) {
		for (Element e : es) {
			e.move(time);
		}

	}

}
