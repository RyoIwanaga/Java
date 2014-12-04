import java.lang.reflect.Array;


public class Swap
{

	public static void main(String[] args)
	{
		int[] pair = {0, 1};
		int[] swapped;

		System.out.println(pair);
		System.out.println(pair[0]);
		System.out.println(pair[1]);

		/*
		swap(pair);
		System.out.println(pair);
		System.out.println(pair[0]);
		System.out.println(pair[1]);
		*/

		swapped = goodSwap(pair[0], pair[1]);
		System.out.println(swapped[0]);
		System.out.println(swapped[1]);

		System.out.println(sum(1, 2, 3));
		System.out.println(sum(new int[] { 1, 2, 3}));
	}

	public static void swap (int[] pair)
	{
		int box;

		System.out.println(pair);
		box = pair[0];
		pair[0] = pair[1];
		pair[1] = box;
	}

	public static int[] goodSwap (int a, int b)
	{
		int[] result = new int[2];

		result[0] = b;
		result[1] = a;

		return result;
	}

	public static int sum (int... args)
	{
		int sum = 0;

		for (int i : args)
			sum += i;

		return sum;
	}
}
