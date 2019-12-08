package mutil_reentrantlock;

import javax.sound.midi.Soundbank;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * date: 2019.12.08 16:45 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 使用reentrantLock（手工锁）可以完成同样的功能<br>
 *     注意：必须要手动释放锁!!!
 *     使用synchronized锁如果遇到异常，jvm可以自动释放锁，但是lock必须要手动释放，因此经常在finally中释放锁
 */
public class Reentrantlock_2 {
    Lock lock = new ReentrantLock();
    public void m1(){
        System.out.println(Thread.currentThread().getName()+"启动");
        try {
            //相当于Reentrantlock_1的synchronized(this)
            lock.lock();
            for (int i = 1; i <= 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("i："+i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName()+"结束");
    }
    public void m2(){
        lock.lock();
        System.out.println("m2.....");
        lock.unlock();
    }

    public static void main(String[] args) {
        Reentrantlock_2 reentrantlock_2 = new Reentrantlock_2();
        new Thread(()->reentrantlock_2.m1(),"m1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->reentrantlock_2.m2(),"m2").start();
    }
}
