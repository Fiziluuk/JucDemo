import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        // 多个线程公用一个futureTask，只有一个线程会执行。
        new Thread(futureTask, "AAA").start();
        new Thread(futureTask, "BBB").start();
        //int res2 = futureTask.get(); // get() 要求callable线程计算完成才能获取到，如果没计算完成就获取，回阻塞main线程，直到计算完成
        System.out.println(Thread.currentThread().getName() + "******");
        int res1 = 100;

        while(!futureTask.isDone()){}
        int res2 = futureTask.get();

        System.out.println(res2 + res1);
    }
}

class MyThread1 implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "come in.");
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 111;
    }
}