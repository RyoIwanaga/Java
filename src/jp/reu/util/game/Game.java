package jp.reu.util.game;

import java.util.List;
import java.util.Scanner;

import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

/**
 * @abstract printWinner
 */

public abstract class Game
{
	abstract public List<LazyTree> makeBranches(LazyGameTree tree);
	abstract public List<Integer> winner (LazyGameTree tree);
	abstract public int scoreState (LazyGameTree tree, int player);

	protected void printWinner(List<Integer> winner)
	{
		System.out.println("Wins" + winner);
	}

	protected static LazyGameTree hundleHuman(LazyGameTree tree)
	{
		Scanner scan;
		int i = 0;
		int number;

		LazyGameTree cast;

		System.out.println("\nChoose your moves:");

		// Print actions
		for (LazyTree move : tree.force()) {
			System.out.print(i + ": ");
			cast = (LazyGameTree)move;
			cast.action.print();

			i++;
		}

		{
			scan = new Scanner(System.in);
			number = scan.nextInt();
			System.out.println();
		}

		try {
			return (LazyGameTree)tree.force().get(number);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("\n*ouch*\n");

			return tree;
		}
	}

	public List<Integer> play(LazyGameTree tree, AI[] ais)
	{
		int player = tree.getState().getPlayer();

		tree.print();

		if (tree.force().isEmpty()) {
			this.printWinner(this.winner(tree));
			return this.winner(tree);
		}
		// Play human
		else if (ais[player] == null){
			return this.play(
					hundleHuman(tree),
					ais);
		}
		else {
			return this.play(
					ais[player].hundleGameTree(tree, player),
					ais);
		}
	}

	//// helper ////

	public static int nextPlayer(int player, int max)
	{
		return (player + 1) % max;
	}
}
