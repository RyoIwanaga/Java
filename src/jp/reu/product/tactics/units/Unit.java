package jp.reu.product.tactics.units;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.reu.product.tactics.StateTactics;
import jp.reu.product.tactics.Tactics;
import jp.reu.product.tactics.actions.ActionAttackMelee;
import jp.reu.product.tactics.actions.ActionMove;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.ActionPass;
import jp.reu.util.game.Clone;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.lists.Lists;

public class Unit implements Cloneable, Clone<Unit> {

	public String name;
	public int hp;
	public int hpMax;
	public int damage;
	public int speed;
	public int initiative;
	public int owner;
	public Point pos;

	public Unit(String name, int hp, int damage, int speed, int initiative, int owner, Point pos) {
		super();

		this.name = name;
		this.hp = hp;
		this.hpMax = hp;
		this.damage = damage;
		this.speed = speed;
		this.initiative = initiative;
		this.owner = owner;
		this.pos = pos;
	}

	public void print() {
		System.out.printf("[%d %d] %s: hp=%d/%d dmg=%d spd=%d ini=%d \n",
				this.pos.x, this.pos.y,
				this.getDisplayName(),
				this.hp,
				this.hpMax,
				this.damage,
				this.speed,
				this.initiative
				);
	}

	public int getInitiative() {
		return this.initiative;
	}

	public int getX() {
		return this.pos.x;
	}

	public int getY() {
		return this.pos.y;
	}

	public String getDisplayName()
	{
		if (this.owner == 0) {
			return this.name.toLowerCase();
		}
		else {
			return this.name.toUpperCase();
		}
	}

	public int getMeleeDamage()
	{
		return this.damage * this.hp / this.hpMax;
	}

	public boolean isDead()
	{
		return this.hp <= 0;
	}

	public boolean isNotDead()
	{
		return !this.isDead();
	}

	public boolean isEnemyUnit(int p)
	{
		return this.owner != p;
	}

	public boolean isEnemyUnit(Unit u)
	{
		return this.owner != u.owner;
	}

	public boolean isAttackAble(Unit target)
	{
		return this.isEnemyUnit(target) && target.isNotDead();
	}

	public boolean isRanged()
	{
		return this instanceof UnitRanged;
	}

	public boolean isSurrounded(List<Unit> units, int width, int height)
	{
		Set<Point> neighbors = Tactics.collectRange(
				this.pos, 1,
				width,
				height);

		// For neighbors
		for (Point p : neighbors) {
			// find it
			if (Tactics.findEnemyUnit(units, p, this.owner) != null)
				return true;
		}

		return false;
	}

	//// Actions ////

	public LazyGameTree makeAttackMelee(StateTactics s, List<Unit> units, int from, int target, Point fromP)
	{
		List<Unit> newUnits = Lists.deepCopyArrayListOnly(
				units, new int[] {from, target});

		int dealDamage, counterDamage;

		Unit fromUnit = newUnits.get(from);
		Unit targetUnit = newUnits.get(target);
		if (fromP != null)
			fromUnit.pos = new Point(fromP);
		dealDamage = fromUnit.getMeleeDamage();

		targetUnit.hp -= dealDamage;

		if (targetUnit.isNotDead()) {
			counterDamage = targetUnit.getMeleeDamage();
			fromUnit.hp -= counterDamage;
		} else {
			counterDamage = -1;
		}

		return new LazyGameTree(
				new ActionAttackMelee(
						units.get(from),
						units.get(target),
						fromP == null ?
								s.getActiveUnit().pos : fromP,
						dealDamage,
						counterDamage
						),
				Tactics.nextTurn(s, newUnits));
	}

	public LazyGameTree makeAttackMelee(StateTactics s, List<Unit> units, int from, int target)
	{
		return this.makeAttackMelee(s, units, from, target, null);
	}

	public LazyGameTree makeWait(StateTactics s)
	{
		return new LazyGameTree(
				new ActionPass(),
				Tactics.nextTurn(s, Lists.deepCopyArrayList(s.getUnits())));
	}

