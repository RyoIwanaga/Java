package jp.reu.util;

import java.util.AbstractCollection;
import java.util.HashSet;

public class Remove
{
	public static <T> AbstractCollection<T> remove(AbstractCollection<T> list, Lambda f)
	{
		AbstractCollection<T> copy = new HashSet<T>();

		for (T item : list) {
			if (f.call(item)) {

			}
		}

		return copy;
	}

	public static void main(String[] args)
	{
		// TODO 自動生成されたメソッド・スタブ

	}
}

abstract class Lambda
{
	abstract public <T, A> T call(A a);
}