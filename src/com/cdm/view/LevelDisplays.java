package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.cdm.view.elements.Level;

public class LevelDisplays {
	private Level level;
	private Campaign campaign;
	private Color moneyColor = new Color(0.7f, 0.7f, 1.0f, 1.0f);
	private Color color = new Color(1, 1, 1, 1);
	private boolean up = false;

	public LevelDisplays() {

	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	void draw(IRenderer renderer) {
		Gdx.graphics.setIcon(null);
		renderer.drawText(0, Gdx.graphics.getHeight(),
				"$" + Integer.toString(level.getMoney()), moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 112,
				Gdx.graphics.getHeight(),
				"Wave " + Integer.toString(level.getPlayer().getLevelNo()),
				moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 260,
				Gdx.graphics.getHeight(),
				"Level " + Integer.toString(campaign.getLevelNo()), moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 140,
				Gdx.graphics.getHeight() - 30,
				"Energy " + Integer.toString(level.getHealth()), moneyColor);
		if (up) {
			if (color.a >= 0.01f) {
				color.a -= 0.01f;
			} else {
				up = false;
			}
		} else {
			if (color.a <= 0.99) {
				color.a += 0.01f;

			} else {
				up = true;
			}
		}

		if (level.gameover()) {
			renderer.drawText(220, 300, "Game OveR", color,3);
			renderer.drawText(260, 330,
					"You've got " + Integer.toString(level.getPoints())
							+ " Points", moneyColor);
			renderer.drawText(235, 200, "click to go back to menu", moneyColor);
		}
		if (level.getBonus() >= 100 && level.getBonus() <= 102) {
			Renderer.font.setScale(2f);
			renderer.drawText(350, 300, "1 up!", color);
			Renderer.font.setScale(1f);
		}
		if (level.getBonus() >= 250 && level.getBonus() <= 252) {
			Renderer.font.setScale(2f);
			renderer.drawText(350, 300, "1 up!", color);
			Renderer.font.setScale(1f);
		}
		if (level.getBonus() >= 500 && level.getBonus() <= 502) {
			Renderer.font.setScale(2f);
			renderer.drawText(350, 300, "1 up!", color);
			Renderer.font.setScale(1f);
		}
	}
}
