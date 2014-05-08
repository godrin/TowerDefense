package com.cdm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.view.IRenderer;
import com.cdm.view.LevelScreen;
import com.cdm.view.Position;
import com.cdm.view.elements.Elements;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.units.Unit;

// review1
public class UnitTypeButton extends Button {

	private Unit.UnitType type;
	private IUnitTypeSelected listener;
	private Unit caption;
	private Color moneyColor = new Color(1, 1, 1, 1);
	private Level level;
	private int cost = 2;
	private Position pos = getPosition();
	private LevelScreen levelScreen;
	
	public UnitTypeButton(int px, int py, int pradius, Unit.UnitType ptype,
			Level plevel,LevelScreen screen) {
		super(px, py, pradius);
		levelScreen=screen;
		type = ptype;
		level = plevel;
		caption = Elements.getElementBy(ptype, new Position(px, py,
				Position.BUTTON_REF));
		pos.y += 55;
		pos.x -= 20;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void touchDown(int x, int y, int pointer, int button) {
		if(level.getMoney() < cost)
			return;

		super.touchDown(x, y, pointer, button);
		if (listener != null && isEnabled())  {
			listener.unitTypeSelected(type, new Position(x, y,
					Position.SCREEN_REF), cost);
			Gdx.input.vibrate(10);
		}
	}

	public IUnitTypeSelected getListener() {
		return listener;
	}

	public void setListener(IUnitTypeSelected listener) {
		this.listener = listener;
	}

	private static StringBuilder textBuilder=new StringBuilder(); 
	@Override
	public void draw(IRenderer renderer) {
		setEnabled(level.getMoney() >= cost);
		if(level.getMoney() < cost)
			return;
		if(levelScreen.isDragging())
			return;
		
		super.draw(renderer);
		Position.BUTTON_REF.setScale(getRadius() * 2);
		caption.draw(renderer);

		// FIXME (?)
		
		textBuilder.setLength(0);
		textBuilder.append("$").append(cost);
		renderer.drawText(pos, textBuilder, moneyColor);
	}

}
