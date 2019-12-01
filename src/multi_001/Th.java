package multi_001;

/**
 * synchronized 关键字
 * 针对某个对象加锁.<br>
 * <p>
 * <br>
 *
 * @Author: dawei
 * @Date: 2019.12.01 23:51
 * @params:
 * @Return:
 * @exception:
 */
public class Th {
    private int count = 10;
    private static int countStatic = 10;
    private Object o;
    public void m1(){
        synchronized (o){
            count--;
            System.out.println(Thread.currentThread().getName()+"，count="+count);
        }
    }

    /**
     * 任何线程要执行下面的内容，要先拿到this的锁
     */
    public void m2(){
        synchronized (this){
            count--;
            System.out.println(Thread.currentThread().getName()+"，count = " + count);
        }
    }

    /**
     * 等同于 synchronized(this)
     */
    public synchronized void m3() {
        count--;
        System.out.println(Thread.currentThread().getName() + "，count = " + count);
    }

    /**
     * synchronized 若用在静态方法上，相当于锁定Th.class
     */
    public synchronized static void mm1(){
        countStatic --;
        System.out.println(Thread.currentThread().getName()+"，countStatic = " +countStatic);
    }

    /**
     * 相当于上面的写法，但是不可以写synchronized(this).<br>
     * 原因：静态的属性和方法是不需要new出对象来的，对象没有new出来，就没有this引用的存在<p>
     * <br>
     *
     * @Author: dawei
     * @Date: 2019.12.01 15:43
     * @params:
     * @Return:
     * @exception:
     */
    public static void mm2(){
        synchronized (Th.class){
            countStatic -- ;
            System.out.println(Thread.currentThread().getName()+"，countStatic = "+ countStatic);
        }
    }
}
