package jp.reu.util.game;

import java.util.ArrayList;
import java.util.List;

import jp.reu.util.lazy.LazyTree;

public abstract class AI
{
	public List<Integer> getRatings(LazyTree tree, int player)
	{
		List<Integer> lst = new ArrayList<Integer>();

		for (LazyTree move : tree.force()) {
			lst.add(this.rateTree((LazyGameTree)move , player));
		}

		return lst;
	}

	abstract protected int rateTree(LazyGameTree tree, int player);

	public LazyGameTree hundleGameTree(LazyGameTree tree, int player)
	{
		List<Integer> ratings;
		int max_index, max;
		LazyGameTree next;

		//// Initialize ////

		ratings = this.getRatings(tree, player);
		max_index = 0;
		max = ratings.get(0);

		for (int i = 1; i < ratings.size(); i++) {
			if (max < ratings.get(i)) {
				max = ratings.get(i);
				max_index = i;
			}
		}

		next = (LazyGameTree)tree.force().get(max_index);

		System.out.println();
		next.action.print();
		System.out.println();

		return next;
	}
}
