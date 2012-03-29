package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public class BoxDrawing {
	List<SingleBox> boxes = new ArrayList<SingleBox>();

	public BoxDrawing(Position start, Position end, int gh) {
		for (int x = (int) start.x; x <= (int) end.x; x++) {
			makeBox(x, -1);
			makeBox(x, gh + 1);
		}
		for (int y = 0; y <= gh; y++) {
			if (y == (int) start.y)
				continue;
			makeBox(-1, y);
			makeBox((int) end.x, y);
		}
	}

	private void makeBox(int x, int y) {
		SingleBox box;
		box = new SingleBox();
		box.setPos(new Position(x - 1, y - 1, RefSystem.Level));
		boxes.add(box);
	}

	public void draw(IRenderer renderer) {

		for (int i = 0; i < boxes.size(); i++)
			boxes.get(i).draw(renderer);
	}
}
