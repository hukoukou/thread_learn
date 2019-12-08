package mutil_myContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 15:22 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 当前方法使用notify和wait较为繁琐，整个通信过程比较复杂<br>
 *     MyContainer_4继续优化
 */
public class MyContainer_3 {
    private List lists = new ArrayList();
    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer_3 myContainer = new MyContainer_3();
        final Object lock = new Object();
        //启动监控线程2
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：启动");
            synchronized (lock){
                if(myContainer.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notifyAll();
                System.out.println(Thread.currentThread().getName()+"：结束");
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动线程1
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"启动");
            synchronized (lock){
                for (int i = 1; i <= 10 ; i++) {
                    myContainer.add(new Object());
                    System.out.println(Thread.currentThread().getName()+"：add，"+i);
                    if(myContainer.size() == 5){
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        },"t1").start();
    }
}
