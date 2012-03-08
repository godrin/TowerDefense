package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.cdm.Settings;

public class Renderer implements IRenderer {
	ImmediateModeRenderer renderer = new ImmediateModeRenderer();

	@Override
	public void drawLines(Position pos, List<Vector3> lines, float angle,
			Color color) {
		float scale = 
			Settings.CELL_WIDTH/2;
			//pos.getScale();
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl10.glTranslatef(pos.getX(), pos.getY(), 0);
		Gdx.gl10.glScalef(scale, scale, scale);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

		renderer.begin(GL10.GL_LINES);
		for (Vector3 v : lines) {
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v);
		}

		renderer.end();

		Gdx.gl10.glPopMatrix();

	}

	@Override
	public void drawPoly(Position pos, List<Vector3> lines, float angle,
			Color color) {
		float scale = 1;
		//if (pos.equals(Position.RefSystem.Level)) {
			scale = Settings.CELL_WIDTH/2;
	//	}
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl10.glTranslatef(pos.getX(), pos.getY(), 0);
		Gdx.gl10.glScalef(scale, scale, scale);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

		renderer.begin(GL10.GL_TRIANGLES);
		for (Vector3 v : lines) {
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v);
		}

		renderer.end();

		Gdx.gl10.glPopMatrix();

	}

	@Override
	public void drawRect(float x0, float y0, float x1, float y1) {
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		renderer.begin(GL10.GL_LINE_LOOP);

		Vector3[] lines = new Vector3[] { new Vector3(x0, y0, 0),
				new Vector3(x1, y0, 0), new Vector3(x1, y1, 0),
				new Vector3(x0, y1, 0) };
		for (Vector3 v : lines) {
			renderer.color(1, 1, 1, 1);
			renderer.vertex(v);
		}

		renderer.end();

	}

	@Override
	public void fillRect(float x0, float y0, float x1, float y1, Color c) {
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		renderer.begin(GL10.GL_TRIANGLES);

		Vector3[] lines = new Vector3[] { new Vector3(x0, y0, 0),
				new Vector3(x1, y1, 0), new Vector3(x1, y0, 0),
				new Vector3(x0, y0, 0), new Vector3(x1, y1, 0),
				new Vector3(x0, y1, 0) };
		for (Vector3 v : lines) {

			renderer.color(c.r, c.g, c.b, c.a);
			renderer.vertex(v);
		}

		renderer.end();

	}

}
