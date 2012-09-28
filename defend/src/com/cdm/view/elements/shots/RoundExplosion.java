package com.cdm.view.elements.shots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.SimpleShader;
import com.cdm.view.WorldCallback;
import com.cdm.view.elements.Level;
import com.cdm.view.enemy.types.Cloud;

public class RoundExplosion extends PositionedDisplayEffect {

	private Position pos;
	private static ShaderProgram meshShader;
	private static Mesh mesh;
	public final static int SEGMENTS = 32;
	private float currentTime = 0;
	private Level level;
	private float[] px = new float[4];
	private float opacity = 1;
	private WorldCallback painter;

	public RoundExplosion(Position pPos, Level pLevel) {
		level = pLevel;
		pos = new Position(pPos.to(Position.LEVEL_REF));
		if (mesh == null) {
			mesh = new Mesh(true, SEGMENTS * 6, 0, new VertexAttribute(
					Usage.Position, 1, "a_distance"), new VertexAttribute(
					Usage.ColorPacked, 4, "a_mycolor"), new VertexAttribute(
					Usage.Generic, 1, "a_angle"));

			float color = Color.toFloatBits(255, 255, 255, 255);
			float vs[] = new float[SEGMENTS * 6 * 3];

			int j = 0;
			float r0 = 1;
			float r1 = 1.4f;
			for (int i = 0; i < SEGMENTS; i++) {
				float a0 = i * 2 * Cloud.M_PI / (SEGMENTS + 2);
				float a1 = (i + 1) * 2 * Cloud.M_PI / (SEGMENTS + 2);

				vs[j++] = r0;
				vs[j++] = color;
				vs[j++] = a0;

				vs[j++] = r1;
				vs[j++] = color;
				vs[j++] = a0;

				vs[j++] = r0;
				vs[j++] = color;
				vs[j++] = a1;

				vs[j++] = r0;
				vs[j++] = color;
				vs[j++] = a1;

				vs[j++] = r1;
				vs[j++] = color;
				vs[j++] = a0;

				vs[j++] = r1;
				vs[j++] = color;
				vs[j++] = a1;

			}
			mesh.setVertices(vs);

			meshShader = SimpleShader.createShader(Gdx.graphics,
					"round_explosion");

		}

		painter = new WorldCallback() {

			@Override
			public void callback(Matrix4 world) {
				drawIt(world, 1);
				drawIt(world, 0.5f);
			}
		};

	}

	protected void drawIt(Matrix4 world, float size) {
		meshShader.begin();
		meshShader.setUniformf("u_time", currentTime);
		meshShader.setUniformf("u_size", size);
		meshShader.setUniformf("u_opacity", opacity);

		px[0] = 0;
		px[1] = 0;
		px[2] = 0;
		px[3] = 1;
		// System.out.println("P "+pos.x+" pos.y");//
		meshShader.setUniformMatrix("world", world);
		meshShader.setUniform4fv("u_position", px, 0, 4);

		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
	}

	@Override
	public void move(float time) {
		this.currentTime += time * 0.5f;
		if (this.currentTime > 0.75f)
			opacity = 1.5f - currentTime / 0.75f;
		if (this.currentTime > 1.5f)
			level.removeShot(this);
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.render(painter, getPosition(), 1, 0);

	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
