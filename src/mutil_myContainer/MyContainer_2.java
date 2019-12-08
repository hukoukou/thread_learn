package mutil_myContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 14:37 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 承接上一个，使用wait和notify，wait会释放锁，notify则不会释放锁，但是这个方法必须让第二个线程先执行，也就是首先让
 * 第二个线程先监听才可以<br>
 *     当前程序无法实现，原因是notify不会释放所，即使通知了t2线程，但是锁没有释放，t2也不会向下执行
 *     MyContainer_3优化
 */
public class MyContainer_2 {
    private List lists = new ArrayList();
    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer_2 myContainer = new MyContainer_2();

        final Object lock = new Object();

        new Thread(()->{
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "：启动");
                if (myContainer.size()!=5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+"：结束");
            }

        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：启动");
            synchronized (lock){
                for (int i = 1; i <= 10; i++) {
                    myContainer.add(new Object());
                    System.out.println("add："+i);

                    if (myContainer.size()==5){
                        lock.notifyAll();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();
    }
}
