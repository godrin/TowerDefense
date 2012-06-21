package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.IRenderer;
import com.cdm.view.LevelScreen;
import com.cdm.view.Position;
import com.cdm.view.Selector;
import com.cdm.view.elements.Grid.GridElement;
import com.cdm.view.elements.paths.PathFinder;
import com.cdm.view.elements.paths.PathPos;
import com.cdm.view.elements.shots.DisplayEffect;
import com.cdm.view.elements.shots.Explosion;
import com.cdm.view.elements.shots.Shake;
import com.cdm.view.elements.shots.ZoomInEffect;
import com.cdm.view.elements.units.PlayerUnit;
import com.cdm.view.elements.units.Unit;
import com.cdm.view.elements.units.Unit.UnitType;
import com.cdm.view.enemy.EnemyPlayer;
import com.cdm.view.enemy.EnemyUnit;

public class Level {
	private Grid grid;
	private Selector selector = null;
	private EnemyPlayer player;
	private float speedFactor = 2.0f;
	private List<Unit> units = new ArrayList<Unit>();
	private Set<Unit> unitsToRemove = new TreeSet<Unit>();
	private PlayerState playerState;

	private List<DisplayEffect> displayEffects = new ArrayList<DisplayEffect>();
	private List<DisplayEffect> displayEffectsToRemove = new ArrayList<DisplayEffect>();
	private List<DisplayEffect> displayEffectsToAdd = new ArrayList<DisplayEffect>();
	private GridDrawing gridDrawing;
	private LevelFinishedListener levelFinishedListener;
	private Random random = new Random();

	public Level(Grid pGrid, LevelScreen pfinishedListener, PlayerState pState) {
		playerState = pState;
		grid = pGrid;
		Position.LEVEL_REF.setWidth(grid.getW());
		Position.LEVEL_REF.setHeight(grid.getH());

		levelFinishedListener = pfinishedListener;
		player = new EnemyPlayer(levelFinishedListener);
		player.setLevel(this);
		gridDrawing = new GridDrawing(grid);

		PathFinder.breadthSearch(grid, PathFinder.GOAL_ACCESSOR,
				getEnemyStartPosition(), getEnemyEndPosition(), null, false);
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
		for (Unit unit : units) {
			if (unit != null)
				unit.drawAfter(renderer);
		}

		for (DisplayEffect shot : displayEffects) {
			shot.draw(renderer);
		}
		if (selector != null)
			selector.draw(renderer);

	}

	private void drawBox(IRenderer renderer) {
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
		PathPos pathPos = new PathPos(lpos);

		// check if way is still free then
		if (dragElement instanceof PlayerUnit) {
			if (!isFreeForNewUnit(pathPos))
				return false;
		}

		GridElement gridElement = grid.get(lpos);
		if (gridElement != null)
			if (gridElement.isEmpty() || dragElement instanceof EnemyUnit) {

				dragElement.setLevel(this);
				dragElement.setPosition(lpos, true);
				units.add(dragElement);
				setMoney(getMoney() - dragElement.getCost());

				if (dragElement instanceof PlayerUnit) {
					PathFinder.breadthSearch(grid, PathFinder.GOAL_ACCESSOR,
							getEnemyStartPosition(), getEnemyEndPosition(),
							null, false);
					List<PathPos> playerUnitPositions = new ArrayList<PathPos>();
					for (Unit unit : units) {
						if (!(unit instanceof EnemyUnit)) {
							playerUnitPositions.add(new PathPos(unit
									.getPosition()));
						}
					}

					PathFinder.breadthSearch(grid,
							PathFinder.UNITDIST_ACCESSOR, (PathPos) null,
							playerUnitPositions, null, false);

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

		GridElement gridElement = grid.get(p);
		if (gridElement != null) {
			if (gridElement.contains(unit)) {
				// System.out.println("OK FOUND unit");
			} else {
				throw new RuntimeException("not found");
			}
			gridElement.remove(unit);
		} else {
			// System.out.println("NOT FOUND" + x0 + " " + y0);
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

	// use more than one start position
	public List<PathPos> getEnemyStartPosition() {
		return grid.getEnemyStartPositions();
	}

	public List<PathPos> getEnemyEndPosition() {
		return grid.getEnemyEndPosition();
	}

	public boolean isFreeForNewUnit(PathPos pos) {
		List<PathPos> from = getEnemyStartPosition();
		List<PathPos> to = getEnemyEndPosition();

		if (!grid.isFree(pos))
			return false;

		return PathFinder.breadthSearch(grid, PathFinder.TMP_ACCESSOR, from,
				to, pos, true);
	}

	public Position getNextPos(Position alignToGrid) {
		PathPos from = new PathPos(alignToGrid);
		if (true) {
			int curVal = 1000;
			GridElement ge = grid.get(from.tmp());
			if (ge != null)
				curVal = ge.getDistToEnd();
			for (PathPos p : from.next()) {
				if (grid.getEnemyEndPosition().contains(p))
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

		PathPos current = new PathPos(pos);
		GridElement ge0 = grid.get(pos);

		int curVal = 1000;
		if (ge0 != null)
			curVal = ge0.getDistToUnit();
		if (curVal == 0) {
			if (getPlayerUnitAt(pos) != null)
				return new Position(-3, -3, Position.LEVEL_REF);
			else
				return new Position(pos.x + 19, pos.y, Position.LEVEL_REF);
		} else if (curVal == -1) {
			return new Position(pos.x + 5, pos.y, Position.LEVEL_REF);
		}
		for (PathPos neighbor : current.next()) {
			GridElement ge = grid.get(neighbor.tmp());
			if (ge != null)
				if (ge.getDistToUnit() < curVal)
					return neighbor.tmp();

		}
		return null;

	}

	public void enemyReachedEnd(EnemyUnit enemyUnit) {
		playerState.hurt();
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
		playerState.enemyDestroyed(enemyUnit);
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

	public Unit getUnitAt(Position target, Class<PlayerUnit> klass) {
		GridElement gridElement = grid.get(target);
		if (gridElement != null) {

			return gridElement.getFirstUnit(klass);
		}
		return null;
	}

	public PlayerUnit getPlayerUnitAt(Position target) {
		GridElement gridElement = grid.get(target);
		if (gridElement != null) {
			return gridElement.getPlayerUnit();
		}
		return null;
	}

	public int getMoney() {
		return playerState.getMoney();
	}

	public void setMoney(int money) {
		playerState.setMoney(money);
	}

	public void setHealth(int health) {
		playerState.setHealth(health);
	}

	public int getBonus() {
		return playerState.getBonus();
	}

	public int getPoints() {
		return playerState.getPoints();
	}

	public void setBonus(int bonus) {
		playerState.setBonus(bonus);
	}

	public int getHealth() {
		return playerState.getHealth();
	}

	public boolean gameover() {
		return playerState.gameover();
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
		PathFinder.breadthSearch(grid, PathFinder.UNITDIST_ACCESSOR,
				(PathPos) null, playerUnitPositions, null, false);
	}

	public void remove(Unit unit) {
		if (units.contains(unit))
			unitsToRemove.add(unit);
	}

	public Position getSomeEnemyEndPosition() {

		return new Position(getEnemyEndPosition().get(
				random.nextInt(getEnemyEndPosition().size())),
				Position.LEVEL_REF);
	}

}
