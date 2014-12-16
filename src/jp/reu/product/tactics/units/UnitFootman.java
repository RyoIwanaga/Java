package jp.reu.product.tactics.units;

import java.awt.Point;

public class UnitFootman extends Unit
{
	public UnitFootman(int owner, Point pos) {
		super(
				"foot",
				// hp
				20,
				// damage
				7,
				// speed
				4,
				// initiative
				10,

				owner, pos);
	}
}
