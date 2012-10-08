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
	private static StringBuffer textBuffer = new StringBuffer(400);

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
		// Gdx.graphics.setIcon(null);
		renderer.drawText(0, Gdx.graphics.getHeight(), getMoneyText(),
				moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 112,
				Gdx.graphics.getHeight(), getWaveText(), moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 260,
				Gdx.graphics.getHeight(), getLevelText(), moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 140,
				Gdx.graphics.getHeight() - 30, getEnergyText(), moneyColor);
		renderer.drawText(Gdx.graphics.getWidth() - 140,
				Gdx.graphics.getHeight() - 80, getFPSText(), moneyColor);
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
			renderer.drawText(220, 300, "Game OveR", color, 3);
			renderer.drawText(260, 330, getGameoverText(), moneyColor);
			renderer.drawText(235, 200, "click to go back to menu", moneyColor);
		}
		if (level.getBonus() >= 50 && level.getBonus() <= 52) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 75 && level.getBonus() <= 77) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 100 && level.getBonus() <= 102) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 125 && level.getBonus() <= 127) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 150 && level.getBonus() <= 152) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 175 && level.getBonus() <= 177) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
		if (level.getBonus() >= 200 && level.getBonus() <= 202) {
			renderer.drawText(350, 300, "1 up!", color, 2);
		}
	}

	private CharSequence getGameoverText() {
		textBuffer.setLength(0);
		textBuffer.append("You've got ").append(level.getPoints())
				.append(" Points \nand ").append(level.getBonus())
				.append(" Bonus POiNts");
		return textBuffer;
	}

	private CharSequence getEnergyText() {
		textBuffer.setLength(0);
		textBuffer.append("Energy ").append(level.getHealth());
		return textBuffer;
	}

	private CharSequence getFPSText() {
		textBuffer.setLength(0);
		textBuffer.append("FPS ").append(level.getFps());
		return textBuffer;
	}

	private CharSequence getLevelText() {
		textBuffer.setLength(0);
		textBuffer.append("Level ").append(campaign.getLevelNo());
		return textBuffer;
	}

	private CharSequence getWaveText() {
		textBuffer.setLength(0);
		textBuffer.append("Wave ").append(level.getPlayer().getWaveNo());

		return textBuffer;
	}

	private CharSequence getMoneyText() {
		textBuffer.setLength(0);
		textBuffer.append("$").append(level.getMoney());
		return textBuffer;
	}
}
