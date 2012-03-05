package com.cdm.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Renderer implements IRenderer {
	private static final int CELL_SIZE = 20;
	ImmediateModeRenderer renderer = new ImmediateModeRenderer();

	@Override
	public void drawLines(float x, float y, List<Vector3> lines,float angle) {
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl10.glEnable( GL10.GL_BLEND );
		Gdx.gl10.glHint(GL10.GL_LINE_SMOOTH_HINT,GL10.GL_NICEST);
		Gdx.gl10.glTranslatef(x * CELL_SIZE, y * CELL_SIZE, 0);
		Gdx.gl10.glScalef(20, 20, 20);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);

		renderer.begin(GL10.GL_LINES);
		for(Vector3 v:lines) {
			renderer.color(1, 1, 1, 1);
			renderer.vertex(v);
		}

		renderer.end();

		Gdx.gl10.glPopMatrix();
		
	}

}
