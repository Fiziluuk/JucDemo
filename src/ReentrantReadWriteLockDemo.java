import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; ++i){
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }
        try{ TimeUnit.MILLISECONDS.sleep(3000); }catch (InterruptedException e){ e.printStackTrace(); }
        for (int i = 1; i <= 5; ++i){
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    public  void put(String key, Object value){
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t is writing:" + key);
            try{ TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t has writed:" + key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }

    }

    public  void get(String key){
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t is reading:" + key);
            try{ TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
            Object res = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t has read:" + res);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }

    }
}
