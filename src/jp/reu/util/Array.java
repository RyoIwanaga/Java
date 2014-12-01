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

	public static byte[][][] copyBBB (byte[][][] arrs) {
		int amax = arrs.length;
		int bmax = arrs[0].length;
		int cmax = arrs[0][0].length;

		byte[][][] copy = new byte[amax][bmax][cmax];
		
		for (int a = 0; a < amax; a++) {
			for (int b = 0; b < bmax; b++) {
				for (int c = 0; c < cmax; c++) {
					copy[a][b][c] = arrs[a][b][c];
				}
			}
		}
		
		return copy;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
