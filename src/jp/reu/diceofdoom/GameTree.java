package jp.reu.diceofdoom;

import java.util.List;

import jp.reu.util.game.DelayedGameTree;
import jp.reu.util.game.DelayedTree;

public class GameTree extends DelayedGameTree
{

	@Override
	protected List<DelayedTree> makeBranches()
	{
		return DiceOfDoom.makeMoves(this);
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
