package jp.reu.util;

public class Array
{
	public static byte[][] copyBB (byte[][] bb) {
		int height = bb.length;
		int width = bb.length;
		byte[][] copy = new byte[height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x< width; x++) {
				copy[y][x] = bb[y][x];
			}
		}
		
		return copy;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
