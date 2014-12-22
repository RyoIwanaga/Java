package jp.reu.util;

import jp.reu.util.diagram.Point;

public class Memory
{
	//// singleton ////

	private static Memory instance;
	public static Memory getInstance()
	{
		if (instance == null) {
			instance = new Memory();
		}

		return instance;
	}
	////

	private Runtime r;

	private Memory () {
		this.r = Runtime.getRuntime();
	}

	public long getTotal ()
	{
		return this.r.totalMemory();
	}

	public long getMax ()
	{
		return this.r.maxMemory();
	}

	public long getFree()
	{
		return this.r.freeMemory();
	}

	public long getUsed ()
	{
		return this.r.totalMemory() - this.getFree();
	}

	public long getTotalFree ()
	{
		return this.getMax() - this.getUsed();
	}

	public void desGC()
	{
		this.r.gc();
	}

	@Override
	public String toString()
	{
		return String.format("MEMORY used / total: %d / %d (%.2f%%) ",
				this.getUsed(), this.getTotal(),
				this.getUsed() * 100f / this.getTotal());
	}

	public void print()
	{
		System.out.println(this);
	}



    public static void main(String[] args) {
    	class Chain
    	{
    		Chain next;
    		Point p;
    	}

    	Chain c1;

    	Memory.getInstance().print();

    	c1 = new Chain();

    	Memory.getInstance().print();

    	for (int i = 0; i < 1000; i ++)
    	{
    		c1.p = new Point(i, i * i);
    		c1.next = new Chain();

    		c1 = c1.next;

    		Memory.getInstance().desGC();
    		Memory.getInstance().print();
    	}

//    	c1 = null;
//    	c2 = null;
//
//    	Memory.getInstance().print();
    }
}

