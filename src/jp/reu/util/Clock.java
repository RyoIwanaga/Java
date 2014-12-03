package jp.reu.util;

public class Clock
{
	long startTime;

	public Clock() {
		this.startTime = System.currentTimeMillis();
	}

	public void print(String str)
	{
		System.out.println(str +
				(System.currentTimeMillis() - this.startTime) + "milli second");
	}

	public static void main(String[] args)
	{
		Clock clock = new Clock();

		clock.print("end:");
	}

}
