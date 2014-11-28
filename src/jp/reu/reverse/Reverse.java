package jp.reu.reverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

import jp.reu.util.game.DelayedTree;
import jp.reu.util.game.State;

class Reverse
{
	static final int PLAYER_HUMAN = 0;
	static final int PLAYER_COMPUTER = 1;

	static final int STONE_NONE = 0;
	static final int STONE_BLACK = 1;
	static final int STONE_WHITE = 2;

	static final int AI_LEVEL = 3;

	static final char[] DISPLAY = { '□', '●', '○' };

	public static void main(String[] args)
	{
		byte[][] board = makeBoard(4);
		State state = new ReverseState(board, 0, false);

		GameTree tree = new GameTree(state);

		//tree.printRec(2);
		play(tree, PLAYER_HUMAN, PLAYER_HUMAN);
		// play(tree, PLAYER_HUMAN, PLAYER_COMPUTER);
		//play(tree, PLAYER_COMPUTER, PLAYER_HUMAN);
	}

	public static void printBoard(byte[][] board)
	{
		printBoard(board, 0);
	}

	public static void printBoard(byte[][] board, int depth)
	{

		for (int y = -1; y < board.length; y++) {

			System.out.println();
			Util.printDepth(depth);

			for (int x = -1; x < board[0].length; x++) {

				// blank
				if (x == -1 && y == -1) {
					System.out.print("  ");
					continue;
				}
				// x軸を出力
				else if (y == -1) {
					System.out.printf("%2d", x);
					continue;
				} else if (x == -1) {
					System.out.printf("%2d", y);
					continue;
				}

				// DISPLAY STONE or NO STONE
				System.out.print(DISPLAY[board[y][x]]);
			}
		}
	}

	/*** Game Tree ***/

	public static byte[][] makeBoard(int size)
	{
		int harf = size / 2;
		byte[][] board = new byte[size][size];

		board[harf - 1][harf - 1] = STONE_BLACK;
		board[harf][harf] = STONE_BLACK;

		board[harf][harf - 1] = STONE_WHITE;
		board[harf - 1][harf] = STONE_WHITE;

		return board;
	}

	static List<DelayedTree> makeMoves(State stateAbs)
	{
		List<DelayedTree> moves = new ArrayList<DelayedTree>();
		ReverseState state = (ReverseState)stateAbs;
		ReverseState new_state;
		GameTree move;

		byte[][] board = state.board;
		int width = board[0].length;
		int height = board.length;
		byte[][] copy;

		int player = state.player;
		int my = player + 1;
		int opp = (player + 1) % 2 + 1;

		boolean f_nospace = true;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isPlaceable(board, x, y, width, height, opp)) {
					f_nospace = false;
					copy = copyBoard(board);

					if (desReverse(copy, height, width, x, y, my, opp) > 0) {
						new_state = new ReverseState(copy, (player + 1) % 2, false);
						move = new GameTree(new Action(player, x, y), new_state);

						moves.add(move);
					}
				}
			}
		}

		// No space
		if (f_nospace) {
			// Game End
		}
		if (moves.isEmpty()) {
			// お互いにパス
			if (state.f_pass) {
				// Game End
				// 一度目のパス
			} else {
				copy = copyBoard(board);
				new_state = new ReverseState(copy, (player + 1) % 2, true);
				move = new GameTree(new Action(player, -1, -1), new_state);

				moves.add(move);
			}
		}

		// add passing move

		return moves;
	}

	/*** Reverse ***/

	private static int desReverse(byte[][] board, int height, int width, int x, int y, int my,
			int opp)
	{
		int result = 0;

		result += desReverseSub(board, height, width, x, y, -1, -1, my, opp);
		result += desReverseSub(board, height, width, x, y, -1, 0, my, opp);
		result += desReverseSub(board, height, width, x, y, -1, 1, my, opp);
		result += desReverseSub(board, height, width, x, y, 0, -1, my, opp);
		result += desReverseSub(board, height, width, x, y, 0, 1, my, opp);
		result += desReverseSub(board, height, width, x, y, 1, -1, my, opp);
		result += desReverseSub(board, height, width, x, y, 1, 0, my, opp);
		result += desReverseSub(board, height, width, x, y, 1, 1, my, opp);

		return result;
	}

	/**
	 * 指定されたベクトル方向にリバース。戻り値は、返した数。
	 */
	private static int desReverseSub(byte[][] board, int height, int width, int x, int y, int dx,
			int dy, int my, int opp)
	{
		int xx = x + dx;
		int yy = y + dy;
		int passed = 0;
		int count = 0;
		int xxx, yyy;

		/*
		 * printBoard(board);
		 *
		 * System.out.println("my"+my); System.out.println("x"+x);
		 * System.out.println("y"+y); System.out.println("dx"+dx);
		 * System.out.println("dy"+dy);
		 *
		 *
		 * System.out.println(xx); System.out.println(yy);
		 */

		while (0 <= yy && yy < height && 0 <= xx && xx < width) {

			// Empty
			if (board[yy][xx] == STONE_NONE) {
				return 0;
			}
			// Opp stone
			else if (board[yy][xx] == opp) {
				passed++;
			}
			// My stone
			else if (board[yy][xx] == my) {
				if (passed == 0) {
					return 0;
				}

				// // Reverse!!

				xxx = dx == 0 ? x : x + dx;
				yyy = dy == 0 ? y : y + dy;
				board[y][x] = (byte) my;

				while (board[yyy][xxx] != STONE_NONE) {

					// *UPDATE*
					board[yyy][xxx] = (byte) my;

					count++;
					if (dx != 0) {
						xxx += dx;
						if (0 <= xxx && xxx < width)
							break;
					}
					if (dy != 0) {
						yyy += dy;
						if (0 <= yyy && yyy < height)
							break;
					}
				}

				return count;
			}

			xx += dx;
			yy += dy;

		}

		return 0;
	}

	/*** Helper ***/

	private static boolean isPlaceable(byte[][] board, int x, int y, int width, int height, int opp)
	{

		if (board[y][x] == 0 && isNeighborOpp(board, x, y, width, height, opp)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isNeighborOpp(byte[][] board, int x, int y, int width, int height,
			int opp)
	{

		int y_min = Math.max(y - 1, 0);
		int y_max = Math.min(y + 1, height - 1);
		int x_min = Math.max(x - 1, 0);
		int x_max = Math.min(x + 1, width - 1);
		byte opp_byte = (byte) opp;

		for (int yy = y_min; yy <= y_max; yy++) {
			for (int xx = x_min; xx <= x_max; xx++) {
				if (board[yy][xx] == opp_byte) {
					return true;
				}
			}
		}

		return false;
	}

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

	private static GameTree hundleHuman(GameTree tree)
	{
		Scanner scan = new Scanner(System.in);
		int i = 0;
		int number;

		GameTree t;

		System.out.println("\nChoose your moves:");

		// Print actions
		for (DelayedTree move : tree.forceBranches()) {
			System.out.print(i + ": ");
			t = (GameTree)move;
			t.action.print();

			i++;
		}

		try {
			number = scan.nextInt();
			return (GameTree)tree.forceBranches().get(number);
		} catch (Exception e) {
			return tree;
		}
	}

	private static GameTree hundleComputer(GameTree tree)
	{
		List<Integer> ratings;
		int max_index, max;

		/*** Force ***/

		tree.desForce(AI_LEVEL);

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
