package jp.reu.product.tactics;

import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.ais.AI;

public class AITactics extends AI
{
	public AITactics(Game rule, int level) {
		super(rule, level);
	}
	
	@Override
	public int scoreTerminal(LazyGameTree tree, int player)
	{
		return super.scoreState(tree, player);
	}

}
