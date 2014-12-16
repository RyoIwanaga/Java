package jp.reu.product.tactics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import jp.reu.util.game.State;
import jp.reu.product.tactics.units.Unit;

public class StateTactics extends State {

	int boardWidth, boardHeight;
	
	// List of all units include dead unit
	List<Unit> units;
	// initiative list
	List<Integer> wait0;
	// initiative list of wait unit of this turn
	List<Integer> wait1;
	
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

	public int getActiveUnitIndex()
	{
		if (wait0 != null) { 
			return wait0.get(0);
		} else {
			return wait1.get(0);
		}
	}
	
	public Unit getActiveUnit() {
		return units.get(getActiveUnitIndex());
	}
	
	/**
	 * Return owner of current unit
	 */
	@Override
	public int getPlayer() {
		return 0;
	};

	@Override
	public void print(int depth) {
		int n = 3;

		printDepth(depth);
		System.out.println("=== Active unit ===");
		printDepth(depth);
		this.getActiveUnit().print();
		
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
				for (Unit u : units) {
					
					if (u.getX() == x && u.getY() == y/n) {
						if (y%n == 0) {
							System.out.printf("%-5s",
								u.name);
						} else {
							System.out.printf("%d|%3d",
								u.owner, u.hp);
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
		for (Unit u : units) {
			printDepth(depth);
			u.print();
		}
		
		System.out.println();

		/*** wait list ***/
		
		printDepth(depth);
		System.out.println("=== Wait List ===");
		for (int unitIndex : wait0) {
			printDepth(depth);
			units.get(unitIndex).print();
		}
		
		System.out.println();
	}

	public int getBoardWidth() {
		return this.boardWidth;
	}

	public int getBoardHeight() {
		return this.boardHeight;
	}

	public static void main(String[] args) 
	{
		List<Unit> units = new ArrayList<Unit>();
		units.add(new Unit("01", 10, 3, 1, 4, 0, new Point(1, 1)));
		units.add(new Unit("02", 10, 3, 1, 4, 1, new Point(2, 2)));
		units.add(new Unit("03", 10, 3, 1, 5, 0, new Point(3, 3)));
		
		new StateTactics(9, 5, units).print();
		new StateTactics(9, 5, units).print(2);
	}
}
