package jp.reu.product.tactics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.reu.product.tactics.units.*;
import jp.reu.util.Memory;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.Range;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;
import jp.reu.util.lists.Identifer;
import jp.reu.util.lists.Lists;

public class Tactics extends Game
{
	public static final Game INSTANCE = new Tactics();

	protected static final int MAX_UNIT_OWNER = 2;

	public LazyGameTree currentNode;

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> result = new ArrayList<LazyTree>();
		StateTactics state = (StateTactics) tree.getState();
		Unit activeUnit = state.getActiveUnit();

		// Game end ?
		if (!this.winner(tree).isEmpty()) return result;

		if (activeUnit.isDead()) {
			// Add pass
			result.add(activeUnit.makeWait(state));
			return result;
		}

		// is surrounded ?
		boolean isSurrounded = activeUnit.isSurrounded(
				state.getUnits(), state.getBoardWidth(), state.getBoardHeight()); //TODO

		// collect movable points
		Set<Point> moveablePoints = activeUnit.collectMovablePoint(state);

		// Add Melee attack with move
		result.addAll(activeUnit.collectAttackMeleeMoved(state, moveablePoints));

		// Add Melee attack from current position
		result.addAll(activeUnit.collectAttackMeleeFromHere(state));

		// Add Ranged attack from current position if possible
		if (activeUnit.isRanged() && !isSurrounded) {
			result.addAll(activeUnit.collectRangedAttack(state));
		}

		// Add Move action
		result.addAll(activeUnit.collectMove(state, moveablePoints));

		// Add pass
		result.add(activeUnit.makeWait(state));


