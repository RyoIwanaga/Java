package jp.reu.diceofdoom;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.reu.util.Array;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.State;
import jp.reu.util.lazy.LazyTree;

public class DiceOfDoom extends Game
{
	static final int NUM_PLAYERS = 2;
	static final int MAX_DICE = 3;
	static final int BOARD_SIZE = 2;

	@Override
	public List<LazyTree> makeMoves(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		DODState state = (DODState)tree.getState();
		byte[][][] board = state.board;
		byte[][][] boardCopy;
		int player = state.player;

		Point from;

		/*** Add passing moves if already moved ***/

		if (!state.fFirstMove) {
			moves.add(new LazyGameTree(
					new ActionPass(),
					new DODState(
							addNewDice(board, player, state.spareDice),
							nextPlayer(player, NUM_PLAYERS),
							0,
							true)));
		}

		/*** Add attack moves ***/

		// For all hexes
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {

				// attack only from player hex
				if (player == board[y][x][DODState.HEX_PLAYER]) {

					// For neighbor of player hex
					from = new Point(x, y);
					for (Point p: collectNeighborPoints(from, board[0].length, board.length)) {

						if (isAttackable(board, from, p)) {

							// make new board
							boardCopy = Array.copyBBB(board);
							boardCopy[p.y][p.x][DODState.HEX_PLAYER] = (byte)player;
							// set dice 1
							boardCopy[y][x][DODState.HEX_DICE] = 1;
							// move dice
							boardCopy[p.y][p.x][DODState.HEX_DICE] = (byte)(board[y][x][DODState.HEX_DICE] - 1);

							moves.add(new LazyGameTree(
									new ActionAttack(player, from, p),
									new DODState(
											boardCopy,
											player,
											// add spare dice
											state.spareDice + board[p.y][p.x][DODState.HEX_DICE],
											// moved already
											false)));
						}
					}
				}
			}
		}

		return moves;
	}

	public static boolean isAttackable(byte[][][] board, Point from, Point to)
	{
		if (board[from.y][from.x][DODState.HEX_PLAYER] != board[to.y][to.x][DODState.HEX_PLAYER] &&
				board[from.y][from.x][DODState.HEX_DICE] > board[to.y][to.x][DODState.HEX_DICE]) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[][][] addNewDice(byte[][][] board, int player, int spareDice)
	{
		byte[][][] copy = Array.copyBBB(board);
		int rest = spareDice;

		outside:
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (rest == 0) {
					break outside;
				}
				else if (board[y][x][DODState.HEX_PLAYER] == player &&
						board[y][x][DODState.HEX_DICE] < MAX_DICE) {
					copy[y][x][DODState.HEX_DICE]++;
					rest--;
				}
			}
		}

		return copy;
	}

	public static Set<Point>collectNeighborPoints(Point p, int height, int width)
	{
		/*
		 *   0:0 1:0 2:0
		 *  0:1 1:1 2:1
		 * 0:2 1:2 2:2
		 */
		Set<Point> points = new HashSet<Point>();
		int minx = Math.max(0, p.x - 1);
		int maxx = Math.min(p.x + 1, width - 1);
		int miny = Math.max(0, p.y - 1);
		int maxy = Math.min(p.y + 1, height - 1);

		for (int y = miny; y <= maxy; y++) {
			for (int x = minx; x <= maxx; x++) {
				if (p.x != x || p.y != y)
					points.add(new Point(x, y));
			}
		}

		return points;
	}

	public static List<Integer[]> winner(State state)
	{
		List<Integer[]> winner = new ArrayList<Integer[]>();
		DODState s = (DODState)state;
		byte[][][] board = s.board;
		int winValue = 0;

		final int PLAYER = 0;
		final int ACC = 1;
		final int MAX = 2;

		/// Initialize

		for (int i = 0; i < NUM_PLAYERS; i++) {
			winner.add(new Integer[MAX]);
			winner.get(i)[PLAYER] = i;
			winner.get(i)[ACC] = 0;
		}

		/// Count occupied area of each players

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				winner.get(board[y][x][DODState.HEX_PLAYER])[ACC]++;
			}
		}
		/*

		/// Caluculate highest value

		for (Integer[] item: winner) {
			if (winValue < item[ACC]) {
				winValue = item[ACC];
			}
		}

		/// Remove weak item

		for (Integer[] item: winner) {
			if (item[ACC] < winValue) {
				winner.remove(item);
			}
		}*/

		for (Integer[] item: winner)
			System.out.printf("%d:%d\n", item[0], item[1]);

		return winner;
	}

	public static void play(LazyGameTree tree)
	{
		tree.print();

		if (!tree.force().isEmpty()) {
			play(hundleHuman(tree));
		} else {
			System.out.println(winner(tree.getState()));
		}
	}

	public static void main(String[] args)
	{
		DODState state = new DODState(BOARD_SIZE, NUM_PLAYERS, MAX_DICE);

		LazyGameTree tree = new LazyGameTree(new DiceOfDoom(), state);
		// tree.printRec(3);
		play(tree);

		/*
		System.out.println("from 0:0");
		for (Point p: collectNeighborPoints(new Point(0, 0), 3, 3))
			System.out.println(p);

		System.out.println("from 1:0");
		for (Point p: collectNeighborPoints(new Point(0, 0), 3, 3))
			System.out.println(p);


		System.out.println("from 1:1");
		for (Point p: collectNeighborPoints(new Point(1, 1), 3, 3))
			System.out.println(p);
			*/

	}
}
