public class IntegerStream
{
	public static final IntegerStream Integers = new IntegerStream(0);

	int n;
	
	IntegerStream(int n) {
		this.n = n;
	}
	
	public IntegerStream getNext() {
		return new IntegerStream(this.n + 1);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.n);
	}
	
	public static void main(String[] args)
	{
		System.out.println(Integers);
		System.out.println(Integers.getNext());
		System.out.println(Integers.getNext().getNext());
		
		long timeStart, timeStop;
		timeStart = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++) {
			Integers.getNext().getNext();
		}
		for (int i = 0; i < 10000; i++) {
			Integers.getNext().getNext();
		}
		for (int i = 0; i < 10000; i++) {
			Integers.getNext().getNext();
		}

		timeStop = System.currentTimeMillis();
		System.out.printf("実行にかかった時間は%dミリ秒です。\n", timeStop - timeStart);
	}
}
