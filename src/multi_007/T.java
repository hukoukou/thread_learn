package multi_007;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.01 19:50 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 程序在执行的过程中，如果出现异常，默认情况下，锁会释放，
 * 所以在并发的处理过程中，有异常要多加小心，不然会发生不一致的情况。<br>
 * 例如在一个web app处理过程中，多个servlet线程共同访问同一个资源，如果这时异常处理不合适，
 * 在第一个线程抛出异常时，其他线程就会步入同步代码块，有可能访问到异常数据，因此要十分小心的处理同步业务逻辑中的
 * 异常
 */
public class T {
    public int count = 0;
    public synchronized void m(){
        System.out.println(Thread.currentThread().getName()+"，start");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName()+"，count："+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5){
                //在此处抛出异常，锁被释放，若不想被释放，可以在这里进行catch，然后让循环继续进行
                int i = 1/0;
                /*try {
                    int i = 1/0;
                } catch (Exception e) {
                    //事物回滚
                    e.printStackTrace();
                    continue;
                }*/
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m(),"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->t.m(),"t2").start();
    }
}
