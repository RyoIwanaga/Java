package jp.reu.RPG;

import jp.reu.util.game.Action;

public class ActionAttack extends Action
{
	Unit from;
	Unit to;
	int dmg;

	public ActionAttack(Unit from, Unit to, int dmg) {
		super();
		this.from = from;
		this.to = to;
		this.dmg = dmg;
	}

	@Override
	public void print()
	{
		System.out.printf("「%s」->「%s」 %d damage\n",
				this.from.name,
				this.to.name,
				this.dmg);
	}
}
