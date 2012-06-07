package com.cdm.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Grid.CellType;
import com.cdm.view.elements.paths.PathPos;

public class CampaignParser {
	public static Grid getGrid(int i) {
		Grid g = null;

		InputStream is = CampaignParser.class
				.getResourceAsStream("/com/cdm/view/campaign1.txt");

		InputStreamReader isr = new InputStreamReader(is);

		LineNumberReader lir = new LineNumberReader(isr);
		String line;

		List<String> gridBuffer = new ArrayList<String>();

		boolean found = false;
		try {
			while ((line = lir.readLine()) != null) {
				if (line.matches("^=.*")) {
					found = line.equals("=" + i);
				} else if (found) {
					if (line.matches("^#.*")) {
						// comment - ignore
					} else {
						gridBuffer.add(line);
					}
				}
				System.out.println(line);
			}
		} catch (IOException e) {
		}

		if (gridBuffer.size() > 0) {
			System.out.println("HOORAY");
			int h = gridBuffer.size();
			int w = 0;
			List<PathPos> start = new ArrayList<PathPos>();
			List<PathPos> end = new ArrayList<PathPos>();
			int startX, startY, endX, endY;
			// get size of grid
			for (String l : gridBuffer) {
				if (w < l.length())
					w = l.length();
			}
			g = new Grid(w, h);
			int x, y = h-1;
			for (String l : gridBuffer) {
				for (x = 0; x < l.length(); x++) {
					char c = l.charAt(x);
					switch (c) {
					case 'S':
						start.add(new PathPos(x, y, 0));
						break;
					case 'E':
						end.add(new PathPos(x, y, 0));
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
			// FIXME
			g.setEndy(end.get(0).y);

		}

		return g;

	}
}
