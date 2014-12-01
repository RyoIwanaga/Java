package jp.reu.util.game;

public abstract class DelayedGameTree extends DelayedTree
{
	public Action action;
	private static Game gameRule;

	public DelayedGameTree(State state) {
		super(state);
		this.action = null;
	}

	public DelayedGameTree(Action action, State state) {
		super(state);
		this.action = action;
	}
}
