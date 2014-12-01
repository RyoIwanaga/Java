
public class CurryTest {

    static interface Func<T, R> {
        R apply(T p);
    }

    public static Func<Integer, Integer> add(final int n) {
        return new Func<Integer, Integer>() {
            @Override
			public Integer apply(Integer m) {
                return n + m;
            }
        };
    }

    public static void main(String[] args) {

        Func<Integer, Integer> inc = add(1);

        System.out.println( inc.apply(1) );
        System.out.println( inc.apply(2) );
    }
}