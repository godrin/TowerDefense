package com.cdm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class SimpleShader {
	public static  ShaderProgram createShader(Graphics graphics, String name) {

		String vertexShader, fragmentShader;
		vertexShader = getProgram("data/shaders/" + name + ".vert");
		fragmentShader = getProgram("data/shaders/" + name + ".frag");

		ShaderProgram meshShader = new ShaderProgram(vertexShader,
				fragmentShader);
		if (meshShader.isCompiled() == false) {
			System.out.println(meshShader.getManagedStatus());
			System.out.println(meshShader.getLog());
			throw new IllegalStateException(meshShader.getLog());
		}
		return meshShader;
	}
	public static String getProgram(String filePath) {
		return Gdx.files.internal(filePath).readString();
	}

}
