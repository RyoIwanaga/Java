package jp.reu.util.game;

import java.util.Scanner;

public class Game
{
	private static DelayedGameTree hundleHuman(DelayedGameTree tree)
	{
		Scanner scan = new Scanner(System.in);
		int i = 0;
		int number;
		
		DelayedGameTree t;

		System.out.println("\nChoose your moves:");

		// Print actions
		for (DelayedTree move : tree.forceBranches()) {
			System.out.print(i + ": ");
			t = (DelayedGameTree)move;
			t.action.print();

			i++;
		}

		try {
			number = scan.nextInt();
			
			System.out.println("hhh");
			return (DelayedGameTree)tree.forceBranches().get(number);
			
		} catch (Exception e) {
			return tree;
		} finally {
			scan.close();
		}
	}

	public static void play(DelayedGameTree tree)
	{
		tree.print();

		if (!tree.forceBranches().isEmpty()) {
			play(hundleHuman(tree));
		}
	}
}
