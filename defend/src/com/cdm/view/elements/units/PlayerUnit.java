package com.cdm.view.elements.units;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.units.upgrades.SellAction;
import com.cdm.view.elements.units.upgrades.UpgradeConfig;

public abstract class PlayerUnit extends Unit {

	private PolySprite circle;
	private boolean selected = false;
	private List<UnitAction> possibleUpgrades = new ArrayList<UnitAction>();
	private int currentValue;

	public PlayerUnit(Position p) {
		super(p);

		circle = new PolySprite();
		circle.fillCircle(0, 0, 1, new Color(0, 0, 1, 0.3f), new Color(0, 0, 0,
				0), 32);
		circle.init();
	}

	public void init() {
		loadConfig();
	}

	public void draw(IRenderer renderer) {
		if (selected) {
			renderer.render(circle, getPosition(), getMaxDist(), 0.0f,
					GL10.GL_TRIANGLES);
		}
	}

	protected abstract float getMaxDist();

	public void selected(boolean b) {
		selected = b;
	}

	public void loadConfig() {
		String unitName = getClass().getSimpleName();
		UnitAction u = UpgradeConfig.getUpgrade(unitName, 0);
		apply(u);
		UnitAction next = u.getNextUprade();
		if (next != null)
			possibleUpgrades.add(next);

		possibleUpgrades.add(new SellAction(this));

	}

	private void apply(UnitAction upgrade) {
		upgrade.apply(this);
		// setValue(upgrade.valueName(), upgrade.value());
	}

	public void incLevel(UnitAction upgrade) {
		if (upgrade != null) {
			UnitAction next = upgrade.getNextUprade();

			apply(upgrade);
			possibleUpgrades.remove(upgrade);
			if (next != null) {
				possibleUpgrades.add(0, next);
			}
		}
	}

	protected abstract void setValue(String key, Float value);

	@Override
	public void drawAfter(IRenderer renderer) {

	}

	public List<UnitAction> getPossibleUpgrades() {
		return possibleUpgrades;
	}

	public void setValue(int round) {
		currentValue = round;

	}

	public Integer getCurrentValue() {
		return currentValue;
	}

}