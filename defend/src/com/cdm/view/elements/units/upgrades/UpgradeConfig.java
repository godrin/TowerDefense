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
import com.cdm.view.elements.units.UnitAction;
import com.cdm.view.elements.units.UpgradeAction;

public class UpgradeConfig {

	private static Map<String, List<Map<String, Float>>> data = null;

	public static List<Map<String, Float>> getConfig(
			Class<? extends PlayerUnit> klass) {
		checkData();
		String klassName = klass.getSimpleName();

		return data.get(klassName);
	}

	public static List<String> getUpradeTypes(String unitName) {
		checkData();
		List<String> l = new ArrayList<String>();

		l.addAll(data.get(unitName).get(0).keySet());

		return l;
	}

	public static UnitAction getUpgrade(String unitName, Integer level) {
		checkData();
		List<Map<String, Float>> levels = data.get(unitName);
		if (levels.size() > level) {
			Map<String, Float> c = levels.get(level);
			return new UpgradeAction(level, unitName, c);
		}
		return null;
	}

	private static void printData() {
		if (true)
			return;
		for (Map.Entry<String, List<Map<String, Float>>> e : data.entrySet()) {
			System.out.println("UNIT " + e.getKey());
			for (Map<String, Float> m : e.getValue()) {
				System.out.println("LEVEL:");
				for (Map.Entry<String, Float> e2 : m.entrySet()) {
					// System.out.println(e2.getKey() + ":" +
					// e2.getValue().value
					// + " -- ");// + e2.getValue().priceForNext);
				}
			}
		}
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
		Map<String, Float> values = new TreeMap<String, Float>();
		List<Map<String, Float>> levels = new ArrayList<Map<String, Float>>();

		try {
			while ((line = lir.readLine()) != null) {
				line = line.replaceAll("#.*", "");
				if (line.matches("^==.*")) {
					if (values.size() > 0) {
						levels.add(values);
					}
					values = new TreeMap<String, Float>();

				} else if (line.matches("^=.*")) {
					if (values.size() > 0) {
						levels.add(values);
						values = new TreeMap<String, Float>();
					}
					if (levels.size() > 0 && unitName != null) {
						data.put(unitName, levels);
					}
					levels = new ArrayList<Map<String, Float>>();
					unitName = line.substring(1);
				} else if (line.matches("^.+=-?[0-9.]+$")) {
					String[] pair = line.split("=");
					values.put(pair[0], new Float(Float.parseFloat(pair[1])));
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
		printData();
	}
}
