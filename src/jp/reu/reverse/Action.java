package jp.reu.reverse;

class Action
{
	int player;
	int x;
	int y;

	Action(int player) {
		this.player = player;
		this.x = -1;
		this.y = -1;
	}

	Action(int player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
	}

	void print()
	{
		if (this.x == -1)
			System.out.println("pass");

		else
			System.out.printf("(%d, %d)\n", x, y);
	}
}
