package jp.reu.product.tactics.actions;


import jp.reu.product.tactics.actions.attack.ActionAttack;
import jp.reu.product.tactics.units.Unit;
import jp.reu.util.diagram.Point;

public class ActionAttackMelee extends ActionAttack
{
	protected String text;

	public ActionAttackMelee(Unit unit, Unit target, Point attackFrom, int damage) {
		this.unit = unit;
		this.target = target;
		this.attackFrom = attackFrom;
		this.damage = damage;
		this.text = "Attack";
	}

	@Override
	public void print() {
		System.out.printf("%s [%d %d]%s %d hp%s, %d damage.\n",
				this.text,
				this.target.pos.x,
				this.target.pos.y,
				this.target.name,
				this.target.hp,
				unit.pos.equals(attackFrom) ?
						"" :
						String.format(" from (%d, %d)",
								this.attackFrom.x,
								this.attackFrom.y),
				this.damage);
	}
}
