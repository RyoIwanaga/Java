package jp.reu.reversi;

import jp.reu.util.game.Action;

class ActionPlace extends Action
{
	int player;
	int x;
	int y;

	ActionPlace(int player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
	}

	@Override
	public void print()
	{
		System.out.printf("(%d, %d)\n", this.x, this.y);
	}
}
