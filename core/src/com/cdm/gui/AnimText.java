package com.cdm.gui;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.cdm.view.IRenderer;

// review1
public class AnimText extends Widget {

	private List<String> texts;
	private float time = 0;

	public AnimText(int mx, int my, int w, int h, List<String> ptexts) {
		texts = ptexts;

		setPos(mx, my, w, h);
	}

	public void setPos(float mx, float my, float w, float h) {
		setBBox(mx - w / 2, my - h / 2, w, h);
	}

	@Override
	public void draw(IRenderer renderer) {
		float i = time * 0.7f;
		i = i % texts.size();
		int t = (int) i;
		float delta = i - t - 0.5f;
		float x=300-delta*delta*Math.signum(delta)*2600;
		renderer.drawText(x, 50.0f, texts.get(t), Color.WHITE,  2.f);
	}
	public void drawOld(IRenderer renderer) {
		float i = time * 0.7f;
		i = i % texts.size();
		int t = (int) i;
		float delta = i - t - 0.5f;
		float size = 0;
		if (delta < 0) {
			if (delta < -0.4f) {
				size = 0;
			} else if (delta < -0.2f) {
				size = (delta + 0.4f) / 0.2f;
			} else
				size = 1;
		} else {
			if (delta > 0.4f) {
				size = 0;
			} else if (delta > 0.2f) {
				size = (0.4f - delta) / 0.2f;
			} else
				size = 1;
		}
		renderer.drawText(50, 50, texts.get(t), Color.WHITE, size * 2.f);
	}

	@Override
	public boolean opaque(int x, int y) {
		return false;
	}

	public void addTime(float t) {
		time += t * 0.2f;
	}

}
