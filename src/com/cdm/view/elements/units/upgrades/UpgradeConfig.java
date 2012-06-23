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
import com.cdm.view.elements.units.Upgrade;
import com.cdm.view.elements.units.UpgradeImpl;

public class UpgradeConfig {

	public static class SingleUpgradeConfig {
		public Float value;
		public Integer priceForNext;

		public SingleUpgradeConfig(Float v, Integer price) {
			value = v;
			priceForNext = price;
		}
	}

	private static Map<String, List<Map<String, SingleUpgradeConfig>>> data = null;

	public static List<Map<String, SingleUpgradeConfig>> getConfig(
			Class<? extends PlayerUnit> klass) {
		checkData();
		String klassName = klass.getSimpleName();

		return data.get(klassName);
	}

	public static List<String> getUpradeTypes(String unitName) {
		checkData();
		List<String> l = new ArrayList<String>();

		System.out.println(unitName);
		l.addAll(data.get(unitName).get(0).keySet());

		return l;
	}

	public static String getSprite(String upgradeType) {
		return upgradeType + ".sprite";
	}

	public static Upgrade getUpgrade(String unitName, Integer level,
			String upgradeType) {

		List<Map<String, SingleUpgradeConfig>> levels = data.get(unitName);
		if (levels.size() > level) {
			SingleUpgradeConfig c = levels.get(level).get(upgradeType);
			return new UpgradeImpl(getSprite(upgradeType), upgradeType,
					c.priceForNext, level, unitName, c.value);
		}
		System.out
				.println("NO LEVEL FOUND FOR " + unitName + " level:" + level);
		return null;
	}

	private static void printData() {
		for (Map.Entry<String, List<Map<String, SingleUpgradeConfig>>> e : data
				.entrySet()) {
			System.out.println("UNIT " + e.getKey());
			for (Map<String, SingleUpgradeConfig> m : e.getValue()) {
				System.out.println("LEVEL:");
				for (Map.Entry<String, SingleUpgradeConfig> e2 : m.entrySet()) {
					System.out.println(e2.getKey() + ":" + e2.getValue().value
							+ " -- " + e2.getValue().priceForNext);
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

		data = new TreeMap<String, List<Map<String, SingleUpgradeConfig>>>();
		LineNumberReader lir = new LineNumberReader(isr);
		String line;
		String unitName = null;
		Map<String, SingleUpgradeConfig> values = new TreeMap<String, SingleUpgradeConfig>();
		List<Map<String, SingleUpgradeConfig>> levels = new ArrayList<Map<String, SingleUpgradeConfig>>();

		try {
			while ((line = lir.readLine()) != null) {
				line = line.replaceAll("#.*", "");
				if (line.matches("^==.*")) {
					if (values.size() > 0) {
						levels.add(values);
						System.out.println("ADD VALUES TO LEVELS");
					}
					values = new TreeMap<String, SingleUpgradeConfig>();

				} else if (line.matches("^=.*")) {
					if (values.size() > 0) {
						levels.add(values);
						values = new TreeMap<String, SingleUpgradeConfig>();
					}
					if (levels.size() > 0 && unitName != null) {
						data.put(unitName, levels);
						System.out
								.println("ADD LEVELS to data for " + unitName);
					}
					levels = new ArrayList<Map<String, SingleUpgradeConfig>>();
					unitName = line.substring(1);
				} else if (line.matches("^.+=[0-9.]+:[0-9]+?$")) {
					String[] pair = line.split("=");
					String[] valuePair = pair[1].split(":");
					values.put(
							pair[0],
							new SingleUpgradeConfig(Float
									.parseFloat(valuePair[0]), Integer
									.parseInt(valuePair[1])));
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
