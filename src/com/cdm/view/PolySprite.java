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
	Mesh mesh = null;
	List<Vector3> vs = new ArrayList<Vector3>();
	List<Color> cs = new ArrayList<Color>();

	public void addVertex(Vector3 v, Color c) {
		vs.add(v);
		cs.add(c);
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
	
	public void makeThickRectangle(float d,float x,float y, float w, float h, Color color) {
		fillRectangle(x, y, w, d, color);
		fillRectangle(x+w-d, y, d, h, color);
		fillRectangle(x, y, d, h, color);
		fillRectangle(x, y+h-d, w, d, color);
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
		for (float a = 0.0f; a < 2 * 3.1415f-0.001f; a += step) {
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
	public void fillCircle(float x, float y, float r, Color c,Color middleColor, int segments) {
		float step = 3.1415f * 2 / segments;
		for (float a = 0.0f; a < 2 * 3.1415f-0.001f; a += step) {
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
