package jp.reu.diceofdoom;

import java.awt.Point;

import jp.reu.util.game.Action;

public class ActionAttack extends Action
{
	int player;
	Point from;
	Point to;
	
	public ActionAttack(int player, Point from, Point to) {
		this.player = player;
		this.from = from;
		this.to = to;
	}

	@Override
	public void print()
	{
		System.out.printf("Attack: [%d %d] -> [%d %d]\n",
				this.from.x, this.from.y,
				this.to.x, this.to.y);
	}

	public static void main(String[] args)
	{
		new ActionAttack(
				0, 
				new Point(1, 2),
				new Point(2, 3)).print();
	}
}
