package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.cdm.Game;
import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.PlayerState;

public class Campaign {
	private int levelNo = 0;
	private List<Grid> levels;
	PlayerState playerState;

	public Campaign(String campaignFile, Game pGame) {
		playerState = new PlayerState(pGame);
		levels = new ArrayList<Grid>();
		levels.addAll(CampaignParser.getGrids(campaignFile).values());
	}

	public Level getNextLevel(Game game, LevelScreen screen) {

		Level level = null;
		int plevel = levelNo;
		if (plevel >= levels.size()) {
			plevel = levels.size() - 1;
		}
		level = new Level(game, new Grid(levels.get(plevel)), screen,
				playerState);
		levelNo += 1;
		SoundFX.play(Type.LEVEL2);
		if (levelNo >= 11)
			levelNo = 0;
		level.setLevelNo(levelNo);
		return level;
	}

	public void restart(Game game) {
		levelNo = 0;
		playerState = new PlayerState(game);
	}

	public int getLevelNo() {
		return levelNo;
	}
}
