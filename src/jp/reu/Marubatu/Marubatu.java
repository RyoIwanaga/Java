package jp.reu.Marubatu;

import java.util.ArrayList;
import java.util.List;

import jp.reu.util.Array;
import jp.reu.util.game.DelayedTree;
import jp.reu.util.game.Game;

public class Marubatu extends Game
{
	static final char[] DISPLAY = {
		'.',
		'o',
		'x',
	};
	
	static List<DelayedTree> makeMoves(DelayedTree tree)
	{
		ArrayList<DelayedTree> moves = new ArrayList<DelayedTree>();
		MarubatuState state = (MarubatuState)tree.state;
		byte[][] copy;
		
		for (int y = 0; y < state.board.length; y ++) {
			for (int x = 0; x < state.board[0].length; x ++) {
				if (state.board[y][x] == 0) {
					// COPY
					copy = Array.copyBB(state.board);
					copy[y][x] = (byte)(state.player + 1);
					
					moves.add(
							new GameTree(
									new ActionPlace(x, y),
									new MarubatuState(copy, (state.player + 1) % 2)));
				}
			}
		}

		return moves;
	}

	public static void main(String[] args)
	{
		byte[][] board = new byte[3][3];
		MarubatuState state = new MarubatuState(board, 0);
		GameTree tree = new GameTree(state);
		
		tree.printRec(3);
	}
}
