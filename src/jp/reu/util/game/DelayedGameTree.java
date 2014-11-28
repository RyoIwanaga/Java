package jp.reu.util.game;

public abstract class DelayedGameTree extends DelayedTree
{
	public Action action;

	public DelayedGameTree(State state) {
		super(state);
		this.action = null;
	}

	public DelayedGameTree(Action action, State state) {
		super(state);
		this.action = action;
	}
}
