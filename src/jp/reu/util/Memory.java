package jp.reu.util;

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
		r = Runtime.getRuntime();
	}
	
	public long getTotal ()
	{
		return r.totalMemory();
	}
	
	public long getMax ()
	{
		return r.maxMemory();
	}

	public long getFree()
	{
		return r.freeMemory();
	}
	
	public long getUsed ()
	{
		return r.totalMemory() - getFree();
	}
	
	public long getTotalFree ()
	{
		return getMax() - getUsed();
	}
	
	public void desGC()
	{
		r.gc();
	}

	@Override
	public String toString()
	{
		return String.format("MEMORY used / total: %d / %d (%.2f%%) ", 
				getUsed(), getTotal(),
				(float)getUsed() * 100f / (float)getTotal());
	}	
	
	

    public static void main(String[] args){
        Runtime r = Runtime.getRuntime() ;//現在のJavaアプリケーションに関連したRuntimeオブジェクトを返す
        System.out.println("Total JVM Memory:" + Memory.getInstance().getTotal()) ;
        System.out.println("Total JVM Memory:" + Memory.getInstance()) ;
        System.out.println("Max JVM Memory:" + Memory.getInstance().getMax()) ;
        System.out.println("Before Memory = " + Memory.getInstance().getFree()) ;
        System.out.println("Used Before Memory = " + Memory.getInstance().getUsed()) ;
        System.out.println("Used Before Memory = " + Memory.getInstance().getTotalFree()) ;
        java.util.Date d = null ;
            d = new java.util.Date() ;
        System.out.println("Total JVM Memory:" + Memory.getInstance()) ;
        for(int i = 0 ; i < 10000 ; i++){
            d = new java.util.Date() ;
            d = null ;
        }
        System.out.println("Used Before Memory = " + Memory.getInstance()) ;
        System.out.println("After Memory = " + r.freeMemory()) ;
        System.out.println("Used After Memory = " + Memory.getInstance().getUsed()) ;
        r.gc() ;
        System.out.println("Used Before Memory = " + Memory.getInstance()) ;
        System.out.println("After GC Memory = " + r.freeMemory()) ;
        System.out.println("Used After GC Memory = " + (r.totalMemory() - r.freeMemory())) ;
    }
}

