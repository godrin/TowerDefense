package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.Selector;
import com.cdm.view.elements.Unit.UnitType;
import com.cdm.view.elements.paths.Path;
import com.cdm.view.elements.paths.PathFinder;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.shots.AbstractShot;
import com.cdm.view.elements.shots.AbstractShot2;
import com.cdm.view.elements.shots.Rocket;
import com.cdm.view.enemy.EnemyPlayer;
import com.cdm.view.enemy.EnemyUnit;
import com.cdm.view.enemy.SmallShip;

public class Level {
	private List<Unit> units = new ArrayList<Unit>();
	private Grid grid;
	private Selector selector = null;
	private EnemyPlayer player;
	private float speedFactor = 2.0f;
	private int health = 20;
	private List<Unit> unitsToRemove = new ArrayList<Unit>();

	private List<AbstractShot> shots = new ArrayList<AbstractShot>();
	private List<AbstractShot> shotsToRemove = new ArrayList<AbstractShot>();

	private List<AbstractShot2> shots2 = new ArrayList<AbstractShot2>();
	private List<AbstractShot2> shotsToRemove2 = new ArrayList<AbstractShot2>();
	
	public Level(int w, int h, int endY) {
		grid = new Grid(w, h, endY);
		player = new EnemyPlayer();
		player.setLevel(this);

		// add(new Rocket(new Position(10, 3, RefSystem.Level)));
		// add(new SmallShip(new Position(1, 1, RefSystem.Level)));


	}

	public void add(Position pos, UnitType type) {
		units.add(Elements.getElementBy(type, pos));
	}

	public void hover(Position pos) {
		if (pos.screenPos()) {
			pos = pos.toLevelPos().alignToGrid();
		}
		selector = new Selector(pos);
	}

	public void stopHover() {
		selector = null;
	}

	public synchronized void move(float time) {
		if (time > 0.1f)
			time = 0.1f;
		time *= speedFactor;
		player.addTime(time);
		for (Unit unit : units) {
			unit.move(time);
		}
		for (AbstractShot shot : shots) {
			shot.move(time);
		}
		for (AbstractShot2 shot2 : shots2) {
			shot2.move(time);
		}
		for (Unit unit : unitsToRemove) {
			System.out.println("REMOVIIIING " + unit);
			units.remove(unit);
		}
		unitsToRemove.clear();
		for (AbstractShot shot : shotsToRemove) {
			shots.remove(shot);
		}
		shotsToRemove.clear();
		
		for (AbstractShot2 shot2 : shotsToRemove2) {
			shots2.remove(shot2);
		}
		shotsToRemove2.clear();
	}

	public void draw(IRenderer renderer) {
		for (Unit unit : units) {
			unit.draw(renderer);
		}
		for (AbstractShot shot : shots) {
			shot.draw(renderer);
		}
		for (AbstractShot2 shot2 : shots2) {
			shot2.draw(renderer);
		}
		if (selector != null)
			selector.draw(renderer);

		drawBox(renderer);

	}

