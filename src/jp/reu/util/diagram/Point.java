package jp.reu.util.diagram;

public class Point
{
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	@Override
	public String toString()
	{
		return "Point [x=" + this.x + ", y=" + this.y + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		return true;
	}

	public static void main(String[] args)
	{
		Point p1 = new Point(1, 2);

		System.out.println(p1);


	}
}
