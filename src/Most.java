import java.util.AbstractCollection;
import java.util.List;
import java.util.ArrayList;


public class Most
{
	public static void main(String[] args)
	{
		int a = 2;

		List<Integer> lst = new ArrayList<Integer>();
		lst.add(1);
		lst.add(2);
		lst.add(3);
		lst.add(4);
		lst.add(5);
	}
	public static void hoge() {

		public int a = 2;

		class Clss
		{
			static void print()
			{
				System.out.println(a);
			}

		}

	}

	public static <E> List<E> removeIf(AbstractCollection<E> lst, Lambda f)
	{
		List<E> copy = new arrayList<E>();

		for (E item : lst) {
			if (f.<boolean, E>call(item)) {
				copy.add(item);
			}
		}

		return copy;
	}
}

interface Lambda<R, A>
{
	public R call(A a);
}