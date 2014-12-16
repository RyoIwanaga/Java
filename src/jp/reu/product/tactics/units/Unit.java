package jp.reu.product.tactics.units;

import java.awt.Point;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.reu.product.tactics.StateTactics;
import jp.reu.product.tactics.Tactics;
import jp.reu.product.tactics.actions.ActionAttackMelee;
import jp.reu.util.Lists;
import jp.reu.util.game.Clone;
import jp.reu.util.game.LazyGameTree;

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

	public boolean isAttackAble(Unit u)
	{
		return this.isEnemyUnit(u) && u.isNotDead();
	}

	public boolean isRanged()
	{
		return this instanceof UnitRanged;
	}


	//// Actions ////

	public LazyGameTree attackMelee(List<Unit> units, int from, int target, StateTactics oldState)
	{
		List<Unit> newUnits = Lists.deepCopyArrayListOnly(
				units, new int[] {from, target});

		Unit fromUnit = newUnits.get(from);
		Unit targetUnit = newUnits.get(target);

		targetUnit.hp -= fromUnit.getMeleeDamage();

		return new LazyGameTree(
				new ActionAttackMelee(
						units.get(from),
						units.get(target),
						units.get(from).pos,
						fromUnit.getMeleeDamage()
						),
				Tactics.nextTurn(oldState, newUnits));
	}

	public int getMeleeDamage()
	{
		return this.damage;
	}

	public List<LazyGameTree> collectRangedAttackResult (StateTactics oldState)
	{
		return new ArrayList<LazyGameTree>();
	}

	public boolean isSurrounded(List<Unit> units, int width, int height)
	{
		Set<Point> neighbors = Tactics.collectRange(
				this.pos, 1,
				width,
				height);

		// For neighbors
		for (Point p : neighbors) {
			int targetIndex = Tactics.findEnemyUnitIndex(units, p, this.owner);

			if (targetIndex >= 0) return true;
		}

		return false;
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
}
