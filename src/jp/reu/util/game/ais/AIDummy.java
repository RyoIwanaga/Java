package jp.reu.util.game.ais;

import jp.reu.util.game.LazyGameTree;

public class AIDummy extends AI
{
	@Override
	protected int rateTree(LazyGameTree tree, int player)
	{
		return 0;
	}

}
