package jp.reu.util.game;

import java.util.List;

import jp.reu.util.lazy.LazyTree;

public class LazyGameTree extends LazyTree
{
	protected Action action;
	protected State state;
	private static Game gameRule;

	public LazyGameTree(Game rule, State state) {
		this.gameRule = rule;
		this.state = state;
		this.action = null;
	}

	public LazyGameTree(Action action, State state) {
		this.state = state;
		this.action = action;
	}

	/*** Getters ***/

	public State getState()
	{
		return this.state;
	}

	public Action getAction()
	{
		return this.action;
	}

	/*** Lazy functions ***/

	@Override
	public List<LazyTree> makeBranches()
	{
		return gameRule.makeBranches(this);
	}

	public void forceRec(int depth)
	{
		LazyGameTree gameTree;

		if (depth > 0) {
			for (LazyTree branch : this.force()) {
				gameTree = (LazyGameTree) branch;
				gameTree.forceRec(depth - 1);
			}
		}
	}

	/*** Print ***/

	public void print()
	{
		this.print(0);
	}

	public void print(int depth)
	{
		this.state.print(depth);
	}

	public void printRec(int limit)
	{
		this.printRec(0, limit);
	}

	public void printRec(int depth, int limit)
	{
		LazyGameTree gameTree;

		if (depth < limit) {
			this.print(depth);
			System.out.println();

			for (LazyTree branch : this.force()) {
				gameTree = (LazyGameTree) branch;
				gameTree.printRec(depth + 1, limit);
			}
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		LazyGameTree other = (LazyGameTree) obj;
		if (this.state == null) {
			if (other.state != null)
				return false;
		} else if (!this.state.equals(other.state))
			return false;
		return true;
	}
}
