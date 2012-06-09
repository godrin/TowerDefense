package com.cdm.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Grid.CellType;
import com.cdm.view.elements.paths.PathPos;

public class CampaignParser {
	public static Map<Integer, Grid> getGrids(String campaignFile) {
		Map<Integer, Grid> grids = new TreeMap<Integer, Grid>();

		Integer level = null;

		InputStream is = CampaignParser.class
				.getResourceAsStream(campaignFile);

		InputStreamReader isr = new InputStreamReader(is);

		LineNumberReader lir = new LineNumberReader(isr);
		String line;

		List<String> gridBuffer = new ArrayList<String>();

		try {
			while ((line = lir.readLine()) != null) {
				if (line.matches("^=.*")) {
					if (gridBuffer.size() > 0 && level != null) {
						grids.put(level, parseLevel(gridBuffer));
					}
					gridBuffer.clear();
					level = Integer.parseInt(line.substring(1));

				} else {
					if (line.matches("^#.*")) {
						// comment - ignore
					} else if(line.length()>0){
						gridBuffer.add(line);
					}
				}
				System.out.println(line);
			}
		} catch (IOException e) {
		}

		if (gridBuffer.size() > 0 && level != null) {
			grids.put(level, parseLevel(gridBuffer));
		}

		return grids;

	}

	private static Grid parseLevel(List<String> gridBuffer) {
		System.out.println("PARSING....");
		int h = gridBuffer.size();
		int w = 0;
		List<PathPos> start = new ArrayList<PathPos>();
		List<PathPos> end = new ArrayList<PathPos>();
		// get size of grid
		for (String l : gridBuffer) {
			if (w < l.length())
				w = l.length();
		}
		Grid g = new Grid(w, h);
		int x, y = h - 1;
		for (String l : gridBuffer) {
			for (x = 0; x < l.length(); x++) {
				char c = l.charAt(x);
				switch (c) {
				case 'S':
					start.add(new PathPos(x, y, 0));
					g.getElement(x, y).setCellType(CellType.FREE);
					break;
				case 'E':
					end.add(new PathPos(x, y, 0));
					g.getElement(x, y).setCellType(CellType.FREE);
					break;
				case '0':
					g.getElement(x, y).setCellType(CellType.FREE);
					break;
				case 'X':
					g.getElement(x, y).setCellType(CellType.BLOCK);
					break;
				case '.':
					g.getElement(x, y).setCellType(CellType.EMPTY);
					break;
				default:
					break;
				}
			}
			y--;
		}
		g.setEndPositions(end);
		g.setStartPositions(start);
		return g;
	}
}
