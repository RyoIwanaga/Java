package jp.reu.product.diceofdoom;

import java.awt.Point;

import jp.reu.util.game.Action;

public class ActionAttack extends Action
{
	int player;
	Point from;
	Point to;
	int iFrom;
	int iTo;

	public ActionAttack(int player, Point from, Point to, int iFrom, int iTo) {
		this.player = player;
		this.from = from;
		this.to = to;
		this.iFrom = iFrom;
		this.iTo = iTo;
	}

	@Override
	public void print()
	{
		System.out.printf("Attack: [%d] -> [%d]\n",
				this.iFrom, this.iTo);
//		System.out.printf("Attack: [%d %d] -> [%d %d]\n",
//				this.from.x, this.from.y,
//				this.to.x, this.to.y);
	}

	public static void main(String[] args)
	{
	}
}
