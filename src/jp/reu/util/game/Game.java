package jp.reu.util.game;

import java.util.List;
import java.util.Scanner;

import jp.reu.util.lazy.LazyTree;

public abstract class Game
{
	abstract public List<LazyTree> makeMoves(LazyGameTree tree);

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

	public static void play(LazyGameTree tree)
	{
		tree.print();

		if (!tree.force().isEmpty()) {
			play(hundleHuman(tree));
		} else {
			System.out.println("Game End");
		}
	}

	/*** helper ***/

	public static int nextPlayer(int player, int max)
	{
		return (player + 1) % max;
	}
}
