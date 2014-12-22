package jp.reu.product.tactics.units;

import jp.reu.util.diagram.Point;


public class UnitArcher extends UnitRanged
{
	public UnitArcher(int owner, Point pos) {
		super(
				"bow",
				// hp
				50,
				// damage
				20,
				// speed
				2,
				// initiative
				8,
				owner, pos);
	}
}
