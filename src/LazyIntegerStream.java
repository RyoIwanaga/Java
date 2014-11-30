public class LazyIntegerStream
{
	public static final LazyIntegerStream Integers = new LazyIntegerStream(0);

	int n;
	LazyIntegerStream next;
	
	LazyIntegerStream(int n) {
		this.n = n;
	}
	
	public LazyIntegerStream getNext() {
		if (this.next == null) {
			this.next = new LazyIntegerStream(this.n + 1);
		}
		
		return next;
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
