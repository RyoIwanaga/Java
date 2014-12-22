import java.util.ArrayList;
import java.util.List;

import jp.reu.util.diagram.Point;


public class Map
{
	public static <T, R> List<R> map(Iterable<T> elements, Lambda<T, R> fn)
	{
		List<R> acc = new ArrayList<R>();
		
		for(T o : elements) {
			acc.add(fn.call(o));
			
		}
		
		return acc;
	}

	/**
	 * Add item if its not null
	 */
	public static <T, R> List<R> mapcan(Iterable<T> elements, Lambda<T, R> fn)
	{
		List<R> acc = new ArrayList<R>();
		R result;
		
		for(T o : elements) {
			result = fn.call(o);
			if (result != null) {
				acc.add(fn.call(o));
			}
		}
		
		return acc;
	}

	public static void main(String[] args)
	{
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(1, 2));
		points.add(new Point(3, 4));
		
		System.out.println(points);
		
		System.out.println(Map.map(points, new Lambda<Point, Integer>() {
			@Override
			public Integer call(Point o)
			{
				// TODO Auto-generated method stub
				return o.x;
			}
		
		}));
	}
}

interface Lambda <T, R>
{
	public R call(T o);
}