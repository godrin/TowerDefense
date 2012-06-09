package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Level;

public class Campaign {
	private int levelNo = 0;
	private List<Grid> levels;

	public Campaign() {
		levels = new ArrayList<Grid>();
		levels.addAll(CampaignParser.getGrids().values());
	}

	public Level getNextLevel(LevelScreen screen) {

		Level level = null;
		int plevel = levelNo;
		if (plevel >= levels.size()) {
			plevel = levels.size() - 1;
		}

		level = new Level(levels.get(plevel), screen);
		levelNo += 1;
		return level;
	}

	public void restart() {
		levelNo = 0;
	}

	public int getLevelNo() {
		return levelNo;
	}
}
