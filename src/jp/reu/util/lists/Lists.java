package jp.reu.util.lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.reu.util.game.Clone;

public class Lists
{
	public static List<Integer> iota (int min, int max, int range)
	{
		return iota(min, max, range, new ArrayList<Integer>());
	}

	public static List<Integer> iota0 (int max, int range)
	{
		return iota(0, max, range, new ArrayList<Integer>());
	}

	public static List<Integer> iota1 (int max, int range)
	{
		return iota(1, max, range, new ArrayList<Integer>());
	}

	public static List<Integer> iota0 (int max)
	{
		return iota(0, max, 1, new ArrayList<Integer>());
	}

	public static List<Integer> iota1 (int max)
	{
		return iota(1, max, 1, new ArrayList<Integer>());
	}

	private static List<Integer> iota (int min, int max, int range, List<Integer> acc)
	{
		if (min > max) {
			return acc;
		} else {
			acc.add(min);

			return iota(
					min + range,
					max,
					range,
					acc);
		}
	}

	//// Higher order functions ////

	public static <E> List<E> deepCopyArrayList(List<E> elements)
	{
		List<E> copy = new ArrayList<E>();
		Clone c;

		for (int i = 0; i < elements.size(); i++) {
			c = (Clone)elements.get(i);
			copy.add((E)c.clone());
		}

		return copy;
	}

	public static <E> List<E> deepCopyArrayListOnly(List<E> elements, int index)
	{
		return deepCopyArrayListOnly(elements, new int[] {index});
	}

	public static <E> List<E> deepCopyArrayListOnly(List<E> elements, int[] indexs)
	{
		List<E> copy = new ArrayList<E>();
		Clone c;

		out:
		for (int i = 0; i < elements.size(); i++) {

			// for target index
			for (int index : indexs) {
				if (i == index) {
					c = (Clone)elements.get(i);
					copy.add((E)c.clone());

					continue out;
				}
			}

			// shallow copy
			copy.add(elements.get(i));
		}

		return copy;
	}

	public static <E> List<E> filter(List<E> elements, Identifer<E> fn)
	{
		List<E> acc = new ArrayList<E>();

		for (E o : elements) {
			if (fn.is(o)) {
				acc.add(o);
			}
		}

		return acc;
	}

	public static <E> E find1(List<E> elements, Identifer<E> fn)
	{
		for (E o : elements) {
			if (fn.is(o)) {
				return o;
			}
		}

		return null;
	}

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

	public static <T> void mapc(Iterable<T> elements, LambdaVoid<T> fn)
	{
		for(T o : elements) {
			fn.call(o);
		}
	}

	public static <T> List<T> sublist(Iterable<T> elements, int start, int end)
	{
		int i = 0;
		List<T> lst = new ArrayList<T>();

		for (T item : elements) {

			if (i >= start)
				lst.add(item);

			i++;

			if (i == end)
				break;
		}

		return lst;
	}

	public static <T> List<T> sublist(Iterable<T> elements, int end)
	{
		return sublist(elements, 0, end);
	}

	public static void main(String[] args) {

		//// Test sublist ////

		System.out.println("\n//// sublist ////");
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(0);
		lst.add(1);
		lst.add(2);
		lst.add(3);
		lst.add(4);

		System.out.println(lst);
		System.out.println(sublist(lst, 2, 4));

		// TODO Auto-generated method stub

		System.out.println(iota(1, 5, 1));
		System.out.println(iota(1, 5, 2));

	}
}
