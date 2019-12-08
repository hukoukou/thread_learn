package mutil_reentrantlock;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 16:36 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: Reentrantlock使用了代替synchronized<br>
 */
public class Reentrantlock_1 {
    public synchronized void m1 (){
        for (int i = 1; i <= 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"，i："+i);
        }
    }

    public synchronized void m2(){
        System.out.println("m2...");
    }

    public static void main(String[] args) {
        Reentrantlock_1 reentrantlock = new Reentrantlock_1();
        new Thread(()->reentrantlock.m1(),"m1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->reentrantlock.m2(),"m2").start();
    }
}
