package com.cdm.view.elements;

import java.util.List;

import com.badlogic.gdx.graphics.GL10;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.SpriteReader;
import com.cdm.view.elements.units.Upgrade;
import com.cdm.view.elements.units.Upgrades;

public class UpgradeView implements Element {
	private boolean visible = false;
	private Position position = new Position(0, 0, Position.LEVEL_REF);
	private Position tmpPos = new Position(0, 0, Position.LEVEL_REF);
	private List<Upgrade> upgrades = Upgrades.UPGRADES;
	private PolySprite highlight = SpriteReader
			.read("/com/cdm/view/elements/units/highlight.sprite");
	private Upgrade selectedUpgrade = null;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (!visible)
			selectedUpgrade = null;
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

	@Override
	public void drawAfter(IRenderer renderer) {
		if (visible) {
			for (Upgrade u : upgrades) {
				tmpPos.set(getPosition().x + u.menuPos().x,
						getPosition().y + u.menuPos().y, Position.LEVEL_REF);

				renderer.render(u.getSprite(), tmpPos, 0.5f, 0.0f, u.viewType());
				if (selectedUpgrade == u) {
					renderer.render(highlight, tmpPos, 0.5f, 0.0f,
							GL10.GL_TRIANGLES);
				}
			}
		}
	}

	private Position getPosition() {
		return position;
	}

	public void hover(Position dragPosition) {
		for (Upgrade u : upgrades) {
			float dx = dragPosition.x - (getPosition().x + u.menuPos().x);
			float dy = dragPosition.y - (getPosition().y + u.menuPos().y);
			if (dx * dx + dy * dy < 1) {
				selectedUpgrade = u;
			}
		}
	}

}