	private void drawBox(IRenderer renderer) {

		float size = 2.0f;
		Color color = new Color(0, 0, 1, 1);
		Position pos = new Position(0, 0, RefSystem.Level);
		Position start = getEnemyStartPosition();
		Position end = getEnemyEndPosition();

		// upper box
		Vector3 a = new Vector3(0, start.y - 0.5f, 0);
		Vector3 b = new Vector3(0, 0, 0);
		Vector3 c = new Vector3(end.x - 0.5f, 0, 0);
		Vector3 d = new Vector3(end.x - 0.5f, start.y - 0.5f, 0);
		// lower box
		Vector3 e = new Vector3(0, start.y + 0.5f, 0);
		Vector3 f = new Vector3(0, grid.getH() - 0.5f, 0);
		Vector3 g = new Vector3(end.x - 0.5f, grid.getH() - 0.5f, 0);
		Vector3 h = new Vector3(end.x - 0.5f, start.y + 0.5f, 0);

		List<Vector3> lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d,
				e, f, f, g, g, h });
		float angle = 0.0f;
		renderer.drawLines(pos, lines, angle, color, size, RefSystem.Level);
	}

	public void add(Unit dragElement) {
		Position lpos = dragElement.getPosition().toLevelPos().alignToGrid();
		if (!(dragElement instanceof EnemyUnit)
				&& (lpos.x < 0 || lpos.x > grid.getW() - 1 || lpos.y < 0 || lpos.y > grid
						.getH()))
			return;
		List<Element> l = grid.get((int) lpos.x, (int) lpos.y);

		if (l == null || l.isEmpty() || dragElement instanceof EnemyUnit) {

			dragElement.setLevel(this);
			dragElement.setPosition(lpos);
			units.add(dragElement);
		} else {
			System.out.println("NOT EMPTY!");
		}
	}

	public void removeMeFromGrid(Position p, Unit unit) {
		int x0 = Math.round(p.x);
		int y0 = Math.round(p.y);

		List<Element> l = grid.get(x0, y0);
		if (l != null)
			l.remove(unit);
		else {
			System.out.println("NOT FOUND" + x0 + " " + y0);
		}
	}

	public void addMeToGrid(Position p, Unit unit) {
		int x0 = Math.round(p.x);
		int y0 = Math.round(p.y);

		List<Element> l = grid.get(x0, y0);
		if (l != null)
			l.add(unit);
	}

	public boolean hasEnemies() {
		for (Unit unit : units) {
			if (unit instanceof EnemyUnit)
				return true;
		}
		return false;
	}

	public Position getEnemyStartPosition() {
		return new Position(-1, grid.endY(), RefSystem.Level);
	}

	public Position getEnemyEndPosition() {
		return new Position(grid.getW(), grid.endY(), RefSystem.Level);
	}

	public Position getNextPos(Position alignToGrid) {
		// FIXME
		// return new Position(alignToGrid.x + 1, alignToGrid.y,
		// RefSystem.Level);
		Position finish = getEnemyEndPosition();
		PathPos from = new PathPos((int) alignToGrid.x, (int) alignToGrid.y);
		PathPos to = new PathPos((int) finish.x, (int) finish.y);

		Path p = PathFinder.findPath(grid, from, to);

		PathPos pp = p.second();
		return new Position(pp.x, pp.y, RefSystem.Level);
	}

	public void enemyReachedEnd(EnemyUnit enemyUnit) {
		System.out.println("REACHED " + enemyUnit);
		health -= 1;
		removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		unitsToRemove.add(enemyUnit);
	}

	public void enemyDestroyed(EnemyUnit enemyUnit) {
		// FIXME: add money
		removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		unitsToRemove.add(enemyUnit);

	}

	public void removeShot(AbstractShot shot) {
		shotsToRemove.add(shot);
	}
	
	public void removeShot2(AbstractShot2 shot2) {
		shotsToRemove2.add(shot2);
	}

	public EnemyUnit getNextEnemy(Position position) {

		SortedSet<EnemyUnit> s = new TreeSet<EnemyUnit>(new DistanceComparator(
				position));
		for (Unit u : units) {
			if (u instanceof EnemyUnit) {
				s.add((EnemyUnit) u);
			}
		}

		if (s.size() > 0)
			return s.first();
		return null;
	}

	public void addShot(AbstractShot abstractShot) {
		shots.add(abstractShot);
	}
	
	public void addShot2(AbstractShot2 abstractShot2) {
		shots2.add(abstractShot2);
	}

	public EnemyUnit getEnemyAt(Position target) {
		List<Element> l = grid.get((int) (target.x + 0.5f),
				(int) (target.y + 0.5f));
		if (l != null) {
			for (Element e : l) {
				if (e instanceof EnemyUnit) {
					return (EnemyUnit) e;
				}
			}
		}
		return null;
	}

}
