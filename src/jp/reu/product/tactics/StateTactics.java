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
	List<Unit> wait0;
	// initiative list of wait unit of this turn
	List<Unit> wait1;
	
	public StateTactics(int boardWidth, int boardHeight, List<Unit> units) {
		super(-1);
		
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.units = units;
		this.wait0 = Tactics.makeWaitList(units);
	}
	
	public Unit getActiveUnit() {
		if (wait0 != null) {
			return wait0.get(0);
		} else {
			return wait1.get(0);
		}
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
		int i = 0;

		printDepth(depth);
		System.out.println("=== Active unit ===");
		printDepth(depth);
		this.getActiveUnit().print();
		
		System.out.println();
		
		/*** board ***/
		
		for (int y = 0; y < this.getBoardHeight() * 2; y++) {
			
			printDepth(depth);
				
			for (int x = 0; x < this.getBoardWidth(); x++) {
				
				for (Unit u : units) {
				
					// unit
					if (u.getX() == x && u.getY() == y/2) {
						System.out.print("o");
					}
					// no unit
					else {
						System.out.print("x");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		
		/*** wait list ***/
		
		printDepth(depth);
		System.out.println("=== Wait List ===");
		for (Unit unit : wait0) {
			printDepth(depth);
			unit.print();
		}
		
		System.out.println();
	}

	public int getBoardWidth() {
		return this.boardWidth;
	}

	public int getBoardHeight() {
		return this.boardHeight;
	}

	public static void main(String[] args) {
		List<Unit> units = new ArrayList<Unit>();
		units.add(new Unit("01", 10, 3, 1, 4, 0, new Point(1, 1)));
		units.add(new Unit("02", 10, 3, 1, 4, 0, new Point(2, 2)));
		units.add(new Unit("03", 10, 3, 1, 5, 0, new Point(3, 3)));
		
		new StateTactics(12, 10, units).print();
		new StateTactics(12, 10, units).print(2);
	}
}
