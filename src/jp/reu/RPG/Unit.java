package jp.reu.RPG;

import java.util.Random;

public class Unit
{
	String name;
	int hp;
	int atk;

	public Unit(String name, int hp, int atk) {
		this.name = name;
		this.hp = hp;
		this.atk = atk;
	}

	public int attack (Unit opp) {

		Random rnd = new Random();
		int dmg = rnd.nextInt(this.atk) + 1;

		opp.hp = opp.hp - dmg;

//		System.out.printf("アクション：「%s」の攻撃、「%s」に %d ダメージを与えた。\n",
//				this.name,
//				opp.name,
//				dmg);

		return dmg;
	}

	public void print() {
		System.out.printf("情報：「%s」HP %d, 攻撃力 %d\n",
				this.name, this.hp, this.atk);
	}

	public boolean isAlive() {
		return this.hp > 0;
	}

	public boolean isDead() {
		return this.hp <= 0;
	}

	@Override
	protected Unit clone()
	{
		return new Unit(this.name, this.hp, this.atk);
	}
}