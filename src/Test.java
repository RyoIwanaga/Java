import java.util.Date ;

public class Test{
    public static void main(String[] args){
        Runtime r = Runtime.getRuntime() ;//現在のJavaアプリケーションに関連したRuntimeオブジェクトを返す
        System.out.println("Total JVM Memory:" + r.totalMemory()) ;
        System.out.println("Before Memory = " + r.freeMemory()) ;
        System.out.println("Used Before Memory = " + (r.totalMemory() - r.freeMemory())) ;
        Date d = null ;
        for(int i = 0 ; i < 10000 ; i++){
            d = new Date() ;
            d = null ;
        }
        System.out.println("After Memory = " + r.freeMemory()) ;
        System.out.println("Used After Memory = " + (r.totalMemory() - r.freeMemory())) ;
        r.gc() ;
        System.out.println("After GC Memory = " + r.freeMemory()) ;
        System.out.println("Used After GC Memory = " + (r.totalMemory() - r.freeMemory())) ;
    }
}