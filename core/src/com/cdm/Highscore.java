package com.cdm;

import java.util.List;

import com.cdm.HighscoreServer.Entry;
import com.cdm.HighscoreServer.HighscoreAccessException;

// review1
public interface Highscore {

	public abstract List<Entry> post(Entry entry)
			throws HighscoreAccessException;

	public abstract List<Entry> read() throws HighscoreAccessException;

}
