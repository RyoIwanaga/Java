package jp.reu.reverse;

import java.util.List;

import jp.reu.util.game.DelayedGameTree;
import jp.reu.util.game.DelayedTree;
import jp.reu.util.game.State;

public class GameTree extends DelayedGameTree
{
	Action action;

	GameTree(State state) {
		super(state);
		this.action = null;
	}

	GameTree(Action action, State state) {
		super(state);
		this.action = action;
	}

	public ReverseState getState() {

		return (ReverseState)this.state;
	}

	public Action getAction() {

		return this.action;
	}

	@Override
	protected List<DelayedTree> makeBranches()
	{
		return Reverse.makeMoves(this.state);
	}
}