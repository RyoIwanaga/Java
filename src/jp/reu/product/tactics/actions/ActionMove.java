package jp.reu.product.tactics.actions;

import java.awt.Point;

import jp.reu.product.tactics.units.Unit;
import jp.reu.util.game.Action;

public class ActionMove extends Action
{
	Unit unit;
	Point from, to;

	public ActionMove(Unit unit, Point from, Point to) {
		this.unit = unit;
		this.from = from;
		this.to = to;
	}

	@Override
	public void print() {
		System.out.printf("Move (%d %d) -> (%d %d)\n",
				this.from.x, this.from.y, this.to.x, this.to.y);
	}
}
