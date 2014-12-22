package jp.reu.product.tactics.actions;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;

public class ActionAttackRaged extends ActionAttackMelee
{
	public ActionAttackRaged(Unit unit, Unit target, Point attackFrom, int damage) {
		super(unit, target, attackFrom, damage, -1);

		this.text = "shoot";
	}
}
