package jp.reu.product.tactics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.reu.product.tactics.actions.*;
import jp.reu.product.tactics.units.*;
import jp.reu.util.Lists;
import jp.reu.util.game.ActionPass;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.Range;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

public class Tactics extends Game
{
	public static final Game INSTANCE = new Tactics();

	protected static final int MAX_UNIT_OWNER = 2;

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> result = new ArrayList<LazyTree>();
		List<LazyTree> resultMoves = new ArrayList<LazyTree>();

		StateTactics state = (StateTactics) tree.getState();
		Unit activeUnit = state.getActiveUnit();
		int activeUnitIndex = state.getActiveUnitIndex();

		int attackTartgetIndex;

		Set<Point> newPoints;
		Set<Point> neighbors;
		Unit movedUnit;
		List<Unit> newUnits;
		StateTactics newState;


		//// Game end ? ////

		if (!this.winner(tree).isEmpty()) return result;

		//// is surrounded ? ////

		boolean isSurrounded = activeUnit.isSurrounded(
				state.units, state.boardWidth, state.boardWidth);

		//// Add Move and Melee attack ////

		newPoints = collectRange(
				activeUnit.pos,
				activeUnit.speed,
				state.boardWidth,
				state.boardHeight);

		// Filter point of units
		newPoints.removeAll(collectUnitPoints(state.units));

		// for movable points
		for (Point p : newPoints) {

			//// Add Move action ////

			newUnits = this.makeMovedUnits(state.units, state.getActiveUnitIndex(), p);
			newState = nextTurn(state, newUnits);

			// add move action
			resultMoves.add(new LazyGameTree(new ActionMove(activeUnit, activeUnit.pos, p),
					newState));


			//// Add Melee attack action ////

			// Skip if unit is ranged and is not surrounded
			if (activeUnit.isRanged() && !isSurrounded) continue;

			movedUnit = newUnits.get(state.getActiveUnitIndex());
			neighbors = collectRange(
					movedUnit.pos,
					1,
					state.boardWidth,
					state.boardHeight);

			// For neighbors
			for (Point pAttack : neighbors) {
				attackTartgetIndex = findEnemyUnitIndex(newUnits, pAttack, activeUnit.owner);

				// find it!
				if (attackTartgetIndex >= 0 &&
						movedUnit.isAttackAble(newUnits.get(attackTartgetIndex))) {
					result.add(
							// attack from active moved unit
							newUnits.get(activeUnitIndex).attackMelee(
									newUnits, activeUnitIndex, attackTartgetIndex, state));
				}
			}
		}

		//// Add Melee attack action from current position ////

		neighbors = collectRange(
				activeUnit.pos,
				1,
				state.boardWidth,
				state.boardHeight);

		// For neighbors
		for (Point target : neighbors) {
			attackTartgetIndex = findEnemyUnitIndex(state.units, target, activeUnit.owner);

			// find it!
			if (attackTartgetIndex >= 0 &&
					activeUnit.isAttackAble(state.units.get(attackTartgetIndex))) {

				result.add(
						// attack from active moved unit
						activeUnit.attackMelee(
								state.units, activeUnitIndex, attackTartgetIndex, state));
			}
		}

		//// Add Ranged attack if possible ////

		if (activeUnit.isRanged() && !isSurrounded) {
			result.addAll(
					activeUnit.collectRangedAttackResult(state));
		}

		//// Marge move branches to main

		result.addAll(resultMoves);


		//// Add pass ////

		result.add(new LazyGameTree(
				new ActionPass(),
				nextTurn(state, state.units)));

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
		List<Integer> newWaitList = new ArrayList<Integer>(state.wait0);

		// remove first index
		newWaitList.remove(0);

		if (newWaitList.isEmpty()) {
			newWaitList = makeWaitList(units);
		}

		return new StateTactics(
				state.boardWidth, state.boardHeight,
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
			for (Unit u : state.units) {
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

		for (Unit u : state.units) {
			if (u.owner == player) {
				score += u.hp;
			} else {
				score -= u.hp;
			}
		}

		return score;
	}

	public static void main(String[] args)
	{
		List<Unit> units = new ArrayList<Unit>();

		units.add(new UnitFootman(0, new Point(1, 0)));
		units.add(new UnitFootman(0, new Point(1, 2)));
		units.add(new UnitFootman(0, new Point(1, 4)));
		units.add(new UnitArcher(0, new Point(0, 1)));
		units.add(new UnitArcher(0, new Point(0, 3)));

		units.add(new UnitFootman(1, new Point(6, 0)));
		units.add(new UnitFootman(1, new Point(6, 2)));
		units.add(new UnitFootman(1, new Point(6, 4)));
		units.add(new UnitArcher(1, new Point(7, 1)));
		units.add(new UnitArcher(1, new Point(7, 3)));

		StateTactics state = new StateTactics(8, 5, units);
		LazyGameTree tree = new LazyGameTree(Tactics.INSTANCE, state);
		new Tactics().play(tree, new AI[] {
				null,
				new AI(INSTANCE, 3)
		});
	}
}
