package com.cdm.view.elements.shots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class RocketShot extends MovingShot {

	public static float speed = 1f;
	private PolySprite sprite = null;

	public RocketShot(Position from, Position to, Level plevel) {
		super(from, to, plevel);
		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillRectangle(-0.75f, -0.2f, 1.5f, 0.4f, new Color(0.7f,
					0.5f, 0.2f, 0.9f));
			sprite.fillRectangle(-0.75f, -0.3f, 0.6f, 0.6f, new Color(0.4f,
					0.2f, 0.08f, 0.9f));
			sprite.init();
		}
	}

	public float getSize() {
		return 0.3f;

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.render(sprite, pos, getSize(), angle, GL10.GL_TRIANGLES);
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	public float getLevel() {
		return 3;
	}

}
