package com.cdm.view.elements.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.SpriteReader;

public abstract class PlayerUnit extends Unit {

	private PolySprite circle;
	private boolean selected = false;
	private static PolySprite power = null;
	private static PolySprite speed = null;
	private static PolySprite shots = null;
	private static PolySprite distance = null;
	private static PolySprite highlight = null;
	private Position tmpPos = new Position(0, 0, Position.LEVEL_REF);

	public PlayerUnit(Position p) {
		super(p);

		if (power == null) {
			power = SpriteReader
					.read("/com/cdm/view/elements/units/power.sprite");
			speed = SpriteReader
					.read("/com/cdm/view/elements/units/speed.sprite");
			shots = SpriteReader
					.read("/com/cdm/view/elements/units/shots.sprite");
			distance = SpriteReader
					.read("/com/cdm/view/elements/units/distance.sprite");
			highlight = SpriteReader
					.read("/com/cdm/view/elements/units/highlight.sprite");
		}

		circle = new PolySprite();
		circle.fillCircle(0, 0, 1 , new Color(0, 0, 1, 0.3f), new Color(0,
				0, 0, 0), 32);
		circle.init();
	}

	public void draw(IRenderer renderer) {
		if (selected) {
			renderer.render(circle, getPosition(), getMaxDist(), 0.0f,
					GL10.GL_TRIANGLES);
		}
	}

	public void drawAfter(IRenderer renderer) {
		if (selected && false) {
			tmpPos.set(getPosition().x - 1f, getPosition().y,
					Position.LEVEL_REF);

			renderer.render(power, tmpPos, getSize(), 0.0f, GL10.GL_TRIANGLES);
			tmpPos.set(getPosition().x + 1f, getPosition().y,
					Position.LEVEL_REF);
			renderer.render(speed, tmpPos, getSize(), 0.0f, GL10.GL_LINES);

			tmpPos.set(getPosition().x, getPosition().y - 1f,
					Position.LEVEL_REF);
			renderer.render(shots, tmpPos, getSize(), 0.0f, GL10.GL_LINES);
			tmpPos.set(getPosition().x, getPosition().y + 1f,
					Position.LEVEL_REF);
			renderer.render(distance, tmpPos, getSize(), 0.0f, GL10.GL_LINES);

			tmpPos.set(getPosition().x, getPosition().y - 1, Position.LEVEL_REF);
			renderer.render(highlight, tmpPos, getSize(), 0.0f,
					GL10.GL_TRIANGLES);

		}
	}

	protected abstract float getMaxDist();

	public void selected(boolean b) {
		selected = b;

	}
}
