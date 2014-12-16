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
		System.out.printf("\"%s\" move (%d %d) -> (%d %d)\n",
				unit.name, from.x, from.y, to.x, to.y);
	}
}
