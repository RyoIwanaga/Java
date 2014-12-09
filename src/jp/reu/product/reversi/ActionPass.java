package jp.reu.product.reversi;

import jp.reu.util.game.Action;

class ActionPass extends Action
{
	int player;

	ActionPass(int player) {
		this.player = player;
	}

	@Override
	public void print()
	{
		System.out.println("Pass");
	}
}
