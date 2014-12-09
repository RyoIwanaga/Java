package jp.reu.RPG;

import jp.reu.util.game.State;

public class StateBattle extends State
{
	int turn;
	Unit me;
	Unit enemy;

	public StateBattle(int player, int turn, Unit me, Unit enemy) {
		super(player);
		this.turn = turn;
		this.me = me;
		this.enemy = enemy;
	}

	@Override
	public void print(int depth)
	{
		System.out.printf("=== ターン %d ===\n\n", this.turn);

		if (this.getPlayer() == 0) {
			System.out.printf("「%s」のターンです。\n", this.me.name);
		} else {
			System.out.printf("「%s」のターンです。\n", this.enemy.name);
		}

		System.out.println();

		this.me.print();
		this.enemy.print();
	}

	public static void main(String[] args)
	{
		new StateBattle(0, 3,
				new Unit("勇者", 10, 3),
				new Unit("敵", 10, 3)).print();
	}
}
