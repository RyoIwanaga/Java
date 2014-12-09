package jp.reu.product.marubatu;

import jp.reu.util.game.State;

public class StateMarubatu extends State
{
	byte[][] board;
	int player;

	public StateMarubatu(byte[][] board, int player) {
		super(player);
		this.board = board;
	}

	public StateMarubatu() {
		super(0);
		this.board = new byte [3][3];
	}

	@Override
	public void print(int depth)
	{
		printDepth(depth);
		System.out.printf("Current player is %s \n", Marubatu.DISPLAY[this.player + 1]);

		for (int y=0; y<this.board.length; y++) {
			printDepth(depth);

			for (int x=0; x<this.board[0].length; x++) {
				System.out.print(Marubatu.DISPLAY[this.board[y][x]]);
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

		new StateMarubatu(board, 0).print();
		new StateMarubatu(board, 0).print(2);
	}
}
