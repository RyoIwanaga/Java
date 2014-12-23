package jp.reu.product.tactics.actions;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.Action;

public class ActionMove extends Action
{
	public Unit unit;
	public int unitIndex;
	public Point from;
	public Point  to;

	public ActionMove(Unit unit, int unitIndex, Point from, Point to) {
		super();
		this.unit = unit;
		this.unitIndex = unitIndex;
		this.from = from;
		this.to = to;
	}

	@Override
	public void print() {
		System.out.printf("Move (%d %d) -> (%d %d)\n",
				this.from.x, this.from.y, this.to.x, this.to.y);
	}
}
