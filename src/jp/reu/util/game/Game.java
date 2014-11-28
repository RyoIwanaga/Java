package jp.reu.util.game;

import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.LabelUI;

public abstract class Game
{
	public static byte[][] copyBoard(byte[][] from)
	{
		int width = from[0].length;
		int height = from.length;
		byte[][] copy = new byte[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				copy[y][x] = from[y][x];
			}
		}

		return copy;
	}

	/*** PLAY ***/

	private static DelayedGameTree hundleHuman(DelayedGameTree tree)
	{
		Scanner scan = new Scanner(System.in);
		int i = 0;
		int number;

		DelayedGameTree t;

		System.out.println("\nChoose your moves:");

		// Print actions
		for (DelayedTree move : tree.forceBranches()) {
			System.out.print(i + ": ");
			t = (DelayedGameTree)move;
			t.action.print();

			i++;
		}

		try {
			number = scan.nextInt();
			return (DelayedGameTree)tree.forceBranches().get(number);
		} catch (Exception e) {
			return tree;
		}
	}

	private static DelayedGameTree hundleComputer(DelayedGameTree tree)
	{
		List<Integer> ratings;
		int max_index, max;

		/*** Force ***/

//		tree.desForce(AI_LEVEL);

		/*** Initialize ***/

		ratings = getRatings(tree, tree.getState().player);
		max_index = 0;
		max = ratings.get(0);

		for (int i = 1; i < ratings.size(); i++) {
			if (max < ratings.get(i)) {
				max = ratings.get(i);
				max_index = i;
			}
		}

		return (GameTree)tree.forceBranches().get(max_index);
	}

	/**
	 * @return 0: even, 1: p1 win, 2: p2 win
	 */
	private static int winners(ReverseState state)
	{
		int count_p1 = 0;
		int count_p2 = 0;

		byte[][] board = state.board;

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				switch (board[y][x]) {
				case STONE_NONE:
					break;
				case STONE_BLACK:
					count_p1++;
					break;
				case STONE_WHITE:
					count_p2++;
					break;
				}
			}
		}

		if (count_p1 == count_p2) {
			return 0;
		} else if (count_p1 > count_p2) {
			return 1;
		} else {
			return 2;
		}
	}

	private static void printWinner(int w)
	{
		System.out.println();

		switch (w) {
		case 0:
			System.out.println("Even:");
			break;
		case 1:
		case 2:
			System.out.printf("Player %s WIN!!", DISPLAY[w]);
			break;
		}
	}

	private static List<Integer> getRatings(GameTree tree, int player)
	{
		List<Integer> lst = new ArrayList<Integer>();

		for (DelayedTree move : tree.forceBranches()) {
			lst.add(rateTree((GameTree)move , player));
		}

		return lst;
	}

	private static Integer rateTree(GameTree tree, int player)
	{
		List<DelayedTree> moves = tree.forceBranches();
		int win;
		List<Integer> scors;

		// game end ?
		if (moves.isEmpty()) {
			win = winners(tree.getState());

			if (win == 0) {
				return 0;
			} else if (win == player + 1) {
				return 1;
			} else {
				return -1;
			}
		} else {
			scors = getRatings(tree, player);

			if (player == tree.getState().player) {
				return Collections.max(scors);
			} else {
				return Collections.min(scors);
			}
		}

	}

	public static void play(GameTree tree, int p1, int p2)
	{
		int player = tree.getState().player;
		GameTree nextMove;

		tree.print();

		if (tree.forceBranches().isEmpty()) {
			printWinner(winners(tree.getState()));

		} else {
			if (player == 0 && p1 == PLAYER_HUMAN)
				nextMove = hundleHuman(tree);

			else if (player == 1 && p2 == PLAYER_HUMAN)
				nextMove = hundleHuman(tree);

			else if (player == 0 && p1 == PLAYER_COMPUTER)
				nextMove = hundleComputer(tree);

			else
				nextMove = hundleComputer(tree);

			play(nextMove, p1, p2);
		}
	}
}
