package mutil_016;

/**
 * date: 2019.12.08 01:07 <br>
 * author: dawei <br>
 * version: 1.0 <br>
 * description: 不要以字符串常量作为锁的对象<br>
 *     在下面例子中s1和s2锁定的是同一个对象
 */
public class T {
    String s1 = "hello";
    String s2 = "hello";
    void m1(){
        synchronized (s1){

        }
    }
    void m2(){
        synchronized (s2){

        }
    }
}
