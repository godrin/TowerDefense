package com.cdm.gui;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;

public class WidgetContainer extends Widget {
	private List<Widget> widgets = new ArrayList<Widget>();

	@Override
	public void draw(IRenderer renderer) {
		for (int i = 0; i < widgets.size(); i++) {
			widgets.get(i).draw(renderer);
		}
	}

	@Override
	public void addTime(float t) {
		super.addTime(t);
		for (int i = 0; i < widgets.size(); i++) {
			widgets.get(i).addTime(t);
		}
	}

	public void add(Widget widget) {
		if (widgets.size() == 0)
			setBBox(widget.getBBox());
		else
			getBBox().add(widget.getBBox());
		widgets.add(widget);
	}

	public void dispose(Widget restartButton) {
		widgets.remove(restartButton);
	}

	@Override
	public boolean opaque(int x, int y) {
		for (int i = 0; i < widgets.size(); i++) {
			Widget w = widgets.get(i);
			if (w.getBBox().contains(x, y))
				if (w.opaque(x, y))
					return true;
		}
		return false;
	}

	public void touchDown(int x, int y, int pointer, int button) {
		for (Widget w : widgetsOf(x, y)) {
			w.touchDown(x, y, pointer, button);
		}
	}

	public void touchUp(int x, int y, int pointer, int button) {
		for (Widget w : widgetsOf(x, y)) {
			w.touchUp(x, y, pointer, button);
		}

	}

	public void touchDrag(int x, int y, int pointer, int button) {
		for (Widget w : widgetsOf(x, y)) {
			w.touchDrag(x, y, pointer, button);
		}

	}

	//FIXME (?)
	private List<Widget> widgetsOf(int x, int y) {
		List<Widget> ws = new ArrayList<Widget>();
		for (Widget w : widgets) {
			if (w.getBBox().contains(x, y) || w.wantsOuterEvents())
				ws.add(w);
		}
		return ws;
	}

	public void clear() {
		widgets.clear();
	}

}
