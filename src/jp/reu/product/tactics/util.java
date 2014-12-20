package jp.reu.product.tactics;

import java.awt.Point;
import java.util.List;

import jp.reu.product.tactics.units.Unit;
import jp.reu.util.lists.Identifer;
import jp.reu.util.lists.Lists;

public class util
{
	public static Unit findAttackableUnit (List<Unit> units, final Point p, final Unit from)
	{
		return Lists.find1(units, new Identifer<Unit>() {
			@Override
			public boolean is(Unit o)
			{
				if (p.equals(o.pos) && from.isAttackAble(o)) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
/*
	public static Unit findAttackableUnit1 (List<Unit> units, final Point p, final Unit from)
	{
		for (Unit u : units) {
			if (p.equals(u.pos) && from.isAttackAble(u)) {
				return u;
			}
		}
		
		return null;
	}
	*/
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}

