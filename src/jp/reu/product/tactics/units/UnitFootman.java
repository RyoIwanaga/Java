package jp.reu.product.tactics.units;

import jp.reu.util.diagram.Point;

public class UnitFootman extends Unit
{
	public UnitFootman(int owner, Point pos) {
		super(
				"foot",
				// hp
				70,
				// damage
				20,
				// speed
				4,
				// initiative
				10,

				owner, pos);
	}
}
