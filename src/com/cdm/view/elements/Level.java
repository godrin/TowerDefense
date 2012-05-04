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
import com.cdm.view.elements.Grid.CellType;
import com.cdm.view.elements.Grid.GridElement;
import com.cdm.view.elements.paths.PathFinder;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.shots.DisplayEffect;
import com.cdm.view.elements.shots.Explosion;
import com.cdm.view.elements.shots.Shake;
import com.cdm.view.elements.shots.ZoomInEffect;
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
	private GridDrawing gridDrawing;

	public Level(int w, int h, int endY) {
		grid = new Grid(w, h, endY);

		grid.getElement(2, 4).setCellType(CellType.BLOCK);
		grid.getElement(3, 4).setCellType(CellType.EMPTY);
		Position.LEVEL_REF.setWidth(w);
		Position.LEVEL_REF.setHeight(h);
		player = new EnemyPlayer();
		player.setLevel(this);
		gridDrawing = new GridDrawing(grid);

		boxDrawer = new BoxDrawing(new Position(-1, grid.endY(),
				Position.LEVEL_REF), getEnemyEndPosition(), grid.getH());

		PathFinder.breadthSearch(grid, PathFinder.GOAL_ACCESSOR,
				getEnemyStartPositionPlusOne(), new PathPos(
						getEnemyEndPosition()), null, false);
		displayEffects.add(new ZoomInEffect(this));
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
			removeMeFromGrid(unit.getPosition(), unit);
		}
		unitsToRemove.clear();
		for (DisplayEffect shot : displayEffectsToRemove) {
			displayEffects.remove(shot);
		}
		displayEffectsToRemove.clear();
		displayEffects.addAll(displayEffectsToAdd);
		displayEffectsToAdd.clear();
		boxDrawer.move(time);
		gridDrawing.move(time);
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
		if (false)
			boxDrawer.draw(renderer);
		gridDrawing.draw(renderer);
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

		GridElement gridElement = grid.get(lpos);

		if (gridElement.isEmpty() || dragElement instanceof EnemyUnit) {

			dragElement.setLevel(this);
			dragElement.setPosition(lpos, true);
			units.add(dragElement);
			setMoney(getMoney() - dragElement.getCost());

			// FIXME: insert abstract class "PlayerUnit" for all "player units"
			if (!(dragElement instanceof EnemyUnit)) {
				PathFinder.breadthSearch(grid, PathFinder.GOAL_ACCESSOR,
						getEnemyStartPositionPlusOne(), new PathPos(
								getEnemyEndPosition()), null, false);
				List<PathPos> playerUnitPositions = new ArrayList<PathPos>();
				for (Unit unit : units) {
					if (!(unit instanceof EnemyUnit)) {
						playerUnitPositions
								.add(new PathPos(unit.getPosition()));
					}
				}

				PathFinder.breadthSearch(grid, PathFinder.UNITDIST_ACCESSOR,
						null, playerUnitPositions, null, false);

			}
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

		System.out.println("REMOVE " + unit + " at " + x0 + "," + y0);

		GridElement gridElement = grid.get(p);
		if (gridElement != null) {
			if (gridElement.contains(unit)) {
				System.out.println("OK FOUND unit");
			} else {
				// throw new RuntimeException("not found");
			}
			gridElement.remove(unit);
		} else {
			System.out.println("NOT FOUND" + x0 + " " + y0);
			throw new RuntimeException("not found");
		}
	}

	public void addMeToGrid(Position p, Unit unit) {

		GridElement gridElement = grid.get(p);
		if (gridElement != null)
			gridElement.add(unit);
	}

	public boolean hasEnemies() {
		for (Unit unit : units) {
			if (unit instanceof EnemyUnit)
				return true;
		}
		return false;
	}

	public Position getEnemyStartPosition() {
		return new Position(0, grid.endY(), Position.LEVEL_REF);
	}

	public Position getEnemyStartPosition2() {
		return new Position(0, grid.endY() - 4, Position.LEVEL_REF);
	}

	public PathPos getEnemyStartPositionPlusOne() {
		return new PathPos(0, grid.endY(), -1);
	}

	public Position getEnemyEndPosition() {
		return new Position(grid.getW() - 1, grid.endY(), Position.LEVEL_REF);
	}

	public boolean isFreeForNewUnit(Position pos) {
		PathPos from = getEnemyStartPositionPlusOne();
		PathPos to = new PathPos(getEnemyEndPosition());

		PathPos ignore = new PathPos(pos);
		return PathFinder.breadthSearch(grid, PathFinder.TMP_ACCESSOR, from,
				to, ignore, true);
	}

	public Position getNextPos(Position alignToGrid) {
		Position finish = getEnemyEndPosition();
		PathPos from = new PathPos(alignToGrid);
		PathPos finishPos = new PathPos(finish);
		finish.y += 4;
		if (true) {
			int curVal = 1000;
			GridElement ge = grid.get(from.tmp());
			if (ge != null)
				curVal = ge.getDistToEnd();
			for (PathPos p : from.next()) {
				if (finishPos.equals(p))
					return new Position(p.x, p.y, Position.LEVEL_REF);
				GridElement nge = grid.get(p.tmp());
				if (nge != null)
					if (nge.getDistToEnd() < curVal && nge.getDistToEnd() >= 0)
						return new Position(p.x, p.y, Position.LEVEL_REF);

			}
		}
		return null; // something went wrong
	}

	public Position getNextStepToUnit(Position pos) {

		if (true) {
			PathPos current = new PathPos(pos);
			GridElement ge0 = grid.get(pos);

			int curVal = 1000;
			if (ge0 != null)
				curVal = ge0.getDistToUnit();
			if (curVal == 0){
				return new Position(0, 0, Position.LEVEL_REF);
			} else	if (curVal == -1){
				return new Position(getEnemyEndPosition());}
			for (PathPos neighbor : current.next()) {
				GridElement ge = grid.get(neighbor.tmp());
				if (ge != null)
					if (ge.getDistToUnit() < curVal)
						return neighbor.tmp();

			}
			return null;
		}

		Position finish = getEnemyEndPosition();

		PathPos from = new PathPos(pos);
		PathPos finishPos = new PathPos(finish);
		finish.y += 4;

		if (true) {
			GridElement ge = grid.get(from.tmp());

			int curVal = 1000;
			if (ge != null)
				curVal = ge.getDistToUnit();
			for (PathPos p : from.next()) {
				if (finishPos.equals(p))
					return new Position(p.x, p.y, Position.LEVEL_REF);
				GridElement nge = grid.get(p.tmp());
				if (nge != null)
					if (nge.getDistToUnit() < curVal
							&& nge.getDistToUnit() >= 0)
						return new Position(p.x, p.y, Position.LEVEL_REF);
					else if (curVal == 0)
						return new Position(0, 0, Position.LEVEL_REF);
			}
		}
		return null;
	}

	public void enemyReachedEnd(EnemyUnit enemyUnit) {
		SoundFX.play(Type.HURT);
		health -= 1;
		if (health < 1)
			SoundFX.play(Type.LOOSE);
		shake();
		// removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
		unitsToRemove.add(enemyUnit);
	}

	private void shake() {
		addShot(new Shake(this));
	}

	public void enemyDestroyed(EnemyUnit enemyUnit) {
		// removeMeFromGrid(enemyUnit.getPosition(), enemyUnit);
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
		if (bonus == 250) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 500) {
			health += 1;
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
		GridElement gridElement = grid.get(target);
		if (gridElement != null) {
			return gridElement.getFirstEnemyUnit();
		}
		return null;
	}

	public Unit getUnitAt(Position target) {
		GridElement gridElement = grid.get(target);
		if (gridElement != null) {
			return gridElement.getFirstUnit();
		}
		return null;
	}
	
	public Unit getPlayerUnitAt(Position target) {
		GridElement gridElement = grid.get(target);
		if (gridElement != null) {
			return gridElement.getPlayerUnit();
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

	public void unitDestroyed(Position position, Unit unit) {
		SoundFX.play(Type.HIT);
		displayEffectsToAdd.add(new Explosion(position, unit.getSize(), this));
		unitsToRemove.add(unit);
		List<PathPos> playerUnitPositions = new ArrayList<PathPos>();
		for (Unit unit1 : units) {
			if (!(unit1 instanceof EnemyUnit)) {
				playerUnitPositions.add(new PathPos(unit1.getPosition()));
			}
		}
		PathFinder.breadthSearch(grid, PathFinder.UNITDIST_ACCESSOR, null,
				playerUnitPositions, null, false);
	}

}
