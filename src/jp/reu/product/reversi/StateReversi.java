package jp.reu.product.reversi;

import jp.reu.util.game.State;

public class StateReversi extends State
{
	byte[][] board;
	boolean f_pass;
	int turn;

	public static final char[] DISPLAY = {
		'□', '●', '○',
	};

	StateReversi(byte[][] board, int player, boolean f_pass, int turn) {
		super(player);
		this.board = board;
		this.f_pass = f_pass;
		this.turn = turn;
	}

	StateReversi(int size) {
		super(0);

		this.board = makeBoard(size);
		this.f_pass = false;
		this.turn = 1;
	}

	private static byte[][] makeBoard(int size)
	{
		int harf = size / 2;
		byte[][] board = new byte[size][size];

		board[harf - 1][harf - 1] = Reversi.STONE_BLACK;
		board[harf][harf] = Reversi.STONE_BLACK;

		board[harf][harf - 1] = Reversi.STONE_WHITE;
		board[harf - 1][harf] = Reversi.STONE_WHITE;

		return board;
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
		System.out.print(makeStrDepth(depth));

		System.out.printf("[%2d] Current player is %s.\n",
				this.turn,
				DISPLAY[this.player + 1]);
	}

	public void printBoard(int depth)
	{
		for (int y = -1; y < this.board.length; y++) {

			System.out.print(makeStrDepth(depth));

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
				System.out.print(DISPLAY[this.board[y][x]]);
			}
			System.out.println();
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

		new StateReversi(board, 0, false, 0).print();
		new StateReversi(board, 0, false, 1).print(2);

	}
}