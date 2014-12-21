package jp.reu.product.tactics;

import java.util.ArrayList;
import java.util.List;

import jp.reu.util.diagram.Point;
import jp.reu.util.game.State;
import jp.reu.product.tactics.units.Unit;
import jp.reu.product.tactics.units.UnitFootman;

public class StateTactics extends State 
{
	final private int boardWidth, boardHeight;

	// List of all units include dead unit
	final private List<Unit> units;
	// initiative list
	final private List<Integer> wait0;
	// initiative list of wait unit of this turn
	final private List<Integer> wait1;

	public StateTactics(int boardWidth, int boardHeight, List<Unit> units, List<Integer> wait0, List<Integer> wait1) {
		super(-1);

		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.units = units;
		this.wait0 = wait0;
		this.wait1 = wait1;
	}

	public StateTactics(int boardWidth, int boardHeight, List<Unit> units) {
		this(boardWidth, boardHeight, units,
				Tactics.makeWaitList(units), null);
	}
	
	//// Getter ////

	public int getBoardWidth()
	{
		return this.boardWidth;
	}

	public int getBoardHeight()
	{
		return this.boardHeight;
	}

	public List<Unit> getUnits()
	{
		return this.units;
	}

	public List<Integer> getWait0()
	{
		return this.wait0;
	}

	public List<Integer> getWait1()
	{
		return this.wait1;
	}

	public int getActiveUnitIndex()
	{
		if (this.wait0 != null) {
			return this.wait0.get(0);
		} else {
			return this.wait1.get(0);
		}
	}

	public Unit getActiveUnit() {
		return this.units.get(this.getActiveUnitIndex());
	}

	/**
	 * Return owner of current unit
	 */
	@Override
	public int getPlayer() {
		return this.getActiveUnit().owner;
	}
	
	////

	@Override
	public void print(int depth) {
		int n = 3;

		printDepth(depth);
//		System.out.println("=== Active unit ===");
//		printDepth(depth);
//		this.getActiveUnit().print();

		System.out.println();

		/*** board ***/

		for (int y = 0; y < this.getBoardHeight() * n; y++) {

			printDepth(depth);
			if (y%n == n-1) {
				System.out.println();
				continue;
			}

			label1:
			for (int x = 0; x < this.getBoardWidth(); x++) {

				// Draw unit
				for (Unit u : this.units) {

					if (u.isNotDead() && u.getX() == x && u.getY() == y/n) {
						if (y%n == 0) {
							System.out.printf("%-5s", u.getDisplayName());
						} else {
							System.out.printf("%5d",
								u.hp);
						}
						System.out.print(" ");
						continue label1;
					}
				}

				// Draw Floor
				if (y%n == 0) {
					System.out.print(".    ");
				} else {
					System.out.print("     ");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();

		/*** units list ***/

		printDepth(depth);
		System.out.println("=== Unit List ===");
		for (Unit u : this.units) {
			printDepth(depth);
			u.print();
		}

		System.out.println();

		/*** wait list ***/

		printDepth(depth);
		System.out.println("=== Wait List ===");
		System.out.print("* ");
		for (int unitIndex : this.wait0) {
			printDepth(depth);
			this.units.get(unitIndex).print();
		}

		System.out.println();
	}


	public static void main(String[] args)
	{
		List<Unit> units = new ArrayList<Unit>();

		units.add(new UnitFootman(0, new Point(1, 0)));
		units.add(new UnitFootman(0, new Point(1, 2)));
		units.add(new UnitFootman(0, new Point(1, 4)));

		units.add(new UnitFootman(0, new Point(7, 0)));
		units.add(new UnitFootman(0, new Point(7, 2)));
		units.add(new UnitFootman(0, new Point(7, 4)));

		new StateTactics(9, 5, units).print();
		new StateTactics(9, 5, units).print(2);
	}
}
