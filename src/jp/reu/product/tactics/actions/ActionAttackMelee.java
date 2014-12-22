package jp.reu.product.tactics.actions;


import jp.reu.product.tactics.actions.attack.ActionAttack;
import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;

public class ActionAttackMelee extends ActionAttack
{
	protected String text;

	int returnDamage;

	public ActionAttackMelee(Unit unit, Unit target, Point attackFrom, int damage, int returnDamage) {
		this.unit = unit;
		this.target = target;
		this.attackFrom = attackFrom;
		this.damage = damage;
		this.text = "Attack";
		this.returnDamage = returnDamage;
	}

	@Override
	public void print() {
		System.out.printf("%s [%d %d]%s %d hp%s, %d damage%s.\n",
				this.text,
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
