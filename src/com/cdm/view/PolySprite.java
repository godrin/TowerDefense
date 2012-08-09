package com.cdm.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PolySprite {
	private static final Color OUTER_COLOR = new Color(0, 0, 0, 0);
	private static final float PI = 3.1415f;
	private Mesh mesh = null;
	private List<Vector3> vs = new ArrayList<Vector3>();
	private List<Color> cs = new ArrayList<Color>();
	private int primitiveType;

	public void addVertex(Vector3 v, Color c) {
		vs.add(v);
		cs.add(c);
	}

	public int getPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(int primitiveType) {
		this.primitiveType = primitiveType;
	}

	public void init() {

		VertexAttribute[] attributes = getAttributes();

		mesh = new Mesh(true, vs.size() * 8, vs.size(), attributes);

		int vertexSize = mesh.getVertexAttributes().vertexSize / 4;
		int colorOffset = mesh.getVertexAttribute(Usage.ColorPacked) != null ? mesh
				.getVertexAttribute(Usage.ColorPacked).offset / 4 : 0;

		float[] vertices = new float[vs.size() * 8 * vertexSize];
		short[] indexes = new short[vs.size()];

		for (short u = 0; u < vertices.length; u++)
			vertices[u] = 1;

		for (short i = 0; i < vs.size(); i++) {
			int b = i * vertexSize;
			Vector3 v = vs.get(i);
			Color c = cs.get(i);
			vertices[b + 0] = v.x;
			vertices[b + 1] = v.y;
			vertices[b + 2] = v.z;
			vertices[b + colorOffset] = Color.toFloatBits(c.r, c.g, c.b, c.a);
			indexes[i] = i;
		}
		mesh.setAutoBind(true);
		mesh.setVertices(vertices);
		mesh.setIndices(indexes);

	}

	public void fillRectangle(float x, float y, float w, float h, Color color) {
		Vector3 a = new Vector3(x, y, 0);
		Vector3 b = new Vector3(x + w, y, 0);
		Vector3 c = new Vector3(x + w, y + h, 0);
		Vector3 d = new Vector3(x, y + h, 0);
		addVertex(a, color);
		addVertex(b, color);
		addVertex(c, color);
		addVertex(a, color);
		addVertex(c, color);
		addVertex(d, color);
	}

	public void makeArc(float x, float y, float radius, float startAngle,
			float angle, int sections, Color inner, Color outer) {
		for (int i = 0; i < sections; i++) {
			addVertex(new Vector3(x, y, 0), inner);
			addVertex(
					new Vector3(x
							+ radius
							* (float) Math.sin(startAngle + angle * i
									/ sections), y
							+ radius
							* (float) Math.cos(startAngle + angle * i
									/ sections), 0), outer);
			addVertex(
					new Vector3(x
							+ radius
							* (float) Math.sin(startAngle + angle * (i + 1)
									/ sections), y
							+ radius
							* (float) Math.cos(startAngle + angle * (i + 1)
									/ sections), 0), outer);

		}
	}

	public void makeNiceRectangle(float d, float x, float y, float w, float h,
			Color inner, Color outer) {

		makeArc(x, y, d, PI, PI / 2, 4, inner, outer);
		makeArc(x + w, y, d, PI / 2, PI / 2, 4, inner, outer);
		makeArc(x + w, y + h, d, 0, PI / 2, 4, inner, outer);
		makeArc(x, y + h, d, PI * 3 / 2, PI / 2, 4, inner, outer);
	}

	public void makeNiceRectangleOld(float d, float x, float y, float w,
			float h, Color inner, Color outer) {
		List<Vector3> vs = new ArrayList<Vector3>();
		vs.add(new Vector3(x - d, y - d, 0));
		vs.add(new Vector3(x, y, 0));
		vs.add(new Vector3(x + d, y + d, 0));

		vs.add(new Vector3(x + w + d, y - d, 0));
		vs.add(new Vector3(x + w, y, 0));
		vs.add(new Vector3(x + w - d, y + d, 0));

		vs.add(new Vector3(x + w + d, y + h + d, 0));
		vs.add(new Vector3(x + w, y + h, 0));
		vs.add(new Vector3(x + w - d, y + h - d, 0));

		vs.add(new Vector3(x - d, y + h + d, 0));
		vs.add(new Vector3(x, y + h, 0));
		vs.add(new Vector3(x + d, y + h - d, 0));

		// upper
		addVertex(vs.get(0), outer);
		addVertex(vs.get(3), outer);
		addVertex(vs.get(1), inner);
		addVertex(vs.get(3), outer);
		addVertex(vs.get(4), inner);
		addVertex(vs.get(1), inner);

		addVertex(vs.get(1), inner);
		addVertex(vs.get(4), inner);
		addVertex(vs.get(2), outer);
		addVertex(vs.get(2), outer);
		addVertex(vs.get(4), inner);
		addVertex(vs.get(5), outer);

		// left

		addVertex(vs.get(0), outer);
		addVertex(vs.get(1), inner);
		addVertex(vs.get(9), outer);
		addVertex(vs.get(1), inner);
		addVertex(vs.get(10), inner);
		addVertex(vs.get(9), outer);

		addVertex(vs.get(1), inner);
		addVertex(vs.get(2), outer);
		addVertex(vs.get(10), inner);
		addVertex(vs.get(2), outer);
		addVertex(vs.get(11), outer);
		addVertex(vs.get(10), inner);

		// lower
		addVertex(vs.get(9), outer);
		addVertex(vs.get(6), outer);
		addVertex(vs.get(10), inner);
		addVertex(vs.get(10), inner);
		addVertex(vs.get(6), outer);
		addVertex(vs.get(7), inner);

		addVertex(vs.get(10), inner);
		addVertex(vs.get(7), inner);
		addVertex(vs.get(11), outer);
		addVertex(vs.get(7), inner);
		addVertex(vs.get(11), outer);
		addVertex(vs.get(8), outer);

	}

	public void makeThickRectangle(float d, float x, float y, float w, float h,
			Color color) {

		if (true) {
			Color c = new Color(color);
			c.a *= 0.25f;
			makeNiceRectangle(d * 4, x, y, w, h, c, OUTER_COLOR);

		} else {
			fillRectangle(x, y, w, d, color);
			fillRectangle(x + w - d, y, d, h, color);
			fillRectangle(x, y, d, h, color);
			fillRectangle(x, y + h - d, w, d, color);
		}
	}

	public void makeRectangle(float x, float y, float w, float h, Color color) {
		Vector3 a = new Vector3(x, y, 0);
		Vector3 b = new Vector3(x + w, y, 0);
		Vector3 c = new Vector3(x + w, y + h, 0);
		Vector3 d = new Vector3(x, y + h, 0);
		addVertex(a, color);
		addVertex(b, color);
		addVertex(b, color);
		addVertex(c, color);
		addVertex(c, color);
		addVertex(d, color);
		addVertex(d, color);
		addVertex(a, color);
	}

	public void fillCircle(float x, float y, float r, Color c, int segments) {
		float step = 3.1415f * 2 / segments;
		for (float a = 0.0f; a < 2 * 3.1415f - 0.001f; a += step) {
			Vector3 v0 = new Vector3(x + (float) Math.sin(a) * r, y
					+ (float) Math.cos(a) * r, 0);
			Vector3 v1 = new Vector3(x + (float) Math.sin(a + step) * r, y
					+ (float) Math.cos(a + step) * r, 0);
			Vector3 v2 = new Vector3(x, y, 0);
			addVertex(v0, c);
			addVertex(v1, c);
			addVertex(v2, c);
		}
	}

	public void fillCircle(float x, float y, float r, Color c,
			Color middleColor, int segments) {
		float step = 3.1415f * 2 / segments;
		for (float a = 0.0f; a < 2 * 3.1415f - 0.001f; a += step) {
			Vector3 v0 = new Vector3(x + (float) Math.sin(a) * r, y
					+ (float) Math.cos(a) * r, 0);
			Vector3 v1 = new Vector3(x + (float) Math.sin(a + step) * r, y
					+ (float) Math.cos(a + step) * r, 0);
			Vector3 v2 = new Vector3(x, y, 0);
			addVertex(v0, c);
			addVertex(v1, c);
			addVertex(v2, middleColor);
		}
	}

	private VertexAttribute[] getAttributes() {
		Array<VertexAttribute> attribs = new Array<VertexAttribute>();
		attribs.add(new VertexAttribute(Usage.Position, 3,
				ShaderProgram.POSITION_ATTRIBUTE));
		attribs.add(new VertexAttribute(Usage.ColorPacked, 4,
				ShaderProgram.COLOR_ATTRIBUTE));

		VertexAttribute[] array = new VertexAttribute[attribs.size];
		for (int i = 0; i < attribs.size; i++)
			array[i] = attribs.get(i);
		return array;
	}

	public void render(int renderMode) {
		mesh.render(renderMode);
	}
}
