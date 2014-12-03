package jp.reu.diceofdoom;

import java.util.Collections;
import java.util.List;

import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;
import jp.reu.diceofdoom.StateDoD;

public class AIDoD extends AI
{
	public AIDoD (Game rule) {
		super(rule);
	}

	@Override
	protected int rateTree(LazyGameTree tree, int player)
	{
		List<LazyTree> moves = tree.force();
		List<Integer> winner;
		List<Integer> scors;

		// game end ?
		if (moves.isEmpty()) {
			winner = this.rule.winner(tree);

			if (winner.contains(player)) {
				return 100 / winner.size();
			} else {
				return 0;
			}
		} else {
			scors = this.getRatings(tree, player);

			StateDoD state = (StateDoD)tree.getState();

			if (player == state.getPlayer()) {
				return Collections.max(scors);
			} else {
				return Collections.min(scors);
			}
		}
	}
}
