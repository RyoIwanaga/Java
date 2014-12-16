package jp.reu.product.tactics.units;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import jp.reu.product.tactics.StateTactics;
import jp.reu.product.tactics.Tactics;
import jp.reu.product.tactics.actions.ActionAttackRaged;
import jp.reu.util.Lists;
import jp.reu.util.game.LazyGameTree;

public class UnitRanged extends Unit
{
	public UnitRanged(String name, int hp, int damage, int speed, int initiative, int owner, Point pos)
	{
		super(name, hp, damage, speed, initiative, owner, pos);
	}

	@Override
	public List<LazyGameTree> collectRangedAttackResult (StateTactics oldState)
	{
		List<LazyGameTree> result = new ArrayList<LazyGameTree>();
		Unit newFromUnit, newTargetUnit;
		List<Unit> units = oldState.units;
		List<Unit> newUnits;

		for (int targetIndex = 0; targetIndex < units.size(); targetIndex++) {

			// is enemy?
			if (units.get(targetIndex).isEnemyUnit(oldState.getPlayer()) &&
					this.isAttackAble(units.get(targetIndex))) {
				newUnits = Lists.deepCopyArrayListOnly(
						units, new int[] {
								oldState.getActiveUnitIndex(), targetIndex});

				newFromUnit = newUnits.get(oldState.getActiveUnitIndex());
				newTargetUnit = newUnits.get(targetIndex);

				newTargetUnit.hp -= newFromUnit.damage;

				result.add(new LazyGameTree(
						new ActionAttackRaged(
								newFromUnit,
								units.get(targetIndex),
								newFromUnit.pos,
								newFromUnit.damage),
						Tactics.nextTurn(oldState, newUnits)));
			}
		}

		return result;
	}

	@Override
	public int getMeleeDamage()
	{
		return super.getMeleeDamage() / 2;
	}
}
