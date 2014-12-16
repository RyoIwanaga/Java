package jp.reu.product.tactics.actions;

import java.awt.Point;

import jp.reu.product.tactics.units.Unit;
import jp.reu.util.game.Action;

public class ActionAttackRaged extends ActionAttackMelee
{
	public ActionAttackRaged(Unit unit, Unit target, Point attackFrom, int damage) {
		super(unit, target, attackFrom, damage);

		this.text = "shoot";
	}
}
