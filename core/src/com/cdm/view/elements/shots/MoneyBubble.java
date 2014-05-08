package com.cdm.view.elements.shots;

import com.badlogic.gdx.graphics.Color;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.elements.Level;

public class MoneyBubble extends PositionedDisplayEffect {

	private static final float TIME = 4;
	private static final float SPEED = 32;
	private static final float FADING_SPEED = 1 / TIME;
	private static final float SCALING_SPEED = 0.75f / TIME;
	private Position pos;
	private Color color = new Color(1, 1, 1, 1);
	private float accTime = 0;
	private Level level;
	private String text;
	private float scale = 3;

	public MoneyBubble(int money, Position p, Level pLevel) {
		level = pLevel;
		text = Integer.toString(money);
		pos = new Position(p.to(Position.SCREEN_REF));
		pos.y += 32;
	}

	@Override
	public void move(float time) {
		pos.y += time * SPEED;
		accTime += time;
		color.a -= time * FADING_SPEED;
		scale -= time * SCALING_SPEED;
		if (accTime > TIME) {
			level.removeShot(this);
		}
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.drawText(pos.x, pos.y, text, color, scale);
	}

	@Override
	public Position getPosition() {
		return pos;
	}
}
