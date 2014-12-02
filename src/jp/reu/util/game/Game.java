package jp.reu.util.game;

import java.util.List;
import java.util.Scanner;

import jp.reu.util.lazy.LazyTree;

public abstract class Game
{
	public static final int PLAYER_HUMAN = 0;
	public static final int PLAYER_COMPUTER = 1;

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
	
	protected static List<Integer> getRatings(LazyTree tree, int player)
	{
		List<Integer> lst = new ArrayList<Integer>();

		for (LazyTree move : tree.force()) {
			lst.add(rateTree((LazyGameTree)move , player));
		}

		return lst;
	}

	abstract protected int rateTree(LazyGameTree tree, int player);
	
	private static LazyGameTree hundleComputer(LazyGameTree tree, int player)
	{
		List<Integer> ratings;
		int max_index, max;

		/*** Initialize ***/

		ratings = getRatings(tree, player);
		max_index = 0;
		max = ratings.get(0);

		for (int i = 1; i < ratings.size(); i++) {
			if (max < ratings.get(i)) {
				max = ratings.get(i);
				max_index = i;
			}
		}

		return (LazyGameTree)tree.force().get(max_index);
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
