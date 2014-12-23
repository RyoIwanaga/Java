package jp.reu.product.tactics.actions.attack;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.Action;

public abstract class ActionAttack extends Action
{
	public Unit unit;
	public Unit target;
	public int unitIndex;
	public int targetIndex;
	public Point attackFrom;
	public int damage;

	public ActionAttack(Unit unit, Unit target, int unitIndex, int targetIndex, Point attackFrom,
			int damage) {
		super();
		this.unit = unit;
		this.target = target;
		this.unitIndex = unitIndex;
		this.targetIndex = targetIndex;
		this.attackFrom = attackFrom;
		this.damage = damage;
	}
}
