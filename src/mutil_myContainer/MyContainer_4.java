package mutil_myContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 15:35 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 承接MyContainer_3优化，使用Latch(门闩)进行优化，代替notify和wait<br>
 *     好处：通信简单，同时也可以指定等待时间
 *     使用await和countdown方法代替wait和notify
 *     CountdownLatch不涉及锁定，当count值为0时，当前线程继续执行
 *     当不涉及同步，只涉及通信时，用synchronized+wait+notify就显得太重了
 *     这时候就应该考虑countdownLatch/CyclicBarrier/Semaphore
 */
public class MyContainer_4 {
    //添加volatile，使t2能够得到通知
    private volatile List lists = new ArrayList();
    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer_4 myContainer = new MyContainer_4();
        //设置countDown初始值为1，当值为0时，门闩打开，程序向下执行
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //启动t2线程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"启动");
            if (myContainer.size() != 5){
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动t1线程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"启动");
            for (int i = 1; i <= 10; i++) {
                myContainer.add(new Object());
                System.out.println("add："+i);
                if(myContainer.size()==5){
                    //打开门闩，让t2得以执行，countDown的值-1
                    countDownLatch.countDown();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        },"t1").start();
    }
}
