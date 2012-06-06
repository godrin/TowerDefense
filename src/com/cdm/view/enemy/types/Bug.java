package com.cdm.view.enemy.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.enemy.GroundMovingEnemy;
import com.cdm.view.enemy.Leg;

public class Bug extends GroundMovingEnemy {
	private static final Color innerColor = new Color(0.8f, 0.8f, 0.0f, 0.8f);
	private static final Color innerColor2 = new Color(0.2f, 0.2f, 0.2f, 0.8f);
	private static final Color outerColor = new Color(0f, 0f, 0.1f, 1.0f);
	private PolySprite sprite = null;
	private Leg leg = new Leg();
	private float delta = 0;

	public Bug(Position pos) {
		super(pos);
		setSize(0.2f);
		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillRectangle(-0.5f, -0.3f, 1.0f, 0.6f, innerColor2); // torso
			sprite.fillRectangle(-1.1f, -0.5f, 0.7f, 1.0f, innerColor); // head
			sprite.fillRectangle(0.3f, -0.6f, 1.7f, 1.2f, innerColor); // end
			sprite.init();
		}
	}

	@Override
	public void move(float time) {
		super.move(time);
		delta += time * 2.0f;

	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.render(sprite, getPosition(), getSize(), 180,
				GL10.GL_TRIANGLES);

		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0f, -0.7f, delta, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0.2f, 0.2f, delta + 1.9f, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0.4f, 0.8f, delta + 3.9f, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0f, -0.7f, delta + 2.0f, -1);
		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0.2f, 0.2f, delta + 2.0f + 1.9f, -1);
		leg.drawLeg(renderer, getPosition(), getAngle(), outerColor, getSize(),
				0.4f, 0.8f, delta + 2.0f + 3.9f, -1);
		/*
		 * renderer.drawPoly(getPosition(), poly, getAngle(), innerColor,
		 * getSize()); getShakingLines().draw(renderer,getPosition(), lines,
		 * getAngle(), outerColor, getSize());
		 * 
		 * chains.drawChain(renderer, getPosition(), getAngle(), outerColor,
		 * getSize());
		 */
		super.draw(renderer);
	}

	@Override
	public int getMoney() {
		return 2;
	}

	@Override
	public int getPoints() {
		return 3;
	}

	@Override
	public int getBonus() {
		return 1;
	}

}
