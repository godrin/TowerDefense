package com.cdm.view.elements;

import java.util.Comparator;

import com.cdm.view.Position;
import com.cdm.view.elements.units.Unit;

// review1
public class DistanceComparator implements Comparator<Unit> {

	private Position base;

	public DistanceComparator(Position position) {
		base = position;
	}

	public DistanceComparator() {
	}

	@Override
	public int compare(Unit arg0, Unit arg1) {
		Position a = arg0.getPosition();
		Position b = arg1.getPosition();
		if (a.getSystem().equals(b.getSystem())
				&& base.getSystem().equals(a.getSystem())) {
			// faster - less memory use
			float dx = (a.x - base.x);
			float dy = (a.y - base.y);
			float d0 = (float) Math.sqrt(dx * dx + dy * dy);
			dx = (b.x - base.x);
			dy = (b.y - base.y);
			float d1 = (float) Math.sqrt(dx * dx + dy * dy);
			if (d0 < d1)
				return -1;
			if (d0 > d1)
				return 1;
			return 0;
		}
		float distA = a.distance(base);
		float distB = b.distance(base);
		if (distA < distB)
			return -1;
		if (distA > distB)
			return 1;
		return 0;
	}

	public void setPosition(Position position) {
		base = position;

	}
}
