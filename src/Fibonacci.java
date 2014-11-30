import jp.reu.util.lazy.LazyStream;

public class Fibonacci extends LazyStream
{
	public static final Fibonacci fibs = new Fibonacci();

	int current;
	int previous;
	
	public Fibonacci() {
		this.current = 1;
		this.nextStream = new Fibonacci(1, 1);
	}

	public Fibonacci(int current, int previous) {
		this.current = current;
		this.previous = previous;
	}

	@Override
	public LazyStream makeNextStream()
	{
		return new Fibonacci(current + previous, current);
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.current);
	}

	public static void main(String[] args)
	{
		System.out.println(fibs);
		System.out.println(fibs.force());
		System.out.println(fibs.force().force());
		System.out.println(fibs.force().force().force());

	}

}
