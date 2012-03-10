package com.cdm.view.elements;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;

public class RocketThrower extends Unit implements Element {

	private List<Vector3> lines;
	private List<Vector3> poly;
	float angle = 0.0f;

	public RocketThrower(Position p) {
		super(p);
		Vector3 c0=new Vector3(-1,-1,0); 
		Vector3 c1=new Vector3(1,-1,0); 
		Vector3 c2=new Vector3(1,1,0); 
		Vector3 c3=new Vector3(-1,1,0); 
		
		lines = Arrays.asList(new Vector3[] { 
				c0,c1,c1,c2,c2,c3,c3,c0 });
		poly = Arrays.asList(new Vector3[] {c0,c1,c2,c0,c2,c3});

	}

	@Override
	public void draw(IRenderer renderer) {
		Color innerColor = new Color(0, 0, 0.6f, 1.0f);
		renderer.drawPoly(pos, poly, angle, innerColor,size);
		Color outerColor = new Color(0.2f, 0.2f, 1.0f, 1.0f);
		renderer.drawLines(pos, lines, angle, outerColor,size);
	}

	@Override
	public void move(float time) {
		angle += time * 10;
	}

}
