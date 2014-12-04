
public class Test
{
	public static void main(String[] args)
	{
		int[] hoge1 = {1, 2, 3};
		int[] hoge2 = {1, 2, 3};

		System.out.println(hoge1);
		System.out.println(hoge2);
		System.out.println(hoge1.length);
		System.out.println(hoge2.length);
		System.out.println(hoge1 == hoge2);
		System.out.println(hoge1.equals(hoge2));
	}
}
