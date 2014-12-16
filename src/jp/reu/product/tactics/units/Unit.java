package jp.reu.product.tactics.units;

import java.awt.Point;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

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
		System.out.printf("[%d %d] %s(%d): hp=%d/%d dmg=%d spd=%d ini=%d \n",
				pos.x, pos.y, 
				name,
				owner,
				hp,
				hpMax,
				damage,
				speed,
				initiative
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
	
	public boolean isDead()
	{
		return this.hp <= 0;
	}
	
	public boolean isNotDead() 
	{ 
		return !isDead(); 
	}
	
	

	//// Actions ////
	
	public LazyGameTree attackMelee(List<Unit> units, int from, int target, StateTactics oldState)
	{
		List<Unit> newUnits = Lists.deepCopyArrayListOnly(
				units, new int[] {from, target});
		
		Unit fromUnit = newUnits.get(from);
		Unit targetUnit = newUnits.get(target);
		
		targetUnit.hp -= fromUnit.damage;

		return new LazyGameTree(
				new ActionAttackMelee(
						units.get(from),
						units.get(target),
						units.get(from).pos
						), 
				Tactics.nextTurn(oldState, newUnits));
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
