package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class BoxDrawing {
	List<SingleBox> boxes = new ArrayList<SingleBox>();
	List<Element> es = new ArrayList<Element>();

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

		for (int x = 0; x < end.x; x++) {
			for (int y = 0; y <= gh; y++) {
				BackgroundElement e = new BackgroundElement(new Position(x, y,
						Position.LEVEL_REF));
				if (x == 2 && y == 3)
					e.startRotation();
				es.add(e);
			}
		}
		es.add(new BackgroundElement(start));
		es.add(new BackgroundElement(end));
	}

	private void makeBox(int x, int y) {
		SingleBox box;
		box = new SingleBox();
		box.setPos(new Position(x - 1, y - 1, Position.LEVEL_REF));
		boxes.add(box);
	}

	public void move(float time) {
		for (int i = 0; i < boxes.size(); i++)
			boxes.get(i).move(time);
		for (int i = 0; i < es.size(); i++)
			es.get(i).move(time);

	}

	public void draw(IRenderer renderer) {

		for (int i = 0; i < boxes.size(); i++)
			boxes.get(i).draw(renderer);
		for (int i = 0; i < es.size(); i++)
			es.get(i).draw(renderer);
	}
}
