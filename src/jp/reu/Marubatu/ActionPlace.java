package jp.reu.Marubatu;

import jp.reu.util.game.Action;

public class ActionPlace extends Action
{
	int x;
	int y;
	
	public ActionPlace(int x, int y) {
		super();
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
		new ActionPlace(1, 2).print();
	}
}
