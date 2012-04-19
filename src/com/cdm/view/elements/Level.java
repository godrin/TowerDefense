package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Selector;
import com.cdm.view.elements.Grid.GridElement;
import com.cdm.view.elements.paths.PathFinder;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.shots.DisplayEffect;
import com.cdm.view.elements.shots.Explosion;
import com.cdm.view.elements.shots.Shake;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.elements.units.Unit.UnitType;
import com.cdm.view.enemy.EnemyPlayer;
import com.cdm.view.enemy.EnemyUnit;

public class Level {
	private Grid grid;
	private Selector selector = null;
	private EnemyPlayer player;
	private float speedFactor = 2.0f;
	private int health = 3;
	private int money = 10;
	private int points = 0;
	private int bonus = 0;
	private List<Unit> units = new ArrayList<Unit>();
	private List<Unit> unitsToRemove = new ArrayList<Unit>();

	private List<DisplayEffect> displayEffects = new ArrayList<DisplayEffect>();
	private List<DisplayEffect> displayEffectsToRemove = new ArrayList<DisplayEffect>();
	private List<DisplayEffect> displayEffectsToAdd = new ArrayList<DisplayEffect>();
	private BoxDrawing boxDrawer;

	public Level(int w, int h, int endY) {
		grid = new Grid(w, h, endY);
		Position.LEVEL_REF.setWidth(w);
		Position.LEVEL_REF.setHeight(h);
		player = new EnemyPlayer();
		player.setLevel(this);
		boxDrawer = new BoxDrawing(new Position(-1, grid.endY(), Position.LEVEL_REF),
				getEnemyEndPosition(), grid.getH());

		PathFinder.breadthSearch(grid, getEnemyStartPositionPlusOne(),
				new PathPos(getEnemyEndPosition()), null, false);

	}

	public void add(Position pos, UnitType type) {
		units.add(Elements.getElementBy(type, pos.to(Position.LEVEL_REF)
				.alignedToGrid()));
	}

	public EnemyPlayer getPlayer() {
		return player;
	}

	public void hover(Position pos) {
		if (pos.screenPos()) {
			pos = pos.to(Position.LEVEL_REF).alignedToGrid();
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

		// fixme - don't iterators, but indexes (?)
		for (Unit unit : units) {
			unit.move(time);
		}
		for (DisplayEffect shot : displayEffects) {
			shot.move(time);
		}
		for (Unit unit : unitsToRemove) {
			units.remove(unit);
		}
		unitsToRemove.clear();
		for (DisplayEffect shot : displayEffectsToRemove) {
			displayEffects.remove(shot);
		}
		displayEffectsToRemove.clear();
		displayEffects.addAll(displayEffectsToAdd);
		displayEffectsToAdd.clear();
	}

	public void draw(IRenderer renderer) {
		drawBox(renderer);

		for (int zLayer = 0; zLayer < 10; zLayer++) {
			for (Unit unit : units) {
				if (unit != null)
					if (unit.getZLayer() == zLayer)
						unit.draw(renderer);
			}
		}
		for (DisplayEffect shot : displayEffects) {
			shot.draw(renderer);
		}
		if (selector != null)
			selector.draw(renderer);

	}

	private void drawBox(IRenderer renderer) {
		boxDrawer.draw(renderer);
	}

	public boolean add(Unit dragElement) {
		if (dragElement == null)
			return false;
		if (getMoney() < dragElement.getCost()) {
			return false;
		}

		Position lpos = dragElement.getPosition().to(Position.LEVEL_REF)
				.alignedToGrid();
		if (!(dragElement instanceof EnemyUnit)
				&& (lpos.x < 0 || lpos.x > grid.getW() - 1 || lpos.y < 0 || lpos.y > grid
						.getH()))
			return false;

		// check if way is still free then
		if (!(dragElement instanceof EnemyUnit)) {
			if (!isFreeForNewUnit(lpos))
				return false;
		}

		List<Element> l = grid.get((int) lpos.x, (int) lpos.y);

		if (l == null || l.isEmpty() || dragElement instanceof EnemyUnit) {

			dragElement.setLevel(this);
			dragElement.setPosition(lpos);
			units.add(dragElement);
			setMoney(getMoney() - dragElement.getCost());

			if (!(dragElement instanceof EnemyUnit))
				PathFinder.breadthSearch(grid, getEnemyStartPositionPlusOne(),
						new PathPos(getEnemyEndPosition()), null, false);
			grid.print();

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
		return new Position(-1, grid.endY(), Position.LEVEL_REF);
	}
	
	public Position getEnemyStartPosition2() {
		return new Position(0, grid.endY()-4, Position.LEVEL_REF);
	}
	
	public PathPos getEnemyStartPositionPlusOne() {
		return new PathPos(0, grid.endY(), -1);
	}

	public Position getEnemyEndPosition() {
		return new Position(grid.getW(), grid.endY(), Position.LEVEL_REF);
	}

	public boolean isFreeForNewUnit(Position pos) {
		PathPos from = getEnemyStartPositionPlusOne();
		PathPos to = new PathPos(getEnemyEndPosition());

		PathPos ignore = new PathPos(pos);
		return PathFinder.breadthSearch(grid, from, to, ignore, true);
	}

	public Position getNextPos(Position alignToGrid) {
		Position finish = getEnemyEndPosition();
		PathPos from = new PathPos(alignToGrid);
		PathPos finishPos = new PathPos(finish);
		finish.y+=4;
		if (true) {
			int curVal = 1000;
			GridElement ge = grid.getElement(from.x, from.y);
			if (ge != null)
				curVal = ge.getDistToEnd();
			for (PathPos p : from.next()) {
				if (finishPos.equals(p))
					return new Position(p.x, p.y, Position.LEVEL_REF);
				GridElement nge = grid.getElement(p.x, p.y);
				if (nge != null)
					if (nge.getDistToEnd() < curVal && nge.getDistToEnd() >= 0)
						return new Position(p.x, p.y, Position.LEVEL_REF);

			}
		}
		return null; // something went wrong
	}

	public void enemyReachedEnd(EnemyUnit enemyUnit) {
		SoundFX.play(Type.HURT);
		health -= 1;
		if (health < 1)
			SoundFX.play(Type.LOOSE);
		shake();
		removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		unitsToRemove.add(enemyUnit);
	}

	private void shake() {
		addShot(new Shake(this));
	}

	public void enemyDestroyed(EnemyUnit enemyUnit) {
		removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		SoundFX.play(Type.HIT);
		displayEffectsToAdd.add(new Explosion(enemyUnit.getPosition(),
				enemyUnit.getSize(), this));
		unitsToRemove.add(enemyUnit);
		money += enemyUnit.getMoney();
		points += enemyUnit.getPoints();
		bonus += enemyUnit.getBonus();
		if (bonus == 100) {
			health += 1;
			SoundFX.play(Type.WIN);

		}
		if (bonus == 200) {
			health += 1;
			EnemyPlayer.level2= true;
			SoundFX.play(Type.WIN);
		}
	}

	public void removeShot(DisplayEffect shot) {
		displayEffectsToRemove.add(shot);
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

	public void addShot(DisplayEffect abstractShot) {
		displayEffects.add(abstractShot);
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

	public void setHealth(int health) {
		this.health = health;
	}

	public int getBonus() {
		return bonus;
	}

	public int getPoints() {
		return points;
	}
	
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getHealth() {
		if (health <= 0)
			health = 0;
		return health;
	}

	public boolean gameover() {
		return health < 1;
	}

	
}
