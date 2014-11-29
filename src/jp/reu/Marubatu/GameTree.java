package jp.reu.Marubatu;

import java.util.List;

import jp.reu.util.game.DelayedGameTree;
import jp.reu.util.game.DelayedTree;
import jp.reu.util.game.Action;

public class GameTree extends DelayedGameTree
{
	public GameTree(MarubatuState state) {
		super(state);
	}
	
	public GameTree(Action action, MarubatuState state) {
		super(action, state);
	}

	@Override
	protected List<DelayedTree> makeBranches()
	{
		return Marubatu.makeMoves(this);
	}
}
