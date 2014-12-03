package jp.reu.diceofdoom;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.reu.util.Array;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

public class DiceOfDoom extends Game
{
	public static final DiceOfDoom instance = new DiceOfDoom();

	static final int NUM_PLAYERS = 2;
	static final int MAX_DICE = 3;
	static final int BOARD_SIZE = 2;

	// Memoize
	protected static Map<Point, Set<Point>> neighborHistory = new HashMap<Point, Set<Point>>();

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateDoD state = (StateDoD)tree.getState();
		byte[][][] board = state.board;
		byte[][][] boardCopy;
		int player = state.player;

		Point from;

		//// Add passing moves if already moved ////

		if (!state.fFirstMove) {
			moves.add(new LazyGameTree(
					new ActionPass(),
					new StateDoD(
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
				if (player == board[y][x][StateDoD.HEX_PLAYER]) {

					// For neighbor of player hex
					from = new Point(x, y);
					for (Point p: memoizeCollectNeighborPoints(from)) {

						if (isAttackable(board, from, p)) {

							// make new board
							boardCopy = Array.copyBBB(board);
							boardCopy[p.y][p.x][StateDoD.HEX_PLAYER] = (byte)player;
							// set dice 1
							boardCopy[y][x][StateDoD.HEX_DICE] = 1;
							// move dice
							boardCopy[p.y][p.x][StateDoD.HEX_DICE] = (byte)(board[y][x][StateDoD.HEX_DICE] - 1);

							moves.add(new LazyGameTree(
									new ActionAttack(player, from, p),
									new StateDoD(
											boardCopy,
											player,
											// add spare dice
											state.spareDice + board[p.y][p.x][StateDoD.HEX_DICE],
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
		if (board[from.y][from.x][StateDoD.HEX_PLAYER] != board[to.y][to.x][StateDoD.HEX_PLAYER] &&
				board[from.y][from.x][StateDoD.HEX_DICE] > board[to.y][to.x][StateDoD.HEX_DICE]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Supply dice left to right, top to botom.
	 * @return updated board
	 */
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
				else if (board[y][x][StateDoD.HEX_PLAYER] == player &&
						board[y][x][StateDoD.HEX_DICE] < MAX_DICE) {
					copy[y][x][StateDoD.HEX_DICE]++;
					rest--;
				}
			}
		}

		return copy;
	}

	public static Set<Point> collectNeighborPoints(Point p)
	{
		/*
		 *   0:0 1:0 2:0
		 *  0:1 1:1 2:1
		 * 0:2 1:2 2:2
		 */
		Set<Point> points = new HashSet<Point>();
		int minx = Math.max(0, p.x - 1);
		int maxx = Math.min(p.x + 1, BOARD_SIZE - 1);
		int miny = Math.max(0, p.y - 1);
		int maxy = Math.min(p.y + 1, BOARD_SIZE - 1);

		for (int y = miny; y <= maxy; y++) {
			for (int x = minx; x <= maxx; x++) {
				if (p.x != x || p.y != y)
					points.add(new Point(x, y));
			}
		}

		return points;
	}

	public static Set<Point> memoizeCollectNeighborPoints(Point p)
	{
		Set<Point> result;

		result = neighborHistory.get(p);

		if (result == null) {
			result = collectNeighborPoints(p);
			neighborHistory.put(p, result);
		}

		return result;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
		List<Integer[]> pairs = new ArrayList<Integer[]>();
		List<Integer> winner = new ArrayList<Integer>();
		int winValue;
		StateDoD s = (StateDoD)tree.getState();
		byte[][][] board = s.board;

		final int PLAYER = 0;
		final int ACC = 1;
		final int MAX = 2;

		/// Initialize

		for (int i = 0; i < NUM_PLAYERS; i++) {
			pairs.add(new Integer[MAX]);
			pairs.get(i)[PLAYER] = i;
			pairs.get(i)[ACC] = 0;
		}

		/// Count occupied area of each players

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				pairs.get(board[y][x][StateDoD.HEX_PLAYER])[ACC]++;
			}
		}

		/// collect win pairs

		winner.add(pairs.get(0)[PLAYER]);
		winValue = pairs.get(0)[ACC];
		for (int i = 1; i < pairs.size(); i++) {
			if (pairs.get(i)[ACC] == winValue) {
				winner.add(pairs.get(0)[PLAYER]);
			} else if (pairs.get(i)[ACC] >= winValue) {
				winner = new ArrayList<Integer>();
				winner.add(pairs.get(0)[PLAYER]);
				winValue = pairs.get(0)[ACC];
			} else {
				continue;
			}
		}

		return winner;
	}

	public static void main(String[] args)
	{
		StateDoD state = new StateDoD(BOARD_SIZE, NUM_PLAYERS, MAX_DICE);

		LazyGameTree tree = new LazyGameTree(instance, state);

		new DiceOfDoom().play(
				tree,
				new AI[] {
						null,
						new AIDoD(instance)
				});
	}
}
