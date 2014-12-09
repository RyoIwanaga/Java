package jp.reu.product.diceofdoom;

import jp.reu.util.game.Action;

public class ActionPass extends Action
{
	@Override
	public void print()
	{
		System.out.println("Pass.");
	}

	public static void main(String[] args)
	{
		new ActionPass().print();
	}
}
