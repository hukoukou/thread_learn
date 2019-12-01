package multi_005;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.01 17:07 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 一个同步方法可以调用另一个同步不方法，一个线程已经拥有了某个对象的锁，再次申请是仍然会获得该对象的锁，
 * 也就是说synchronized获得的锁是可重入的<br>
 * 同一线程，同一把锁，所以可以
 */
public class T {
    public synchronized void m1(){
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 end");
        m2();
    }
    public synchronized void m2(){
        System.out.println("m2 start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m1()).start();
    }
}
