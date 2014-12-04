package jp.reu.util.game.ais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.lazy.LazyTree;

public class AI
{
	public static final int LEVEL_MAX = -1;
	public Game rule;
	// force depth
	public int level;

	public AI (Game rule, int level) {
		this.rule = rule;
		this.level = level;
	}

	protected int rateTree(LazyGameTree tree, int player)
	{
		// Limited tree
		if (!tree.isForced()) {
			return this.scoreState(tree, player);
		}
		// Forced terminal
		else if (tree.isForcedTerminal()) {
			return this.scoreTerminal(tree, player);
		}
		// keep rating
		else {

			if (player == tree.getState().getPlayer()) {
				return Collections.max(
						this.getRatings(tree, player));
			} else {
				return Collections.min(
						this.getRatings(tree, player));
			}
		}
	}

	public List<Integer> getRatings(LazyTree tree, int player)
	{
		List<Integer> lst = new ArrayList<Integer>();

		for (LazyTree move : tree.force()) {
			lst.add(this.rateTree((LazyGameTree)move , player));
		}

		return lst;
	}

	public int scoreTerminal(LazyGameTree tree, int player)
	{
		List<Integer> winner = this.rule.winner(tree);

		if (winner.contains(player)) {
			return Integer.MAX_VALUE / winner.size();
		} else {
			return Integer.MIN_VALUE;
		}
	}

	public int scoreState (LazyGameTree tree, int player)
	{
		return this.rule.scoreState(tree, player);
	}

	public LazyGameTree hundleGameTree(LazyGameTree tree, int player)
	{
		List<Integer> ratings;
		int max_index, max;
		LazyGameTree next;

		//// Force ////
		// Force tree befor search.

		if (this.level == LEVEL_MAX) {
			tree.forceRec();
		} else {
			tree.forceRec(this.level);
		}

		ratings = this.abGetRatings(tree, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
		max_index = 0;
		max = ratings.get(0);
//
//		for (int i = 1; i < ratings.size(); i++) {
//			if (max < ratings.get(i)) {
//				max = ratings.get(i);
//				max_index = i;
//			}
//		}
//
		next = (LazyGameTree)tree.force().get(max_index);

		System.out.println();
		next.getAction().print();
		System.out.println();

		return next;
	}

	public List<Integer> abGetRatings(LazyGameTree tree, int player, int alpha, int beta, )
	{
		int a = alpha;
		int b = beta;
		List<Integer> result = new ArrayList<Integer>();
		int score;

		for (LazyTree branche : tree.force()) {
			score = this.abRateState((LazyGameTree)branche , player, a, b);
			result.add(score);

			if (result)


		}


		return result;
	}

	public int abRateState(LazyGameTree tree, int player, int alpha, int beta)
	{
		if (!tree.isForced() || tree.isForcedTerminal()) {
			return this.scoreState(tree, player);
		} else {

			if (tree.getState().getPlayer() == player) {
				return Collections.max(
						this.abGetRatings(tree, player, alpha, beta));
			} else {
				return Collections.min(
						this.abGetRatings(tree, player, alpha, beta));
			}
		}
	}
}
