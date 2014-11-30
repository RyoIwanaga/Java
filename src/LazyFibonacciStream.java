public class LazyFibonacciStream
{
	public static final LazyFibonacciStream Fibonaccis = new LazyFibonacciStream();

	int current;
	int previous;
	LazyFibonacciStream next;
	
	LazyFibonacciStream() {
		this.current = 1;
		this.next = new LazyFibonacciStream(1, 1);
	}

	LazyFibonacciStream(int current, int previous) {
		this.current = current;
		this.previous = previous;
	}
	
	public LazyFibonacciStream getNext()
	{
		if (next == null) {
			this.next = new LazyFibonacciStream(current + previous, current);
		}

		return next;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.current);
	}
	
	public static void main(String[] args)
	{
		System.out.println(Fibonaccis);
		System.out.println(Fibonaccis.getNext());
		System.out.println(Fibonaccis.getNext().getNext());
		
		LazyFibonacciStream stream = Fibonaccis;
		
		for (int i=0; i < 30; i++) {
			System.out.printf("%dth value is %s.\n", i, stream);
			stream = stream.getNext();
		}
	}
}
