package jp.reu.product.diceofdoom;


import java.util.Arrays;
import java.util.Random;

import jp.reu.util.game.State;

public class StateDoD extends State
{
	static final int HEX_PLAYER = 0;
	static final int HEX_DICE = 1;
	static final int HEX_MAX = 2;

	static final char[] DISPLAY_PLAYER = {'a', 'b', 'c', 'd', 'e'};

	// Array of Array of Pair of player and Dice
	byte[][][] board;
	int spareDice;
	boolean fFirstMove;

	public StateDoD(int size, int numPlayer, int maxDice) {
		super(0);
		this.board = makeBoard(size, numPlayer, maxDice);
		this.spareDice = 0;
		this.fFirstMove = true;
	}

	public StateDoD(byte[][][] board, int numPlayer, int maxDice) {
		super(0);
		this.board = board;
		this.spareDice = 0;
		this.fFirstMove = true;
	}


	public StateDoD(byte[][][] board, int player, int spare_dice, boolean fFirstMove) {
		super(player);
		this.board = board;
		this.spareDice = spare_dice;
		this.fFirstMove = fFirstMove;
	}

	public static byte[][][] makeBoard(int size, int numPlayer, int maxDice)
	{
		byte[][][] board = new byte[size][size][HEX_MAX];
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
		printDepth(depth);
		System.out.printf("Current player: %s, Spare dice: %d.\n\n",
				DISPLAY_PLAYER[this.player], this.spareDice);

		int i = 0;

		for (int y = 0; y < this.board.length; y++) {
			printDepth(depth);

			// print blank
			for (int n = 0; n < this.board.length - y - 1; n ++) {
				System.out.print("    ");
			}

			for (int x = 0; x < this.board[0].length; x++) {
				System.out.printf("%s-%d   ",
						DISPLAY_PLAYER[this.board[y][x][HEX_PLAYER]],
						this.board[y][x][HEX_DICE]);
			}

			System.out.println();

			printDepth(depth);

			// print blank
			for (int n = 0; n < this.board.length - y - 1; n ++) {
				System.out.print("    ");
			}

			for (int x = 0; x < this.board[0].length; x++) {
				System.out.printf("[%2d]  ", i);

				i++;
			}

			System.out.println("\n");
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.board);
		result = prime * result + (this.fFirstMove ? 1231 : 1237);
		result = prime * result + this.spareDice;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		StateDoD other = (StateDoD) obj;
		if (!Arrays.deepEquals(this.board, other.board))
			return false;
		if (this.fFirstMove != other.fFirstMove)
			return false;
		if (this.spareDice != other.spareDice)
			return false;
		return true;
	}

	public static void main(String[] args)
	{
		byte[][][] board = new byte[][][] {
				{ {1, 3}, {1, 2}, {0, 2}, },
				{ {0, 2}, {1, 1}, {0, 3}, },
				{ {0, 1}, {1, 2}, {1, 2}, },
		};
		new StateDoD(3, 2, 3).print();
	}
}
