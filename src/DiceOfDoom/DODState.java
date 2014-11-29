package DiceOfDoom;


import java.util.Random;
import jp.reu.util.game.State;

public class DODState extends State
{
	private static final int HEX_PLAYER = 0;
	private static final int HEX_DICE = 1;
	private static final int HEX = 2;
	
	// Array of Array of Pair of player and Dice
	byte[][][] board;
	int player;
	int spare_dice;
	boolean fFirstMove;
	
	public DODState(int size) {
		this.board = makeBoard(size);
		this.player = 0;
		this.spare_dice = 0;
		this.fFirstMove = false;
	}
	
	public DODState(byte[][][] board, int player, int spare_dice, boolean fFirstMove) {
		this.board = board;
		this.player = player;
		this.spare_dice = spare_dice;
		this.fFirstMove = fFirstMove;
	}
	
	public static byte[][][] makeBoard(int size)
	{
		byte[][][] board = new byte[size][size][HEX];
		Random rnd = new Random();
		
		for (int y = 0; y <size; y++) {
			for (int x = 0; x<size; x++) {
				// Set player
				board[y][x][HEX_PLAYER] = (byte)rnd.nextInt(2);
				// Set dice
				board[y][x][HEX_DICE] = (byte)(rnd.nextInt(2) + 1);
			}
		}
		
		return board;
	}

	@Override
	public void print(int depth)
	{
		for (int y = 0; y < this.board.length; y++) {
			
			// print blank
			for (int n = 0; n < this.board.length - y; n ++) {
				System.out.print(" ");
			}
			
			for (int x = 0; x < this.board[0].length; x++) {
				System.out.printf("%d-%d ", 
						board[y][x][HEX_PLAYER],
						board[y][x][HEX_DICE]);
			}
			
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		new DODState(3).print();

	}

}
