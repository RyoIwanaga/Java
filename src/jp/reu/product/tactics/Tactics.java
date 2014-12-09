package jp.reu.product.tactics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.reu.product.tactics.units.Unit;
import jp.reu.util.game.Game;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.lazy.LazyTree;

public class Tactics extends Game {

	public static List<Unit> makeWaitList(List<Unit> units) {
		
		class UnitComperator implements Comparator<Unit> {

			@Override
			public int compare(Unit o1, Unit o2) {
				// compare initiative;
				int ia = o1.getInitiative();
				int ib = o2.getInitiative();
				
				if (ia == ib) return 0;
				else if (ia >= ib) return -1;
				else return 1;
			}
		}
		
		ArrayList<Unit> wait = new ArrayList<Unit>(units);
		Collections.sort(wait, new UnitComperator());

		return  wait;
	}
	
	@Override
	public List<LazyTree> makeBranches(LazyGameTree tree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> winner(LazyGameTree tree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int scoreState(LazyGameTree tree, int player) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
