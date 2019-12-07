package multi_015;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 00:48 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 锁定某个对象o，如果对象o的某个属性发生改变，则不影响锁的使用，
 * 如果o变成了另一个对象，则锁定的对象也发生改变<br>
 * 应该避免将锁定对象的引用变成另外的对象
 */
public class T {
    Object o = new Object();

    public void m() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        //启动第一个线程
        new Thread(()->t.m(),"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //创建第二个线程
        Thread t2 = new Thread(() -> t.m(), "t2");
        //锁对象发生改变，所以t2线程得以执行，如果注释掉，线程t2永远也不会执行
        t.o = new Object();
        t2.start();
    }
}
