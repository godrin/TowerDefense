package com.cdm.view.enemy.types;

import com.badlogic.gdx.math.Matrix4;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.WorldCallback;
import com.cdm.view.enemy.types.Cloud.ShrinkedCallback;

public class BigShip extends SmallShip {

	private static final float SPEED = 0.4f;
	private Cloud cloud;
	private WorldCallback painter;

	public BigShip(Position position) {
		super(position);
		setSize(0.95f);
		setRayspeed(0.7f);
		cloud = new Cloud(2, 0.4f, 64);
		painter = new WorldCallback() {

			@Override
			public void callback(Matrix4 world) {
				cloud.draw(world);
			}
		};
	}

	@Override
	public void drawInLayer(int zLayer, IRenderer renderer) {
		super.drawInLayer(zLayer, renderer);
		if (zLayer == 1) {
			renderer.render(painter, getPosition(), getSize(), angle);
		}

	}

	protected void onDestruction() {
		cloud.shrink(new ShrinkedCallback() {

			@Override
			public void callback() {
				removeMe();

			}
		});

	}

	@Override
	public void move(float time) {
		super.move(time);
		cloud.move(time);
	}

	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

}
