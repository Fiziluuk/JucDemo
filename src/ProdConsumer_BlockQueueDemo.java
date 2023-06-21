import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try{
                myResource.myProduct();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "Product").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try{
                myResource.myConsumer();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "Consumer").start();

        try{TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("1s时间到，停止");
        myResource.myStop();
    }
}

class MyResource{
    private volatile boolean FLAG = true; //默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void myProduct() throws Exception{
        String data = null;
        boolean retValue;
        while(FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t此时FLAG = FALSE 不生产了");
    }
    public void myConsumer() throws Exception{
        String result = null;
        while(FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(result == null || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t超过2s没有取到数据，停止消费");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列" + result + "成功");
        }
    }
    public void myStop() throws Exception{
        this.FLAG = false;
    }
}