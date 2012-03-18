package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.MathTools;

public abstract class AbstractShot implements Element {

	Position pos;
	Position target;
	float angle;
	private Level level;

	public AbstractShot(Position from, Position to, Level plevel) {
		pos = new Position(from);
		target = new Position(to);
		level = plevel;

		angle = MathTools.angle(from.to(to));


	}


	protected float getSize() {
		return 0.3f;
	}

	protected Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public abstract float getSpeed();

	public void move(float time) {
		Vector3 deltaV = pos.to(target);
		float distance = time * getSpeed();
		if (distance > deltaV.len()) {
			pos = target;
			SoundFX.hit.play();
			// FIXME: hit target
			level.removeShot(this);
		} else {

			Vector3 nu = deltaV.nor().mul(distance);
			pos.x += nu.x;
			pos.y += nu.y;
		}

	}

}
