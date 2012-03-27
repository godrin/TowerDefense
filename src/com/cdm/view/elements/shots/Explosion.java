/*package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Level;

public class Explosion extends MovingShot {

public static float speed = 1;
private List<Vector3> lines;
private List<Vector3> poly;

public Explosion(Position from, Position to, Level plevel) {
	super(from, to, plevel);
	Vector3 a = new Vector3(-1, -1, 0);
	Vector3 b = new Vector3(1, -1, 0);
	Vector3 c = new Vector3(1, 1, 0);
	Vector3 d = new Vector3(-1, 1, 0);

	lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, a});
	poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d });

}

@Override
public void draw(IRenderer renderer) {
	Color innerColor = new Color(0, 0, 0.6f, 1.0f);
	renderer.drawPoly(getPosition(), poly, 0.0f, innerColor, 0.5f,
			RefSystem.Level);
	Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
	renderer.drawLines(getPosition(), lines, 0.0f, outerColor, 0.5f,
			RefSystem.Level);
}

@Override
public float getSpeed() {
	return speed;
}

}
######################*/
package com.cdm.view.elements.shots;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.Unit;

public class Explosion extends Unit implements Element {

	// private Position pos;
	private List<Vector3> lines;
	private List<Vector3> poly;
	private float size = getSize() + 1.0f;
	public Explosion(Position pos) {
		super(pos);
		Vector3 a = new Vector3(-1, -1, 0);
		Vector3 b = new Vector3(1, -1, 0);
		Vector3 c = new Vector3(1, 1, 0);
		Vector3 d = new Vector3(-1, 1, 0);

		lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, a });
		poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d });
	}

	public void draw(IRenderer renderer) {
		while (size >= 0.0f) {
			Color innerColor = new Color(0.7f, 0, 0, 0);
			renderer.drawPoly(getPosition(), poly, 180, innerColor,
					size, RefSystem.Level);
			Color outerColor = new Color(1, 0, 0, 1.0f);
			renderer.drawLines(getPosition(), lines, 180, outerColor,
					size, RefSystem.Level);
			size = shrink(size);
		}
	}

	@Override
	public void setPosition(Position pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(float time) {
		// TODO Auto-generated method stub

	}

	public float shrink(float size) {
		size -= 0.001;
		return size;
	}

}
