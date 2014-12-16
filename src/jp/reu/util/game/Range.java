package jp.reu.util.game;

public class Range 
{
	public int min;
	public int max;
	
	public Range(int start, int add, int sub, int min, int max) {
		this.min = Math.max(min, start + sub);
		this.max = Math.min(max, start + add);
	}

	public Range(int start, int add, int min, int max) {
		this(start, add, - add, min, max);
	}

	@Override
	public String toString() {
		return "MinAndMax [min=" + this.min + ", max=" + this.max + "]";
	}

	public static void main(String[] args) {
		
		System.out.println(new Range(1, 1, 0, 4));

	}

}
