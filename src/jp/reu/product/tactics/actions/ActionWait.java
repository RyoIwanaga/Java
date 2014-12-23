package jp.reu.product.tactics.actions;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.Action;

public class ActionWait extends Action
{
	public Unit unit;
	public int unitIndex;

	public ActionWait(Unit unit, int unitIndex) {
		super();
		this.unit = unit;
		this.unitIndex = unitIndex;
	}

	@Override
	public void print() {
		System.out.printf("Wait.");
	}
}
