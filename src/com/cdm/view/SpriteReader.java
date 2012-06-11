package com.cdm.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class SpriteReader {
	private static final Color BLUE = new Color(0, 0, 1, 0.8f);
	private static final Color SOLID_BLUE = new Color(0, 0, 1, 1f);
	private static final Color WHITE = new Color(1, 1, 1, 1);

	public static PolySprite read(String filename) {
		InputStream is = SpriteReader.class.getResourceAsStream(filename);

		InputStreamReader isr = new InputStreamReader(is);

		LineNumberReader lir = new LineNumberReader(isr);
		String line;

		List<String> gridBuffer = new ArrayList<String>();
		PolySprite sprite = new PolySprite();

		try {
			while ((line = lir.readLine()) != null) {
				if (line.matches("^fr:.*$")) {
					String[] args = line.split(":");
					sprite.fillRectangle(Float.parseFloat(args[1]),
							Float.parseFloat(args[2]),
							Float.parseFloat(args[3]),
							Float.parseFloat(args[4]), parseColor(args[5]));
				} else if (line.matches("^r:.*$")) {
					String[] args = line.split(":");
					sprite.makeThickRectangle(Float.parseFloat(args[1]),
							Float.parseFloat(args[2]),
							Float.parseFloat(args[3]),
							Float.parseFloat(args[4]),
							Float.parseFloat(args[5]), parseColor(args[6]));
				} else if (line.matches("^l:.*$")) {
					String[] args = line.split(":");
					Color c = parseColor(args[5]);
					sprite.addVertex(new Vector3(Float.parseFloat(args[1]),
							Float.parseFloat(args[2]), 0), c);
					sprite.addVertex(new Vector3(Float.parseFloat(args[3]),
							Float.parseFloat(args[4]), 0), c);

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sprite.init();

		return sprite;
	}

	private static Color parseColor(String string) {
		if ("blue".equals(string)) {
			return BLUE;
		}
		if ("solid-blue".equals(string)) {
			return SOLID_BLUE;
		}

		return WHITE;
	}
}
