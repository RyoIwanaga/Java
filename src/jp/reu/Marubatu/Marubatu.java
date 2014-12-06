package jp.reu.Marubatu;

import java.util.ArrayList;
import java.util.List;

import jp.reu.util.Array;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.lazy.LazyTree;

public class Marubatu extends Game
{
	static final char[] DISPLAY = {
		'.',
		'o',
		'x',
	};

	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree)
	{
		List<LazyTree> moves = new ArrayList<LazyTree>();
		StateMarubatu state = (StateMarubatu)tree.getState();
		byte[][] board = state.board;
		int preStone = state.player == 0 ? 2 : 1;
		byte[][] copy;

		/*** game end ? ***/

		if (isGameEnd(board, preStone))
			return moves;

		/*** add place moves ***/

		for (int y = 0; y < board.length; y ++) {
			for (int x = 0; x < board[0].length; x ++) {
				if (state.board[y][x] == 0) {
					// COPY
					copy = Array.copyBB(board);
					copy[y][x] = (byte)(state.player + 1);

					moves.add(
							new LazyGameTree(
									new ActionPlace(state.player, x, y),
									new StateMarubatu(copy, (state.player + 1) % 2)));
				}
			}
		}

		return moves;
	}

	private static boolean isGameEnd(byte[][] board, int stone)
	{

		/*** SOME ***/
		if (isGameEndTo(board, stone, 0, 0, 1, 1)
				|| isGameEndTo(board, stone, 2, 0, -1, 1)
				||
				// left to right
				isGameEndTo(board, stone, 0, 0, 1, 0)
				|| isGameEndTo(board, stone, 0, 1, 1, 0)
				|| isGameEndTo(board, stone, 0, 2, 1, 0)
				||
				// top to bottom
				isGameEndTo(board, stone, 0, 0, 0, 1)
				|| isGameEndTo(board, stone, 1, 0, 0, 1)
				|| isGameEndTo(board, stone, 2, 0, 0, 1))
			return true;
		else
			return false;
	}

	private static boolean isGameEndTo(byte[][] board, int stone, int x, int y,
			int dx, int dy)
	{
		// Start position
		int xx = x;
		int yy = y;

		while (xx < board[0].length && yy < board.length) {
			if (board[yy][xx] == stone) {
				xx += dx;
				yy += dy;
			} else {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree)
	{
	}

	@Override
	public int scoreState(LazyGameTree tree, int player)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args)
	{
		byte[][] board = new byte[3][3];
		StateMarubatu state = new StateMarubatu(board, 0);

		LazyGameTree tree = new LazyGameTree(new Marubatu(), state);
		this.play(tree);
	}

}
