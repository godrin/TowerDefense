package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
import com.cdm.view.elements.shots.MovingShot;
import com.cdm.view.enemy.EnemyPlayer;
import com.cdm.view.enemy.EnemyUnit;

public class Level {
	private List<Unit> units = new ArrayList<Unit>();
	private Grid grid;
	private Selector selector = null;
	private EnemyPlayer player;
	private float speedFactor = 2.0f;
	private int health = 3;
	private int money = 15;
	private int points = 0;
	private int bonus = 0;
	private List<Unit> unitsToRemove = new ArrayList<Unit>();

	private List<AbstractShot> shots = new ArrayList<AbstractShot>();
	private List<AbstractShot> shotsToRemove = new ArrayList<AbstractShot>();

	public Level(int w, int h, int endY) {
		grid = new Grid(w, h, endY);
		player = new EnemyPlayer();
		player.setLevel(this);
	}

	public void add(Position pos, UnitType type) {
		units.add(Elements.getElementBy(type, pos));
	}

	public EnemyPlayer getPlayer() {
		return player;
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
		boolean gameover = gameover();
		if (time > 0.1f)
			time = 0.1f;
		time *= speedFactor;
		if (!gameover)
			player.addTime(time);

		for (Unit unit : units) {
			unit.move(time);
		}
		for (AbstractShot shot : shots) {
			shot.move(time);
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
	}

	public void draw(IRenderer renderer) {
		for (Unit unit : units) {
			unit.draw(renderer);
		}
		for (AbstractShot shot : shots) {
			shot.draw(renderer);
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

	public boolean add(Unit dragElement) {
		if (dragElement == null)
			return false;
		if (getMoney() < dragElement.getCost()) {
			return false;
		}

		Position lpos = dragElement.getPosition().toLevelPos().alignToGrid();
		if (!(dragElement instanceof EnemyUnit)
				&& (lpos.x < 0 || lpos.x > grid.getW() - 1 || lpos.y < 0 || lpos.y > grid
						.getH()))
			return false;

		if (!isFreeForNewUnit(lpos))
			return false;

		List<Element> l = grid.get((int) lpos.x, (int) lpos.y);

		if (l == null || l.isEmpty() || dragElement instanceof EnemyUnit) {

			dragElement.setLevel(this);
			dragElement.setPosition(lpos);
			units.add(dragElement);
			setMoney(getMoney() - dragElement.getCost());

			return true;
		} else {
			System.out.println("NOT EMPTY!");
		}
		return false;
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

	public boolean isFreeForNewUnit(Position pos) {
		PathPos from = new PathPos(getEnemyStartPosition());
		PathPos to = new PathPos(getEnemyEndPosition());

		Set<PathPos> ignore = new TreeSet<PathPos>();
		ignore.add(new PathPos(pos));
		return PathFinder.widthSearch(grid, from, to, ignore);
	}

	public Position getNextPos(Position alignToGrid) {
		// FIXME
		// return new Position(alignToGrid.x + 1, alignToGrid.y,
		// RefSystem.Level);
		Position finish = getEnemyEndPosition();
		PathPos from = new PathPos(alignToGrid);
		PathPos to = new PathPos(finish);

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
		removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		unitsToRemove.add(enemyUnit);
		money += enemyUnit.getMoney();
		points += enemyUnit.getPoints();
		bonus += enemyUnit.getBonus();
	}

	public void removeShot(MovingShot shot) {
		shotsToRemove.add(shot);
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

	public void addShot(MovingShot abstractShot) {
		shots.add(abstractShot);
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getHealth() {
		return health;
	}

	public boolean gameover() {
		return health < 1;
	}

}
