package multi_011;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.01 20:07 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: volatile关键字，使一个变量在多个线程间可见<br>
 *     A，B两个线程都用到了同一个变量，java默认在A线程中保留一份copy，这样如果B修改了这个变量，A线程未必知道。
 *     使用volatile关键字，让所有线程都会读到这个变量的修改值
 *
 *     在下面代码中，running是存在于堆存的t对象中，
 *     当线程t1开始执行时，会将running的值从内存中读到t1线程的工作区，在运行的过程中使用这个copy，并不会每次都去读
 *     取堆内存，这样，当主线程程修改了running的值后，t1线程感知不到，所以不会停止，
 *
 *     使用volatile关键字，会强制所有线程都去堆内存中读取running的值。
 *     注意：volatile关键字并不会保证多个线程共同修改running变量带来的不一致问题，所以volatile并不能代替synchronized
 *     内存的可见性，禁止重排序
 *     涉及到java的内存模型（jmm）
 */
public class T {
    public volatile Boolean running = true;

    public void m(){
        System.out.println(Thread.currentThread().getName()+"，m start");
        while (running){
            //若执行语句，cpu可能会空出一段时间，这时可能会主内存中刷一下变量的值，因此会读到变化后的running
        }
        System.out.println(Thread.currentThread().getName()+"，m end");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m(),"ti").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