	public Set<Point> collectMovablePoint(StateTactics s)
	{
		Set<Point> moveables = Tactics.collectRange(
				this.pos,
				this.speed,
				s.getBoardWidth(),
				s.getBoardHeight());

		// Filter point of units
		moveables.removeAll(Tactics.collectUnitPoints(s.getUnits()));

		return moveables;
	}

	public List<LazyGameTree> collectMove (StateTactics s, Set<Point> newPoints)
	{
		List<LazyGameTree> acc = new ArrayList<LazyGameTree>();
		List<Unit> copyUnits;

		for (Point newPoint : newPoints) {
			copyUnits = Lists.deepCopyArrayListOnly(s.getUnits(), s.getActiveUnitIndex());
			copyUnits.get(s.getActiveUnitIndex()).pos = new Point(newPoint);

			acc.add(new LazyGameTree(
					new ActionMove(
						s.getActiveUnit(),
						s.getActiveUnit().pos,
						newPoint),
					Tactics.nextTurn(s, copyUnits)));
		}

		return acc;
	}

	public List<LazyGameTree> collectAttackMeleeMoved (StateTactics s, Set<Point> points)
	{
		List<LazyGameTree> acc = new ArrayList<LazyGameTree>();
		int targetIndex;

		// for move point
		for (Point moveP : points) {
			// collect neighbor
			Set<Point> neighbors = Tactics.collectRange(
					moveP,
					1,
					s.getBoardWidth(),
					s.getBoardHeight());

			// For neighbors from move point
			for (Point p : neighbors) {
				targetIndex = s.getUnits().indexOf(
						Tactics.findAttackableUnit(s.getUnits(), p, s.getActiveUnit()));

				// find it!
				if (targetIndex >= 0) {
					acc.add(this.makeAttackMelee(
							s,
							s.getUnits(),
							s.getActiveUnitIndex(),
							targetIndex,
							moveP));
				}
			}
		}


		return acc;
	}

	public List<LazyGameTree> collectAttackMeleeFromHere (StateTactics s)
	{
		List<LazyGameTree> acc = new ArrayList<LazyGameTree>();
		int targetIndex;

		// collect neighbor
		Set<Point> neighbors = Tactics.collectRange(
				s.getActiveUnit().pos,
				1,
				s.getBoardWidth(),
				s.getBoardHeight());

		// For neighbors
		for (Point p : neighbors) {
			targetIndex = s.getUnits().indexOf(
					Tactics.findAttackableUnit(s.getUnits(), p, s.getActiveUnit()));

			// find it!
			if (targetIndex >= 0) {
				acc.add(this.makeAttackMelee(
						s,
						s.getUnits(),
						s.getActiveUnitIndex(),
						targetIndex));
			}
		}

		return acc;
	}

	public List<LazyGameTree> collectRangedAttack (StateTactics oldState)
	{
		return new ArrayList<LazyGameTree>();
	}

	@Override
	public Unit clone()
	{
		try {
			return (Unit)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	public static void main(String[] args)
	{
		Unit a = new Unit("hoge", 1, 2, 3, 4, 5, new Point(1, 2));
		Unit b = new Unit("hoge", 1, 2, 3, 4, 5, new Point(1, 2));

		List<Unit> units = new ArrayList<Unit>();
		units.add(a);
		units.add(b);
		System.out.println(units);

//		ArrayList<Unit> units1 = Lists.deepCopyArrayList((List<Clone>)units);
//		units1.add(a.clone());
//		System.out.println(units1);

		List<Unit> units1 = Lists.deepCopyArrayList(units);
		units1.get(0).print();
		units1.get(1).print();
		System.out.println(units1);

		List<Unit> units2 = Lists.deepCopyArrayListOnly(units, 1);
		units2.get(0).print();
		units2.get(1).print();
		System.out.println(units2);
	}

	public int getRagedDamage()
	{
		return 0;
	}
}
