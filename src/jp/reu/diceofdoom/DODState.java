package jp.reu.diceofdoom;


import java.util.Random;
import jp.reu.util.game.State;

public class DODState extends State
{
	static final int HEX_PLAYER = 0;
	static final int HEX_DICE = 1;
	static final int HEX = 2;
	static final char[] DISPLAY_PLAYER = {'a', 'b', 'c'};

	// Array of Array of Pair of player and Dice
	byte[][][] board;
	int player;
	int spareDice;
	boolean fFirstMove;

	public DODState(int size, int numPlayer, int maxDice) {
		this.board = makeBoard(size, numPlayer, maxDice);
		this.player = 0;
		this.spareDice = 0;
		this.fFirstMove = true;
	}

	public DODState(byte[][][] board, int player, int spare_dice, boolean fFirstMove) {
		this.board = board;
		this.player = player;
		this.spareDice = spare_dice;
		this.fFirstMove = fFirstMove;
	}

	public static byte[][][] makeBoard(int size, int numPlayer, int maxDice)
	{
		byte[][][] board = new byte[size][size][HEX];
		Random rnd = new Random();

		for (int y = 0; y <size; y++) {
			for (int x = 0; x<size; x++) {
				// Set player
				board[y][x][HEX_PLAYER] = (byte)rnd.nextInt(numPlayer);
				// Set dice
				board[y][x][HEX_DICE] = (byte)(rnd.nextInt(maxDice) + 1);
			}
		}

		return board;
	}

	@Override
	public void print(int depth)
	{
		System.out.print(makeStrDepth(depth));
		System.out.printf("Current player: %s, Spare dice: %d.\n",
				DISPLAY_PLAYER[this.player], this.spareDice);

		for (int y = -1; y < this.board.length; y++) {
			System.out.print(makeStrDepth(depth));

			System.out.printf("%s ",
					y == -1 ? " " : Integer.toString(y));

			// print blank
			for (int n = 0; n < this.board.length - y; n ++) {
				System.out.print(" ");
			}

			for (int x = 0; x < this.board[0].length; x++) {
				if (y == -1) {
					System.out.printf("%d   ", x);
				} else {
				System.out.printf("%s-%d ",
						DISPLAY_PLAYER[this.board[y][x][HEX_PLAYER]],
						this.board[y][x][HEX_DICE]);

				}
			}

			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		new DODState(3, 2, 3).print();
	}
}
