import java.awt.Point;
import java.util.Date ;

public class Test{
    public static void main(String[] args){
    	Point p1 = new Point(1, 1);
    	Point p2 = new Point(p1.x, p1.y);
    	
    	System.out.println(p1);
    	System.out.println(p2);
    	System.out.println(p1 == p2);
    }
}