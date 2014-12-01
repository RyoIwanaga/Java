package jp.reu.util.lazy;

import java.util.List;

public abstract class LazyTree
{
	protected List<LazyTree> branches;
	
	public List<LazyTree> force()
	{
		if (this.branches == null) {
			this.branches = makeBranches();
		}

		return branches;
	}
	
	abstract public List<LazyTree> makeBranches();

	public boolean isForced()
	{
		return this.branches != null;
	}
}
