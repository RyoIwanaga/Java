package jp.reu.product.tactics.actions.attack;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.Action;

public abstract class ActionAttack extends Action
{
	public Unit unit;
	public Unit target;
	public Point attackFrom;
	public int damage;
}
