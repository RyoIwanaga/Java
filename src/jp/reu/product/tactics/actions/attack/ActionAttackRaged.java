package jp.reu.product.tactics.actions.attack;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;

public class ActionAttackRaged extends ActionAttack
{
	public ActionAttackRaged(Unit unit, Unit target, int unitIndex, int targetIndex,
			Point attackFrom, int damage) {
		super(unit, target, unitIndex, targetIndex, attackFrom, damage);
	}

	@Override
	public void print()
	{
		System.out.printf("Attack [%d %d]%s %d hp%s, %d damage.\n",
				this.target.pos.x,
				this.target.pos.y,
				this.target.name,
				this.target.hp,
				this.unit.pos.equals(this.attackFrom) ?
						"" :
						String.format(" from (%d, %d)",
								this.attackFrom.x,
								this.attackFrom.y),
				this.damage
				);
	}
}
