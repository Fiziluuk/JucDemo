import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for(int i = 1; i <= 20; ++i){
            new Thread(() -> {
                for(int j = 1; j <= 1000; ++j){
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        //等待上方20个线程都执行完
        //如果活跃的线程数大于2，主线程就等待
        //为什么是2，后台有两个线程，主线程和GC线程
        while(Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t get finally number value: " + myData.num);
        System.out.println(Thread.currentThread().getName() + "\t get finally atomic number value: " + myData.atomicInteger);
    }

    //volatile 关键字可以保证可见性 及时通知其他线程主内存的值已经被修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value: " + myData.num);
        }, "AAA").start();
        while(myData.num == 0){}
        System.out.println("mission is over! Main get number value: " + myData.num);
    }
}
class MyData{
    volatile int num = 0;
    public void addTo60(){
        this.num = 60;
    }
    public void addPlusPlus(){
        this.num++;
    }
    //解决 i++ 的原子性问题 使用atomicInteger
    //构造方法默认是0
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        //自增 1
        atomicInteger.getAndIncrement();
        //每次以括号内的数字增加
        //atomicInteger.getAndAdd(4);
    }
}
