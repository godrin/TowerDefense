package com.cdm.view.elements.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.MathTools;
import com.cdm.view.elements.shots.RocketShot;
import com.cdm.view.enemy.EnemyUnit;

public class RocketLauncher extends RotatingUnit implements Element {

	private static PolySprite sprite = null;
	private static PolySprite lines = null;
	float shotFrequency = 5.0f;
	float lastShot = 0.0f;
	private float maxDist = 4.5f;
	private double startingRadius = 0.01f;
	private int impact = 5;
	Color innerColor = new Color(0, 0, 0.6f, 1.0f);
	Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);

	Color groundColor = new Color(0, 0, 0.6f, 1.0f);
	Color plateColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
	Color highlight2Color = new Color(0.2f, 0.2f, 0.8f, 1.0f);
	Color highlightColor = new Color(0.2f, 0.2f, 0.5f, 1.0f);
	Color borderColor = new Color(0.6f, 0.6f, 1.0f, 1.0f);

	public RocketLauncher(Position p) {
		super(p);
		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillCircle(0, 0, 0.95f, groundColor, 16);
			sprite.fillRectangle(-0.7f, -0.7f, 1.4f, 1.4f, plateColor);
			sprite.fillRectangle(-0.5f, -0.5f, 1.0f, 0.2f, highlightColor);
			sprite.fillRectangle(-0.5f, 0.3f, 1.0f, 0.2f, highlightColor);
			sprite.fillRectangle(-0.5f, 0.1f, 1.0f, 0.1f, highlight2Color);
			sprite.fillRectangle(-0.5f, -0.2f, 1.0f, 0.1f, highlight2Color);
			sprite.init();

			lines = new PolySprite();
			lines.makeRectangle(-0.7f, -0.7f, 1.4f, 1.4f, borderColor);
			lines.init();
		}
	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);
		renderer.render(sprite, getPosition(), getSize(), getAngle(),
				GL10.GL_TRIANGLES);

		renderer.render(lines, getPosition(), getSize(), getAngle(),
				GL10.GL_LINES);

	}

	@Override
	protected EnemyUnit getEnemy() {
		EnemyUnit u = getLevel().getNextEnemy(getPosition());
		if (u == null)
			return null;
		if (getPosition().distance(u.getPosition()) > maxDist)
			return null;
		return u;
	}

	@Override
	public void move(float time) {
		super.move(time);
		EnemyUnit enemy = getEnemy();
		lastShot += time;
		if (ableToShoot)
			shoot(enemy);
	}

	protected float getMaxDist() {
		return maxDist;
	}

	private void shoot(EnemyUnit enemy) {
		if (enemy != null) {

			if (lastShot > shotFrequency) {
				lastShot = 0.0f;
				Position startingPos = new Position(getPosition());
				float angle = getAngle();
				startingPos.x -= Math.cos(angle * MathTools.M_PI / 180.0f)
						* startingRadius;
				startingPos.y -= Math.sin(angle * MathTools.M_PI / 180.0f)
						* startingRadius;
				getLevel().addShot(
						new RocketShot(startingPos, anticipatePosition(
								startingPos, enemy, RocketShot.speed),
								getLevel(), impact));
				SoundFX.play(Type.SHOT);

			}

		}
	}

	@Override
	public int getZLayer() {
		return 0;
	}
	@Override
	protected void setValue(String key, Float value) {
		if ("distance".equals(key))
			maxDist = value;
		else if ("frequency".equals(key))
			shotFrequency = value;
		else if ("impact".equals(key))
			impact = value.intValue();
	}

}
