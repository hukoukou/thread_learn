package multi_014;

import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.08 00:34 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: synchronized语句优化<br>
 *     同步代码块中的语句越少越好
 *     对比m1和m2
 */
public class T {
    private int count = 0;
    public synchronized void m1(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++ ;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void m2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //业务代码块中只有下面的语句需要sync，这时就不应该给整个代码块上锁
        //采用细粒度的锁，可以使线程争用时间变短，从而提高效率*/
        synchronized (this){
            count ++ ;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
