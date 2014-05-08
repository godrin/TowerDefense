package com.cdm.view.enemy.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.enemy.GroundMovingEnemy;
import com.cdm.view.enemy.Leg;

public class Bug extends GroundMovingEnemy {
	private static final Color headColor = new Color(0.8f, 0.8f, 0.0f, 0.8f);
	private static final Color torsoColor = new Color(0.2f, 0.2f, 0.2f, 0.8f);
	private static final Color legColor = new Color(0f, 0f, 0.1f, 1.0f);
	private PolySprite sprite = null;
	private PolySprite outlineSprite = null;
	private Leg leg = new Leg();
	private float delta = 0;
	private Color outlineColor = new Color(1.0f, 0f, 0.1f, 1.0f);
	private Color outerColor = new Color(0, 0, 0, 0);

	public Bug(Position pos) {
		super(pos);
		setSize(0.2f);
		if (sprite == null) {
			sprite = new PolySprite();
			if (false) {
				sprite.fillRectangle(-0.5f, -0.3f, 1.0f, 0.6f, torsoColor); // torso
				sprite.fillRectangle(-1.1f, -0.5f, 0.7f, 1.0f, headColor); // head
				sprite.fillRectangle(0.3f, -0.6f, 1.7f, 1.2f, headColor); // end
			} else {
				sprite.makeNiceRectangle(0.3f, -0.5f, -0.3f, 1.0f, 0.6f,
						torsoColor, outerColor); // torso
				sprite.makeNiceRectangle(0.4f, -1.1f, -0.5f, 0.7f, 1.0f,
						headColor, outerColor); // head
				sprite.makeNiceRectangle(0.9f, 0.3f, -0.6f, 1.7f, 1.2f,
						headColor, outerColor); // end

			}
			sprite.init();
		}
		if (outlineSprite == null) {
			outlineSprite = new PolySprite();
			outlineSprite.makeRectangle(-0.5f, -0.3f, 1.0f, 0.6f, outlineColor);
			outlineSprite.makeRectangle(-1.1f, -0.5f, 0.7f, 1.0f, outlineColor);
			outlineSprite.makeRectangle(0.3f, -0.6f, 1.7f, 1.2f, outlineColor);
			outlineSprite.init();
		}
	}

	@Override
	public void move(float time) {
		super.move(time);
		delta += time * 12.0f * getSpeed();

	}

	@Override
	public void draw(IRenderer renderer) {

		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0f, -0.7f, delta, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0.2f, 0.2f, delta + 1.9f, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0.4f, 0.8f, delta + 3.9f, 1);
		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0f, -0.7f, delta + 2.0f, -1);
		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0.2f, 0.2f, delta + 2.0f + 1.9f, -1);
		leg.drawLeg(renderer, getPosition(), getAngle(), legColor, getSize(),
				0.4f, 0.8f, delta + 2.0f + 3.9f, -1);
		renderer.render(sprite, getPosition(), getSize(), getAngle() - 180,
				GL20.GL_TRIANGLES);

		renderer.render(outlineSprite, getPosition(), getSize(),
				getAngle() - 180, GL20.GL_LINES);
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
