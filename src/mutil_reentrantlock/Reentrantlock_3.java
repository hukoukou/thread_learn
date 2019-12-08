package mutil_reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * date: 2019.12.08 17:24 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: <br>
 */
public class Reentrantlock_3 {
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

    /**
     * 尝试使用tryLock进行尝试上锁，不管锁定与否，方法都将继续进行.<br>
     * 可以根据tryLock返回值判断是否锁定<p>
     * 也可以指定tryLock时间，由于tryLock(time)抛出异常，所以需要注意unlock的处理，必须放到finally中进行处理<br>
     *
     * @Author: dawei
     * @Date: 2019.12.08 17:41
     * @params:
     * @Return:
     * @exception:
     */
    public void m2(){
        //第一种方式
        /*boolean b = lock.tryLock();
        System.out.println(Thread.currentThread().getName()+"...."+b);
        if(b){
            lock.unlock();
        }*/
        //第二种方式，尝试锁定5秒
        Boolean locked = false;
        try {
            locked = lock.tryLock(5,TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName()+"...."+locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(locked){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Reentrantlock_3 reentrantlock = new Reentrantlock_3();
        new Thread(()->reentrantlock.m1(),"m1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->reentrantlock.m2(),"m2").start();
    }
}
