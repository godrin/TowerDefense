package com.cdm.view.enemy.types;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.enemy.AirMovingEnemy;
import com.cdm.view.enemy.GroundMovingEnemy;

public class Rotor extends AirMovingEnemy {

	public Position nextStep = null;
	private static PolySprite sprite = null;
	private static final Vector3 DIRECTION = new Vector3(1, 0, 0);
	public static final Vector3 a = new Vector3(-1, -1, 0);
	static Vector3 a1 = new Vector3(0, -0.5f, 0);
	static Vector3 b = new Vector3(1, -1, 0);
	static Vector3 b1 = new Vector3(0.5f, 0, 0);
	static Vector3 c = new Vector3(1, 1, 0);
	static Vector3 c1 = new Vector3(0, 0.5f, 0);
	static Vector3 d = new Vector3(-1, 1, 0);
	static Vector3 d1 = new Vector3(-0.5f, 0, 0);
	static Vector3 e = new Vector3(-0.5f, 0, 0);

	private static List<Vector3> lines = Arrays.asList(new Vector3[] { a, a1,
			a1, b, b, b1, b1, c, c, c1, c1, d, d, d1, d1, e, e, a });
	private static final float SPEED = 0.49f;
	private static final Color outerColor = new Color(1, 0, 0, 0.7f);
	private static final Color innerColor = new Color(0, 0, 0, 1f);
	private static final Color innerColor2 = new Color(0.7f, 0, 0, 1f);
	float angle1, angle2 = 180;
		
	public Rotor(Position position) {
		super(position);
		if (sprite == null) {
			sprite = new PolySprite();
			sprite.fillCircle(0, 0, 0.95f, innerColor, 16);
			sprite.fillCircle(0, 0, 0.85f, innerColor2, 16);
			sprite.fillCircle(0, 0, 0.50f, innerColor, 16);
			sprite.fillCircle(0, 0, 0.40f, innerColor2, 16);
			sprite.fillCircle(0, 0, 0.30f, innerColor, 16);
			
			
			
			
			sprite.init();

			/*
			 * lines = new PolySprite(); lines.makeRectangle(-0.7f, -0.7f, 1.4f,
			 * 1.4f, borderColor); lines.init();
			 */
		}
		setSize(0.4f);
		setTurningSpeed2(15);
		setEnergy(25);
	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);

		getShakingLines().draw(renderer, getPosition(), lines, angle1,
				innerColor, getSize()*0.9f);
		getShakingLines().draw(renderer, getPosition(), lines, angle1+45,
				innerColor, getSize()*0.85f);
		getShakingLines().draw(renderer, getPosition(), lines, angle2,
				outerColor, getSize()*0.88f);
		getShakingLines().draw(renderer, getPosition(), lines, angle2+45,
				outerColor, getSize()*0.8f);
		renderer.render(sprite, getPosition(), getSize(), 180,
				GL10.GL_TRIANGLES);
		
	}
	
	@Override
	public void move(float time) {
		super.move(time);
		angle1 -= 4;
		angle2 += 2f;
		if (getLevel().getNextUnitPos(getPosition()) == FightPos){
			attack(getLevel().getUnitAt(getPosition().alignedToGrid()));
			}
		

	}
	
	
	@Override
	public float getOriginalSpeed() {
		return SPEED;
	}

	@Override
	public Vector3 getMovingDirection() {
		return DIRECTION;
	}

	@Override
	public int getMoney() {
		return 10;
	}

	@Override
	public int getPoints() {
		return 20;
	}

	@Override
	public int getBonus() {
		return 1;
	}

	@Override
	public int getZLayer() {
		return 3;
	}

}