		return result;
	}

	public static List<Integer> makeWaitList(List<Unit> units)
	{
		List<Integer> waits = new ArrayList<Integer>();

		for (int i = 0; i < units.size(); i++) {
			if (!units.get(i).isDead())
				waits.add(i);
		}

		for (int i = 0; i < waits.size(); i++) {
			for (int j = i; j < waits.size(); j++) {

				if (units.get(waits.get(i)).initiative
						< units.get(waits.get(j)).initiative) {
					Collections.swap(waits, i, j);
				}
			}
		}

		return waits;
	}

	public static Set<Point> collectRange(Point p, int n, int maxWidth, int maxHeight)
	{
		Set<Point> ps = new HashSet<Point>();

		Range xRange = new Range(p.x, n, 0, maxWidth - 1);
		Range yRange = new Range(p.y, n, 0, maxHeight - 1);

		for (int y = yRange.min; y <= yRange.max; y++) {
			for (int x = xRange.min; x <= xRange.max; x++) {
				ps.add(new Point(x, y));
			}
		}

		return ps;
	}

	public static Set<Point> collectUnitPoints (List<Unit> units)
	{
		Set<Point> ps = new HashSet<Point>();

		for (Unit u : units) {
			ps.add(u.pos);
		}

		return ps;
	}

	public static Unit findAttackableUnit (List<Unit> units, final Point p, final Unit from)
	{
		return Lists.find1(units, new Identifer<Unit>() {
			@Override
			public boolean is(Unit o)
			{
				if (p.equals(o.pos) && from.isAttackAble(o)) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	public static Unit findEnemyUnit (List<Unit> units, final Point p, final int owner)
	{
		return Lists.find1(units, new Identifer<Unit>() {
			@Override
			public boolean is(Unit o)
			{
				if (o.pos.equals(p) && o.isEnemyUnit(owner)) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	/**
	 * @return fail -1 or index of units
	 */
	public static int findEnemyUnitIndex(List<Unit> units, Point p, int owner)
	{
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).pos.equals(p) && units.get(i).owner != owner)
				return i;
		}

		return -1;
	}

	public static StateTactics nextTurn(StateTactics state, List<Unit> units)
	{
		List<Integer> newWaitList = new ArrayList<Integer>(state.getWait0());

		// remove first index
		newWaitList.remove(0);

		if (newWaitList.isEmpty()) {
			newWaitList = makeWaitList(units);
		}

		return new StateTactics(
				state.getBoardWidth(), state.getBoardHeight(),
				units,
				newWaitList, null); // TODO パス後のリストの生成

	}

	public List<Unit> makeMovedUnits(List<Unit> units, int index, Point p)
	{
		List<Unit> newUnits = Lists.deepCopyArrayListOnly(units, index);
		newUnits.get(index).pos = p;

		return newUnits;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
		StateTactics state = (StateTactics) tree.getState();
		List<Integer> win = new ArrayList<Integer>();

		out:
		for (int p = 0; p < MAX_UNIT_OWNER; p++) {
			for (Unit u : state.getUnits()) {
				if (p == u.owner) {
					if (u.isDead()) {
						// keep looping
					} else {
						continue out;
					}
				}
			}

			if (p == 0)	win.add(1);
			else		win.add(0);
		}

		return win;
	}

	/**
	 * @return sum of unit hp
	 */
	@Override
	public int scoreState(LazyGameTree tree, int player)
	{
		StateTactics state = (StateTactics) tree.getState();
		int score = 0;

		for (Unit u : state.getUnits()) {
			if (u.owner == player) {
				score += u.hp;
			} else {
				score -= u.hp;
			}
		}

		return score;
	}

	@Override
	public List<Integer> play(LazyGameTree tree, AI[] ais)
	{
		Memory.getInstance().print();
		Memory.getInstance().desGC(); Memory.getInstance().print();
		// TODO Auto-generated method stub
		return super.play(tree, ais);
	}

	private Tactics()
	{
		super();
	}

	public Tactics(int width, int height, Unit[] units)
	{
		this.currentNode = new LazyGameTree(
				Tactics.INSTANCE,
				new StateTactics(width, height, Arrays.asList(units)));
	}

	public static void main(String[] args)
	{
		List<Unit> units = new ArrayList<Unit>();

//		units.add(new UnitFootman(0, new Point(0, 0)));
//		units.add(new UnitFootman(0, new Point(1, 1)));
//		units.add(new UnitFootman(1, new Point(1, 0)));
//		units.add(new UnitFootman(1, new Point(0, 1)));

//		units.add(new UnitArcher(0, new Point(0, 0)));
//		units.add(new UnitArcher(0, new Point(0, 1)));
//		units.add(new UnitArcher(1, new Point(1, 1)));
//		units.add(new UnitArcher(1, new Point(2, 3)));

		units.add(new UnitFootman(0, new Point(1, 0)));
		units.add(new UnitFootman(0, new Point(1, 1)));
		units.add(new UnitFootman(0, new Point(1, 2)));
//		units.add(new UnitFootman(0, new Point(1, 3)));
//		units.add(new UnitFootman(0, new Point(1, 4)));
		units.add(new UnitArcher(0, new Point(0, 0)));
		units.add(new UnitArcher(0, new Point(0, 1)));
//		units.add(new UnitArcher(0, new Point(0, 2)));
//		units.add(new UnitArcher(0, new Point(0, 3)));
//		units.add(new UnitArcher(0, new Point(0, 4)));
		units.add(new UnitFootman(1, new Point(6, 0)));
		units.add(new UnitFootman(1, new Point(6, 1)));
		units.add(new UnitFootman(1, new Point(6, 2)));
//		units.add(new UnitFootman(1, new Point(6, 3)));
//		units.add(new UnitFootman(1, new Point(6, 4)));
		units.add(new UnitArcher(1, new Point(7, 0)));
		units.add(new UnitArcher(1, new Point(7, 1)));
//		units.add(new UnitArcher(1, new Point(7, 2)));
//		units.add(new UnitArcher(1, new Point(7, 3)));
//		units.add(new UnitArcher(1, new Point(7, 4)));

		StateTactics state = new StateTactics(8, 5, units);
		LazyGameTree tree = new LazyGameTree(Tactics.INSTANCE, state);
//		new Tactics().play(tree);
		new Tactics().play(tree, new AI[] {
				new AI(INSTANCE, 3),
				new AI(INSTANCE, 3),
		});
	}
}
