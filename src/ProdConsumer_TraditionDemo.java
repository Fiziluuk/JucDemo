import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//题目：一个初始值为 0 的变量，两个线程对其交替操作，一个 +1 一个 -1，交替执行5次
/*
    线程        操作          资源类
    判断        干活          通知
    防止虚假唤醒机制
*/

public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for(int i = 1; i <= 5; ++i){
                try{
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "AAA").start();

        new Thread(() -> {
            for(int i = 1; i <= 5; ++i){
                try{
                    shareData.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "BBB").start();
    }
}

class ShareData{
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void increment() throws Exception{
        lock.lock();
        try{
            // 1. 唤醒
            // 多线程判断必须用while 不能用if 防止虚假唤醒
            while(num != 0){
                // 等待，不能生产
                condition.await();
            }
            // 2. 干活
            num++;
            System.out.println(Thread.currentThread().getName() + "\t " + num);
            // 3. 通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception{
        lock.lock();
        try{
            // 1. 唤醒
            // 多线程判断必须用while 不能用if 防止虚假唤醒
            while(num == 0){
                // 等待，不能生产
                condition.await();
            }
            // 2. 干活
            num--;
            System.out.println(Thread.currentThread().getName() + "\t " + num);
            // 3. 通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
