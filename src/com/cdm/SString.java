package com.cdm;

import java.util.ArrayList;
import java.util.List;

public class SString implements Comparable<SString> {
	private Integer id;
	private static List<String> known = new ArrayList<String>();
	private static List<SString> sstrings = new ArrayList<SString>();
	
	public static SString SIZE_BUTTON=SString.create("SIZE_BUTTON");
	public static SString RESTART_BUTTON=SString.create("RESTART_BUTTON");
	
	private SString(int i) {
		id = i;
	}

	public static SString create(String name) {
		int i = known.indexOf(name);
		if (i >= 0 && i < known.size()) {
			return sstrings.get(i);
		} else {
			i = known.size();
			known.add(name);
			SString s = new SString(i);
			sstrings.add(s);
			return s;

		}
	}

	public boolean equals(SString a) {
		return id.equals(a.id);
	}

	@Override
	public int compareTo(SString arg0) {
		return id.compareTo(arg0.id);
	}

}
