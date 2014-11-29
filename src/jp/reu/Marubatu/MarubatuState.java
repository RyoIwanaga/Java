package jp.reu.Marubatu;

import jp.reu.util.game.State;

public class MarubatuState extends State
{
	byte[][] board;
	int player;
	
	public MarubatuState(byte[][] board, int player) {
		super();
		this.board = board;
		this.player = player;
	}
	
	@Override
	public void print(int depth)
	{
		System.out.print(this.makeStrDepth(depth));
		System.out.printf("Current player is %s \n", Marubatu.DISPLAY[this.player + 1]);

		for (int y=0; y<board.length; y++) {
			System.out.print(this.makeStrDepth(depth));

			for (int x=0; x<board[0].length; x++) {
				System.out.print(Marubatu.DISPLAY[board[y][x]]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		byte[][] board = {
			{0, 1, 2},
			{0, 0, 0},
			{0, 1, 2},
		};

		new MarubatuState(board, 0).print();
		new MarubatuState(board, 0).print(2);
	}
}
