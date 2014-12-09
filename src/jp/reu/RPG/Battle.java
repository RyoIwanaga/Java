package jp.reu.RPG;

import java.util.ArrayList;
import java.util.List;

import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

public class Battle extends Game
{
	public static final Battle INSTANCE = new Battle();

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateBattle state = (StateBattle)tree.getState(),
				newState;
		int player = state.getPlayer();
		Unit from, to;
		int dmg;

		if (state.me.isDead() || state.enemy.isDead()) {
			return moves;
		}


		if (player == 0) {

			from =state.me;
			to = state.enemy.clone();

			dmg = from.attack(to);

			newState = new StateBattle(
							Game.nextPlayer(player, 2),
							state.turn + 1,
							from,
							to);

			moves.add(new LazyGameTree(
					new ActionAttack(from, to, dmg),
					newState));

			/*** Heal ***/

			from =state.me.clone();
			from.hp += 3;
			to = state.enemy.clone();

			newState = new StateBattle(
							Game.nextPlayer(player, 2),
							state.turn + 1,
							from,
							to);

			moves.add(new LazyGameTree(
					new ActionHeal(from, 3),
					newState));
		} else {

			from =state.enemy;
			to = state.me.clone();

			dmg = from.attack(to);

			newState = new StateBattle(
							Game.nextPlayer(player, 2),
							state.turn + 1,
							to,
							from);

			moves.add(new LazyGameTree(
					new ActionAttack(from, to, dmg),
					newState));
		}

		return moves;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
		List<Integer> win = new ArrayList<Integer>();
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateBattle state = (StateBattle)tree.getState();
		Unit player = state.me;
		Unit enemy = state.enemy;

		if (player.isDead())
			win.add(1);
		else
			win.add(0);

		return win;
	}

	@Override
	public int scoreState(LazyGameTree tree, int player)
	{
		StateBattle state = (StateBattle)tree.getState();
		Unit me = state.me;
		Unit enemy = state.enemy;

		if (player == 0) {
			return me.hp - enemy.hp;
		} else {
			return enemy.hp - me.hp;
		}
	}

	public static void main(String[] args)
	{
		StateBattle state = new StateBattle(0, 1,
				new Unit("勇者", 10, 3),
				new Unit("敵", 10, 3));

		LazyGameTree tree = new LazyGameTree(INSTANCE, state);

		new Battle().play(tree, new AI [] { null, null });

	}

}
