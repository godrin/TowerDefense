package com.cdm.gui;

import com.badlogic.gdx.graphics.Color;
import com.cdm.SString;
import com.cdm.view.IRenderer;
import com.cdm.view.Rectangle;

public class BigButton extends Widget {

	private Color bg = new Color(0.7f, 0.7f, 0.0f, 0.7f);
	private Color bg2 = new Color(0.7f, 0.7f, 0.0f, 0.9f);
	private Color textColor = new Color(0, 0, 0, 0.4f);
	private Rectangle topBox;
	private String caption;
	private SString name;
	private IButtonPressed buttonPressedListener;

	public BigButton(int mx, int my, int w, int h, String text,
			SString buttonName, IButtonPressed listener) {
		setBBox(new Rectangle(mx - w / 2, my - h / 2, w, h));
		topBox = new Rectangle(mx - w / 2 + 5, my - h / 2 + 5, w - 10, 10);
		caption = text;
		buttonPressedListener = listener;
		name = buttonName;

	}

	@Override
	public void draw(IRenderer renderer) {
		Rectangle box = getBBox();
		renderer.fillRect(box.x(), box.y(), box.x2(), box.y2(), bg);
		renderer.fillRect(topBox.x(), topBox.y(), topBox.x2(), topBox.y2(), bg2);
		renderer.drawText((int) box.x() + 5, (int) box.y2(), caption, textColor);
		// rect.draw(renderer);
	}

	@Override
	public boolean opaque(int x, int y) {
		return getBBox().contains(x, y);
	}
	
	@Override
	public void clicked(int x, int y, int pointer, int button) {
		if (buttonPressedListener != null)
			buttonPressedListener.buttonPressed(name);
	}

}
