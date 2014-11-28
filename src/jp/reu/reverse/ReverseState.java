package jp.reu.reverse;

import jp.reu.util.game.State;

public class ReverseState extends State
{
	byte[][] board;
	int player;
	boolean f_pass;

	ReverseState(byte[][] board, int player, boolean f_pass) {
		super();
		this.board = board;
		this.player = player;
		this.f_pass = f_pass;
	}

	@Override
	public void print(int depth)
	{
		this.printPlayer(depth);
		this.printBoard(depth);
	}

	public void printPlayer(int depth)
	{
		System.out.println();
		System.out.print(this.makeStrDepth(depth));

		System.out.printf("Current player is %s", this.player == 0 ? Reverse.DISPLAY[1]
				: Reverse.DISPLAY[2]);
	}

	public void printBoard(int depth)
	{
		for (int y = -1; y < this.board.length; y++) {

			System.out.println();
			System.out.print(this.makeStrDepth(depth));

			for (int x = -1; x < this.board[0].length; x++) {

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
				System.out.print(Reverse.DISPLAY[this.board[y][x]]);
			}
		}
	}

	public static void main(String[] args)
	{
		byte[][] board = {
				{0, 0, 0, 0},
				{0, 1, 2, 0},
				{0, 2, 1, 0},
				{0, 0, 0, 0},
		};

		new ReverseState(board, 0, false).print();
		new ReverseState(board, 0, false).print(2);

	}
}