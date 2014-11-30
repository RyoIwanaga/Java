package jp.reu.util.game;

public abstract class LazyGameTree extends DelayedTree
{
	public Action action;

	public LazyGameTree(State state) {
		super(state);
		this.action = null;
	}

	public LazyGameTree(Action action, State state) {
		super(state);
		this.action = action;
	}
}
