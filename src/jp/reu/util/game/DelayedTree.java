package jp.reu.util.game;

import java.util.List;

public abstract class DelayedTree
{
	public State state;
	private List<DelayedTree> branches;
	private Function f;

	public DelayedTree(State state) {
		this.state = state;
	}

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

	protected void printRec(int depth, int limit)
	{
		if (depth == limit)
			return;

		System.out.println();
		this.print(depth);

		for (DelayedTree move : this.forceBranches()) {
			move.printRec(depth + 1, limit);
		}
	}

	public void desForce(int depth)
	{
		if (depth > 0) {
			for (DelayedTree branch : this.forceBranches()) {
				branch.desForce(depth - 1);
			}
		}
	}

	public boolean isBranchesForced()
	{
		return this.branches != null;
	}

	public List<DelayedTree> forceBranches()
	{
		if (this.branches == null) {
			// 現時点ではクロージャでは無く、自身のStateを参照している。
			this.branches = this.makeBranches();
		}

		return this.branches;
	}

	protected List<DelayedTree> makeBranches() {
		
	}
}
