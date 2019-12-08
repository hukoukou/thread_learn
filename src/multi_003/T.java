package multi_003;

/**
 * date: 2019.12.01 16:20 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 同步方法和非同步方法是否可以同时调用<br>
 * m1在执行时必须要申请对象的锁，但是m2执行时不需要
 *
 */
public class T {
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"，m1 start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"，m1 end");
    }

    public /*synchronized*/ void m2(){
        System.out.println(Thread.currentThread().getName()+"，m2 start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"，m2 end");
    }

    public static void main(String[] args) {
        T t = new T();

        //java8的lambda表达式
        //new Thread(()->t.m1(),"t1").start();
        //new Thread(()->t.m2(),"t2").start();
        //或者
        //new Thread(t::m1,"t1").start();
        //new Thread(t::m2,"t2").start();
        //同等于
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
                }
            },"t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        },"t2").start();
    }
}


