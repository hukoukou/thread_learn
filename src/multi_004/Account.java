package multi_004;

import com.sun.org.apache.xml.internal.serializer.ToHTMLStream;

import java.awt.image.BandCombineOp;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * date: 2019.12.01 16:47 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 业务上加锁与不加锁的脏读问题（dir）<br>
 * 只对写加锁，不对读加锁，在执行锁定的存方法时，有可能其他线程对非锁定方法执行，导致业务上数据存在脏读问题
 * 临界资源访问加锁
 * 若业务允许存在脏读，则可以不加锁
 */
public class Account {
    private String name;
    private double balance;

    public synchronized void set(String name,double balance){
        this.name = name;
        try {
            //两秒后才存上，如果在这存的过程中读，那么就会存在脏读问题
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Account account = new Account();
        new Thread(()->account.set("zhangsan",100.0)).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getBalance("zhangsan"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getBalance("zhangsan"));

    }
}
