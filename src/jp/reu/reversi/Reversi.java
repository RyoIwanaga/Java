package jp.reu.reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.State;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

class Reversi extends Game
{
	public static final Reversi instance = new Reversi();

	static final int STONE_NONE = 0;
	static final int STONE_BLACK = 1;
	static final int STONE_WHITE = 2;

	static final int AI_LEVEL = 3;

	static final char[] DISPLAY = { '□', '●', '○' };


	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateReversi state = (StateReversi)tree.getState();
		StateReversi new_state;
		LazyTree move;

		byte[][] board = state.board;
		int width = board[0].length;
		int height = board.length;
		byte[][] copy;

		int player = state.getPlayer();
		int my = player + 1;
		int opp = (player + 1) % 2 + 1;

		boolean f_nospace = true;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isPlaceable(board, x, y, width, height, opp)) {
					f_nospace = false;
					copy = copyBoard(board);

					if (desReverse(copy, height, width, x, y, my, opp) > 0) {
						new_state = new StateReversi(copy, (player + 1) % 2, false,
								state.turn + 1);
						move = new LazyGameTree(new ActionPlace(player, x, y), new_state);

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
				new_state = new StateReversi(copy, (player + 1) % 2, true,
						state.turn + 1);
				move = new LazyGameTree(
						new ActionPass(player),
						new_state
						);

				moves.add(move);
			}
		}

		// add passing move

		return moves;
	}

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

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
		List<Integer> win = new ArrayList<Integer>();
		int count_p1 = 0;
		int count_p2 = 0;

		StateReversi state = (StateReversi)tree.getState();
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
			win.add(0);
			win.add(1);
		} else if (count_p1 > count_p2) {
			win.add(0);
		} else {
			win.add(1);
		}

		return win;
	}

	@Override
	public int scoreState(LazyGameTree tree, int player)
	{
		final int[][] scoreTable = {
				{ 30, -12,  0, -1, -1,  0, -12,  30},
				{-12, -15, -3, -3, -3, -3, -15, -12},
				{  0,  -3,  0, -1, -1,  0,  -3,   0},
				{ -1,  -3, -1, -1, -1, -1,  -3,  -1},
				{ -1,  -3, -1, -1, -1, -1,  -3,  -1},
				{  0,  -3,  0, -1, -1,  0,  -3,   0},
				{-12, -15, -3, -3, -3, -3, -15, -12},
				{ 30, -12,  0, -1, -1,  0, -12,  30},
		};

		StateReversi state = (StateReversi)tree.getState();
		byte[][] board = state.board;
		int sum = 0;

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {

				if (state.turn > 35) {
					// blank
					if (board[y][x] == STONE_NONE) {
						continue;
					}
					// my
					else if (board[y][x] == player + 1) {
						sum += 100;
					}
					// enemy stone
					else {
						sum -= 100;
					}
				} else {
					// blank
					if (board[y][x] == STONE_NONE) {
						continue;
					}
					// my
					else if (board[y][x] == player + 1) {
						sum += scoreTable[y][x];
					}
					// enemy stone
					else {
						sum += scoreTable[y][x] * -1;
					}
				}
			}
		}

		return sum;
	}

	public static void main(String[] args)
	{
		// State state = new StateReversi(8);

		String[] boardStr = {
				"□□○○○○○○",
				"□□○○●●□□",
				"●○●●○●○○",
				"○○●●●●●●",
				"○○●●○●●○",
				"○○●●●●○○",
				"□□●●○○□□",
				"□□□□●○□□",
		};
		byte[][] board = new byte[8][8];
		char c;

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				c = boardStr[y].charAt(x);

				if (c == StateReversi.DISPLAY[0]) {
					board[y][x] = STONE_NONE;
				}
				else if (c == StateReversi.DISPLAY[1]) {
					board[y][x] = STONE_BLACK;
				} else {
					board[y][x] = STONE_WHITE;
				}
			}
		}

		State state = new StateReversi(board, 0, false, 47);
		LazyGameTree tree = new LazyGameTree(instance, state);

		new Reversi().play(tree, new AI[] {
				null,
				new AI(instance, 7),
		});
	}
}
