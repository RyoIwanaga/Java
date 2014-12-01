package jp.reu.util.game;

public abstract class State
{
	private static String strDepth = "_   ";

	public void print() {
		this.print(0);
	}

	protected static String makeStrDepth(int depth)
	{
		return jp.reu.util.Str.makeStrNTimes(depth, strDepth);
	}

	abstract public void print(int depth);
}
