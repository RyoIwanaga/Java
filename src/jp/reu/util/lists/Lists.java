package jp.reu.util.lists;

import java.util.ArrayList;
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(iota(1, 5, 1));
		System.out.println(iota(1, 5, 2));
	}
}
