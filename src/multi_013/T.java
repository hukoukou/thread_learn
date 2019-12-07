package multi_013;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * date: 2019.12.08 00:17 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 承接012，同样的问题，使用AtomXXX类，效率更高<br>
 *     AtomXXX类本身方法都是原子操作，但是不能保证，多个方法连续调用是原子性的
 *     在AtomXXX类两个方法执行的过程中，可能会有其他线程插进来，所以，多个方法连续方法调用时，不是原子性的
 */
public class T {
    private AtomicInteger atomCount = new AtomicInteger(0);
    public void m(){
        for (int i = 0; i < 100000; i++) {
            atomCount.incrementAndGet();//用来替代count++，使用了相当底层的方法进行实现的（count++不具备原子性）
        }
    }

    public static void main(String[] args) {
        T t = new T();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(()->t.m(),"Thread"));
        }
        threads.forEach(o->o.start());
        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.atomCount);
    }
}
