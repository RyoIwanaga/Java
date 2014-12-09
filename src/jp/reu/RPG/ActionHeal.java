package jp.reu.RPG;

import jp.reu.util.game.Action;

public class ActionHeal extends Action
{
	Unit u;
	int amount;

	public ActionHeal(Unit u, int amount) {
		this.u = u;
		this.amount = amount;
	}

	@Override
	public void print()
	{
		System.out.printf("Heal %d\n",
				this.amount);
	}
}
