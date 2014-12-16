package jp.reu.product.tactics.units;

import java.awt.Point;

public class UnitArcher extends UnitRanged
{
	public UnitArcher(int owner, Point pos) {
		super(
				"bow",
				// hp
				15,
				// damage
				6,
				// speed
				2,
				// initiative
				8,

				owner, pos);
	}
}
