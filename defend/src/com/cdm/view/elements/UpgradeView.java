package com.cdm.view.elements;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.LevelScreen;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.SpriteReader;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.UnitAction;

public class UpgradeView implements Element {
	private boolean visible = false;
	private Position position = new Position(0, 0, Position.LEVEL_REF);
	private Position tmpPos = new Position(0, 0, Position.LEVEL_REF);
	private PolySprite highlight = SpriteReader
			.read("/com/cdm/view/elements/units/highlight.sprite");
	private UnitAction selectedUpgrade = null;
	private PlayerUnit targetUnit;
	private LevelScreen screen;

	public UpgradeView(LevelScreen screen) {
		this.screen = screen;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (!visible)
			selectedUpgrade = null;
	}

	public PlayerUnit getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(PlayerUnit targetUnit) {
		this.targetUnit = targetUnit;
	}

	@Override
	public int compareTo(Element arg0) {
		return arg0.hashCode() - this.hashCode();
	}

	@Override
	public void draw(IRenderer renderer) {

	}

	@Override
	public void setPosition(Position pos) {
		position.set(pos);
	}

	@Override
	public void move(float time) {

	}

	private interface UpgradeWithPosition {
		void run(UnitAction upgrade, float dx, float dy);
	}

	private void doWithAllUpgrades(UpgradeWithPosition callback) {
		if (visible && targetUnit != null) {

			List<UnitAction> upgrades = targetUnit.getPossibleUpgrades();
			float a = 3.1415f * 2 / upgrades.size();
			int i = 0;
			float radius = 1.0f;
			for (UnitAction u : upgrades) {
				i++;
				float dx = (float) Math.sin(i * a) * radius;
				float dy = (float) Math.cos(i * a) * radius;
				callback.run(u, dx, dy);

			}
		}
	}

	@Override
	public void drawAfter(final IRenderer renderer) {
		doWithAllUpgrades(new UpgradeWithPosition() {

			@Override
			public void run(UnitAction u, float dx, float dy) {
				tmpPos.set(getPosition().x + dx, getPosition().y + dy,
						Position.LEVEL_REF);
				if (targetUnit != null && u!=null) {
					Integer level = targetUnit.getUnitLevel();
					if (level != null) {
						Color color = Color.WHITE;
						if (screen.getLevel().getMoney() < u.getCostForNext()) {
							color = Color.RED;
						}

						renderer.render(u.getSprite(), tmpPos, 0.5f, 0.0f, u
								.getSprite().getPrimitiveType());
						if (selectedUpgrade == u) {
							renderer.render(highlight, tmpPos, 0.5f, 0.0f,
									GL10.GL_TRIANGLES);
						}
						renderer.drawText(tmpPos.to(Position.SCREEN_REF),
								level.toString(), color);
						tmpPos.x += dx - 0.25f;
						tmpPos.y += dy + 0.25f;
						renderer.drawText(tmpPos.to(Position.SCREEN_REF), "$"
								+ u.getCostForNext(), color);
					}
				}
			}
		});
	}

	private Position getPosition() {
		return position;
	}

	public void hover(final Position dragPosition) {
		selectedUpgrade = null;
		doWithAllUpgrades(new UpgradeWithPosition() {

			@Override
			public void run(UnitAction u, float dx, float dy) {
				tmpPos.set(getPosition().x + dx, getPosition().y + dy,
						Position.LEVEL_REF);
				float dx2 = dragPosition.x - tmpPos.x;
				float dy2 = dragPosition.y - tmpPos.y;
				if (dx2 * dx2 + dy2 * dy2 < 0.5f
						&& screen.getLevel().getMoney() >= u.getCostForNext()) {
					selectedUpgrade = u;
				}

			}
		});

	}

	public UnitAction getSelectedUpgrade() {
		return selectedUpgrade;
	}

}
