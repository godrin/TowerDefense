package com.cdm.view.enemy.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.cdm.view.Position;
import com.cdm.view.SimpleShader;

public class Cloud {
	private List<Float> radii;
	private static final Random random = new Random();
	private static final float M_PI = 3.141519f;
	private static final float SKRINK_SPEED = 0.3f;
	private ShaderProgram meshShader;
	private Mesh mesh;
	private float currentTime = 0;
	private Position pos = new Position(0, 0, Position.LEVEL_REF);
	private float[] px = new float[4];
	private float size = 0.0f;
	private int sizing = 1;
	private float opacity=1.0f;

	public Cloud(float mean, float delta, int segments) {
		radii = new ArrayList<Float>(segments);
		for (int i = 0; i < segments; i++)
			radii.add(random.nextFloat() * delta * 2 - delta + mean);
		for (int i = 0; i < segments; i++) {
			float a = get(i - 1);
			float b = get(i);
			float c = get(i + 1);
			set(i, (a + b * 4 + c) / 6);
		}

		mesh = new Mesh(false, segments * 3 * 3, 0, new VertexAttribute(
				Usage.Position, 1, "a_distance"), new VertexAttribute(
				Usage.ColorPacked, 4, "a_mycolor"), new VertexAttribute(
				Usage.Generic, 1, "a_angle"));

		// float middleColor = Color.toFloatBits(255, 0, 0, 155);
		float centerColor = Color.toFloatBits(255, 0, 0, 155);
		float outerColor = Color.toFloatBits(0, 0, 0, 0);

		float middleColor = Color.toFloatBits(0, 0, 0, 100);

		int s = 3 * 3 * 3;

		float vs[] = new float[segments * s];
		for (int i = 0; i < segments; i++) {
			float a0 = i * 2 * M_PI / segments;
			float a1 = (i + 1) * 2 * M_PI / segments;
			float h = 0.9f;
			int j = 0;

			// 3 triangles:
			// C--D
			// |/ |
			// A--B
			// | /
			// |/
			// *
			//

			// *AB

			// *
			vs[i * s + (j++)] = 0;
			vs[i * s + (j++)] = centerColor;
			vs[i * s + (j++)] = a0;

			// A
			vs[i * s + (j++)] = get(i) * h;
			vs[i * s + (j++)] = middleColor;
			vs[i * s + (j++)] = a0;

			// B
			vs[i * s + (j++)] = get(i + 1) * h;
			vs[i * s + (j++)] = middleColor;
			vs[i * s + (j++)] = a1;

			// ADC

			// A
			vs[i * s + (j++)] = get(i) * h;
			vs[i * s + (j++)] = middleColor;
			vs[i * s + (j++)] = a0;

			// D
			vs[i * s + (j++)] = get(i + 1);
			vs[i * s + (j++)] = outerColor;
			vs[i * s + (j++)] = a1;

			// C
			vs[i * s + (j++)] = get(i);
			vs[i * s + (j++)] = outerColor;
			vs[i * s + (j++)] = a0;

			// ABD
			// A
			vs[i * s + (j++)] = get(i) * h;
			vs[i * s + (j++)] = middleColor;
			vs[i * s + (j++)] = a0;

			// B
			vs[i * s + (j++)] = get(i + 1) * h;
			vs[i * s + (j++)] = middleColor;
			vs[i * s + (j++)] = a1;
			// D
			vs[i * s + (j++)] = get(i + 1);
			vs[i * s + (j++)] = outerColor;
			vs[i * s + (j++)] = a1;

			if (j > s)
				throw new RuntimeException("WRONG");

		}

		mesh.setVertices(vs);

		meshShader = SimpleShader.createShader(Gdx.graphics, "cloud");
	}

	public void draw(Matrix4 world) {
		meshShader.begin();
		meshShader.setUniformf("u_time", currentTime);
		meshShader.setUniformf("u_size", size);
		meshShader.setUniformf("u_opacity", opacity);
		px[0] = pos.x;
		px[1] = pos.y;
		px[2] = 0;
		px[3] = 1;
		meshShader.setUniformMatrix("world", world);
		meshShader.setUniform4fv("u_position", px, 0, 4);

		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
	}

	public interface ShrinkedCallback {
		void callback();
	}

	private ShrinkedCallback skrinkedCallback = null;

	public void shrink(ShrinkedCallback callback) {
		skrinkedCallback = callback;
		sizing = -1;

	}

	public void move(float t) {
		currentTime += t;
		if (sizing != 0) {

			size += sizing * t * SKRINK_SPEED;
			if(sizing<0) {
				size += sizing * t * SKRINK_SPEED;
				opacity+= sizing * t * SKRINK_SPEED*3;
			}
			if (size < 0) {
				size = 0;
				if (skrinkedCallback != null) {
					skrinkedCallback.callback();
					skrinkedCallback = null;
				}
			}
			if (size > 1)
				size = 1;
		}
	}

	private void set(int i, float f) {
		radii.set(index(i), f);
	}

	private float get(int i) {
		return radii.get(index(i));
	}

	private int index(int i) {
		while (i < 0)
			i += radii.size();
		return i % radii.size();
	}
}
