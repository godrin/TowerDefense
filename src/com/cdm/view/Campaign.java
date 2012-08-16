package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.PlayerState;

public class Campaign {
	private int levelNo = 0;
	private List<Grid> levels;
	PlayerState playerState = new PlayerState();

	public Campaign(String campaignFile) {
		levels = new ArrayList<Grid>();
		levels.addAll(CampaignParser.getGrids(campaignFile).values());
	}

	public Level getNextLevel(LevelScreen screen) {

		Level level = null;
		int plevel = levelNo;
		if (plevel >= levels.size()) {
			plevel = levels.size() - 1;
		}
		level = new Level(new Grid(levels.get(plevel)), screen, playerState);
		levelNo += 1;
		if (levelNo>= 11) 
			levelNo=0;
		level.setLevelNo(levelNo);
		return level;
	}

	public void restart() {
		levelNo =0;
		playerState = new PlayerState();
	}

	public int getLevelNo() {
		return levelNo;
	}
}
