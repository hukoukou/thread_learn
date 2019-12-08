package mutil_myContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 14:09 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 面试题：实现一个容器，提供add和size两个方法<br>
 *     写两个线程，线程1添加10个元素到线程中，线程2监控容器中元素的个数，当个数等于5的时候，线程2给出提示并结束
 *     不完美：1.当myContainer.size == 5的时候，另一个线程又向上增加了一个
 *            2.while(true)，死循环，浪费次cpu
 */
public class MyContainer_1 {
    private volatile List lists = new ArrayList();
    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer_1 myContainer = new MyContainer_1();
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                myContainer.add(new Object());
                System.out.println("add:"+i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread_1").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"开始");
            while (true){
                if (myContainer.size()==5){
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        },"Thread_2").start();
    }
}
