package multi_002;

/**
 * date: 2019.12.01 15:51 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 启动五个线程，当有synchronized和没有synchronize有何不同，为什么<br>
 * 多线程对同一资源进行访问，需要对其资源进行加锁，若不加锁，输出会有重复的值
 * 当前问题叫线程重入
 * cpu（线程）缓存改变了的值没有刷新到主内存，然后又有一个新的cpu（线程）读取了原来的值，导致出现问题
 */
public class T implements Runnable{
    private int count = 10;

    /**
     * 若加锁，表示当一个线程在执行时，不允许其他线程进行访问.<br>
     * 一个synchronized代码块是原子操作，不可分<p>
     * <br>
     *
     * @Author: dawei
     * @Date: 2019.12.01 16:05
     * @params:
     * @Return:
     * @exception:
     */
    public /*synchronized*/ void run(){
        count -- ;
        System.out.println(Thread.currentThread().getName() + "，count：" + count);
    }

    /**
     * 启动五个线程.<br>
     * <p>
     * <br>
     *
     * @Author: dawei
     * @Date: 2019.12.01 15:59
     * @params:
     * @Return:
     * @exception:
     */
    public static void main(String[] args) {
        T t = new T();
        for (int i = 0; i < 5; i++) {
            new Thread(t,"THREAD" + i).start();
        }
    }
}
