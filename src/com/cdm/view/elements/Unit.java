package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public abstract class Unit implements Element {

	public enum UnitType {
		CANNON, ROCKET_THROWER, STUNNER, PHAZER, ROCKET, EXPLOSION;

		public int getCost() {
			switch (this) {
			case CANNON:
				return 5;
			case ROCKET_THROWER:
				return 20;
			case STUNNER:
				return 10;
			case PHAZER:
				return 120;
			}
			return 0;
		}
	};

	private Position pos = new Position(0, 0, Position.LEVEL_REF),
			oldpos = new Position(1, 1, Position.LEVEL_REF);
	private float size;
	private Level level;
	private int cost;

	public Unit(Position p) {
		pos = p;
		size = 0.4f;
		level = null;
	}

	public abstract void move(float time);

	public abstract void draw(IRenderer renderer);

	@Override
	public void setPosition(Position p) {
		boolean modified = (!oldpos.alignedToGrid().equals(p.alignedToGrid()));
		if (modified && level != null) {
			level.removeMeFromGrid(oldpos, this);
		}
		pos.assignFrom(p);
		if (modified && level != null) {
			level.addMeToGrid(p, this);
		}
		oldpos.assignFrom(pos);

	}

	public Position getPosition() {
		return pos;
	}

	protected void setSize(float f) {
		size = f;
	}

	public void setLevel(Level level) {
		this.level = level;
		level.addMeToGrid(pos, this);
	}

	public Level getLevel() {
		return level;
	}

	public float getSize() {
		return size;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
