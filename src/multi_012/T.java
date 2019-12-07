package multi_012;


import java.util.ArrayList;
import java.util.List;

/**
 * date: 2019.12.07 23:38 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: volatile和synchronized到底有啥区别<br>
 *     volatile并不能保证多个线程共同修改同一变量带来的不一致问题，也就是说volatile不能代替synchronized
 *     volatile只保证可见性，不保证原子性
 *     synchronized既保证可见性，又保证原子性
 *     synchronized的效率较低
 */
public class T {
    private volatile int count = 0;
    public /*synchronized*/ void m (){
        for (int i = 0; i < 100000; i++) {
            count ++ ;
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        //创建5个线程
        for (int i = 0; i < 5 ; i++) {
            threads.add(new Thread(()->t.m(),"Thread："+i));
        }
        //启动所有线程
        threads.forEach((o)->o.start());
        //等待所有线程结束，再执行主线程
        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}
