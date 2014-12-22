package jp.reu.product.tactics.units;

import java.util.ArrayList;
import java.util.List;

import jp.reu.product.tactics.StateTactics;
import jp.reu.product.tactics.Tactics;
import jp.reu.product.tactics.actions.ActionAttackRaged;
import jp.reu.util.diagram.Point;
import jp.reu.util.game.LazyGameTree;
import jp.reu.util.lists.Lists;

public class UnitRanged extends Unit
{
	public UnitRanged(String name, int hp, int damage, int speed, int initiative, int owner, Point pos)
	{
		super(name, hp, damage, speed, initiative, owner, pos);
	}

	@Override
	public List<LazyGameTree> collectRangedAttack (StateTactics s)
	{
		List<LazyGameTree> acc = new ArrayList<LazyGameTree>();
		Unit newFromUnit, newTargetUnit;
		List<Unit> units = s.getUnits();
		List<Unit> newUnits;
		int damage = s.getActiveUnit().getRagedDamage();

		for (int targetIndex = 0; targetIndex < units.size(); targetIndex++) {

			// is enemy?
			if (units.get(targetIndex).isEnemyUnit(s.getPlayer()) &&
					this.isAttackAble(units.get(targetIndex))) {
				newUnits = Lists.deepCopyArrayListOnly(
						units, new int[] {
								s.getActiveUnitIndex(), targetIndex});

				newFromUnit = newUnits.get(s.getActiveUnitIndex());
				newTargetUnit = newUnits.get(targetIndex);

				newTargetUnit.hp -= damage;

				acc.add(new LazyGameTree(
						new ActionAttackRaged(
								newFromUnit,
								units.get(targetIndex),
								newFromUnit.pos,
								damage),
						Tactics.nextTurn(s, newUnits)));
			}
		}

		return acc;
	}

	@Override
	public int getMeleeDamage()
	{
		return super.getMeleeDamage() / 2;
	}

	@Override
	public int getRagedDamage()
	{
		return super.getMeleeDamage();
	}
}
