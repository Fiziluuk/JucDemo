import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//题目：依次执行，AA线程打印5次，BB打印10次，CC打印15次，重复10轮
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareNewData shareNewData = new ShareNewData();
        new Thread(() -> {
            for(int i = 1; i <= 2; ++i){
                try{
                    shareNewData.print5();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "AAA").start();

        new Thread(() -> {
            for(int i = 1; i <= 2; ++i){
                try{
                    shareNewData.print10();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "BBB").start();

        new Thread(() -> {
            for(int i = 1; i <= 2; ++i){
                try{
                    shareNewData.print15();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CCC").start();
    }
}

class ShareNewData{
    private int num = 1; // A:1  B:2  C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            while(num != 1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 2;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void print10(){
        lock.lock();
        try{
            while(num != 2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 3;
            c3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            while(num != 3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 1;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
