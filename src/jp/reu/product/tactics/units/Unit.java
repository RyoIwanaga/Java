package jp.reu.product.tactics.units;

import java.awt.Point;

public class Unit {
	
	String name;
	
	int hp;
	int hpMax;
	
	int damage;
	
	int speed;
	int initiative;
	
	int owner;
	
	Point pos;

	public Unit(String name, int hp, int damage, int speed, int initiative, int owner, Point pos) {
		super();

		this.name = name;
		this.hp = hp;
		this.hpMax = hp;
		this.damage = damage;
		this.speed = speed;
		this.initiative = initiative;
		this.owner = owner;
		this.pos = pos;
	}
	
	public void print() {
		System.out.printf("[%d %d] %s(%d): hp=%d/%d dmg=%d spd=%d ini=%d \n",
				pos.x, pos.y, 
				name,
				owner,
				hp,
				hpMax,
				damage,
				speed,
				initiative
				);
	}

	public static void main(String[] args) {
		new Unit("hoge", 1, 2, 3, 4, 5, new Point(1, 2)).print();
	}

	public int getInitiative() {
		return this.initiative;
	}

	public int getX() {
		return this.pos.x;
	}

	public int getY() {
		return this.pos.y;
	}
}
