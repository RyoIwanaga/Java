package jp.reu.product.marubatu;

import jp.reu.util.game.Action;

public class ActionPlace extends Action
{
	int player;
	int x;
	int y;
	
	public ActionPlace(int player, int x, int y) {
		super();
		this.player = player;
		this.x = x;
		this.y = y;
	}

	@Override
	public void print()
	{
		System.out.printf("[%d %d]\n", this.x, this.y);
	}

	public static void main(String[] args)
	{
		new ActionPlace(0, 1, 2).print();
	}
}
