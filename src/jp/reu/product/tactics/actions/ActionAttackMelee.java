package jp.reu.product.tactics.actions;

import java.awt.Point;

import jp.reu.product.tactics.units.Unit;
import jp.reu.util.game.Action;

public class ActionAttackMelee extends Action 
{
	Unit unit;
	Unit target;
	Point attackFrom;
	
	public ActionAttackMelee(Unit unit, Unit target, Point attackFrom) {
		this.unit = unit;
		this.target = target;
		this.attackFrom = attackFrom;
	}

	@Override
	public void print() {
		System.out.printf("\"%s\" attack \"%s\" from (%d %d)\n",
				unit.name, target.name, 
				attackFrom.x, attackFrom.y);
	}
}
