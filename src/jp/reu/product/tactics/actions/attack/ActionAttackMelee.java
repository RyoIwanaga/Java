package jp.reu.product.tactics.actions.attack;


import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;

public class ActionAttackMelee extends ActionAttack
{
	public int returnDamage;

	public ActionAttackMelee(Unit unit, Unit target, int unitIndex, int targetIndex,
			Point attackFrom, int damage, int returnDamage) {
		super(unit, target, unitIndex, targetIndex, attackFrom, damage);
		this.returnDamage = returnDamage;
	}


	@Override
	public void print() {
		System.out.printf("Attack [%d %d]%s %d hp%s, %d damage%s.\n",
				this.target.pos.x,
				this.target.pos.y,
				this.target.name,
				this.target.hp,
				this.unit.pos.equals(this.attackFrom) ?
						"" :
						String.format(" from (%d, %d)",
								this.attackFrom.x,
								this.attackFrom.y),
				this.damage,
				this.returnDamage < 0 ?
						"" :
						String.format(", But resieve counter damage %d",
								this.returnDamage)
				);
	}
}
