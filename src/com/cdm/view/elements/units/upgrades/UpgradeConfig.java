package com.cdm.view.elements.units.upgrades;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cdm.view.SpriteReader;
import com.cdm.view.elements.units.PlayerUnit;

public class UpgradeConfig {

	private static Map<String, List<Map<String, Float>>> data = null;

	public static List<Map<String, Float>> getConfig(
			Class<? extends PlayerUnit> klass) {
		checkData();
		String klassName = klass.getSimpleName();

		return data.get(klassName);
	}

	private static void checkData() {
		String filename = "/com/cdm/view/elements/units/unit_config.txt";
		InputStream is = SpriteReader.class.getResourceAsStream(filename);
		if (is == null)
			throw new RuntimeException("File " + filename + " not found!");
		InputStreamReader isr = new InputStreamReader(is);

		data = new TreeMap<String, List<Map<String, Float>>>();
		LineNumberReader lir = new LineNumberReader(isr);
		String line;
		String unitName = null;
		Integer levelNumber = null;
		Map<String, Float> values = new TreeMap<String, Float>();
		List<Map<String, Float>> levels = new ArrayList<Map<String, Float>>();

		try {
			while ((line = lir.readLine()) != null) {
				if (line.matches("^==.*")) {
					if (values.size() > 0) {
						levels.add(values);
					}
					values = new TreeMap<String, Float>();
					levelNumber = Integer.parseInt(line.substring(2));

				} else if (line.matches("^=.*")) {
					if (levels.size() > 0 && unitName != null) {
						data.put(unitName, levels);
					}
					levels = new ArrayList<Map<String, Float>>();
					unitName = line.substring(1);
				} else if (line.matches("^.+=[0-9.]+(#.*)?$")) {
					String[] pair = line.replaceAll("#.*", "").split("=");
					values.put(pair[0], Float.parseFloat(pair[1]));
				}

			}
		} catch (IOException e) {
		}

		if (values.size() > 0) {
			levels.add(values);
		}
		if (levels.size() > 0 && unitName != null) {
			data.put(unitName, levels);
		}

	}
}
