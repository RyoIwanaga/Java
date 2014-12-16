
public class TestLambda {

	public static void main(String[] args) {
		int a = 2;
		final int b = a;

		Runnable runner = new Runnable() {
			public void run() {
				System.out.println(b);
			}
		};
		
		runner.run();
	}
}

