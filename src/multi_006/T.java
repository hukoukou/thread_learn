package multi_006;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.01 19:41 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 一个同步方法可以调用另一个同步不方法，一个线程已经拥有了某个对象的锁，再次申请是仍然会获得该对象的锁，
 * 这里是继承可能发生的情形，子类调用父类的同步方法<br>
 */
public class T {
    public synchronized void m(){
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new TT().m();
    }
}
class TT extends T{
    public synchronized void m(){
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}
