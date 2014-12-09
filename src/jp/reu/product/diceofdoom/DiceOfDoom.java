package jp.reu.product.diceofdoom;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.reu.util.Array;
import jp.reu.util.Clock;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.game.ais.AI;
import jp.reu.util.lazy.LazyTree;

public class DiceOfDoom extends Game
{
	public static final DiceOfDoom instance = new DiceOfDoom();

	static final int NUM_PLAYERS = 2;
	static final int MAX_DICE = 3;
	static final int BOARD_SIZE = 3;

	// Memoize
	protected static Map<LazyGameTree, List<LazyTree>> historyMakeBranches = new HashMap<LazyGameTree, List<LazyTree>>();
	protected static Map<Point, Set<Point>> historyNeighbor = new HashMap<Point, Set<Point>>();

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> result;

		result = historyMakeBranches.get(tree);

		if (result == null) {
			result = this.makeBranchesMemo(tree);
			historyMakeBranches.put(tree, result);
		}

		return result;
	}

	public List<LazyTree> makeBranchesMemo(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateDoD state = (StateDoD)tree.getState();
		byte[][][] board = state.board;
		byte[][][] boardCopy;
		int player = state.getPlayer();

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
					for (Point p: collectNeighborPoints(from)) {

						if (isAttackable(board, from, p)) {

							// make new board
							boardCopy = Array.copyBBB(board);
							boardCopy[p.y][p.x][StateDoD.HEX_PLAYER] = (byte)player;
							// set dice 1
							boardCopy[y][x][StateDoD.HEX_DICE] = 1;
							// move dice
							boardCopy[p.y][p.x][StateDoD.HEX_DICE] = (byte)(board[y][x][StateDoD.HEX_DICE] - 1);

							moves.add(new LazyGameTree(
									new ActionAttack(player, from, p,
											board.length * from.y + from.x,
											board.length * p.y + p.x
											),
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

	public static Set<Point> collectNeighborPointsMemo(Point p)
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

	public static Set<Point> collectNeighborPoints(Point p)
	{
		Set<Point> result;

		result = historyNeighbor.get(p);

		if (result == null) {
			result = collectNeighborPointsMemo(p);
			historyNeighbor.put(p, result);
		}

		return result;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
		int[] scores = new int[NUM_PLAYERS];

		List<Integer> winner = new ArrayList<Integer>();
		int winValue;
		StateDoD s = (StateDoD)tree.getState();
		byte[][][] board = s.board;

		//// Count occupied area of each players ////

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				scores[board[y][x][StateDoD.HEX_PLAYER]]++;
			}
		}

		//// collect winner ////

		winner.add(0);
		winValue = scores[0];

		for (int i = 1; i < NUM_PLAYERS; i++) {
			if (scores[i] == winValue) {
				winner.add(i);
			} else if (scores[i] > winValue) {
				winner = new ArrayList<Integer>();
				winner.add(i);
				winValue = scores[i];
			} else {
				continue;
			}
		}

		return winner;
	}

	@Override
	public int scoreState(LazyGameTree tree, int player)
	{
		StateDoD state = (StateDoD)tree.getState();
		byte[][][] board = state.board;

		int sum = 0;

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board.length; x++) {
				if (board[y][x][StateDoD.HEX_PLAYER] == player) {
					sum += board[y][x][StateDoD.HEX_DICE];
				} else {
					sum -= board[y][x][StateDoD.HEX_DICE];
				}
			}
		}

		return sum;
	}

	public static void main(String[] args)
	{
		if (true) {

		}
		byte[][][] board = new byte[][][] {
				{ {1, 3}, {1, 2}, {0, 2}, },
				{ {0, 2}, {1, 1}, {0, 3}, },
				{ {0, 1}, {1, 2}, {1, 2}, },
		};
//		StateDoD state = new StateDoD(board, NUM_PLAYERS, MAX_DICE);
//		StateDoD state = new StateDoD(board, 1, 0, true);

		StateDoD state = new StateDoD(BOARD_SIZE, NUM_PLAYERS, MAX_DICE);
		LazyGameTree tree = new LazyGameTree(instance, state);

		if (false) {
			Clock time = new Clock();
			tree.forceRec(12);
			time.print("force: ");

		} else if (true) {
			new DiceOfDoom().play(
					tree,
					new AI[] {
							//						new AIDoD(instance),
							//new AI(instance, 4),
							null,
							new AI(instance, 3),
							null,
					});
		}
	}
}
