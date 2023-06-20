import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception{
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // add()  remove()  element() 当队满和无元素时会报异常
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.element());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.element());

        // offer() 队满时返回false 不报异常  poll() 队空时返回null 不报异常 peek() 队空返回null 不报异常
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//
//
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.peek());

        // put() 队满时阻塞  take() 队空时阻塞
//        blockingQueue.put("a");
//        blockingQueue.put("a");
//        blockingQueue.put("a");
//        blockingQueue.put("a");
//
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();

        // 带超时时间的 offer 和 poll
//        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
//        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
//        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
//        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
//        blockingQueue.poll(2L, TimeUnit.SECONDS);
//        blockingQueue.poll(2L, TimeUnit.SECONDS);
//        blockingQueue.poll(2L, TimeUnit.SECONDS);
//        blockingQueue.poll(2L, TimeUnit.SECONDS);

        // SynchronousQueue 同步队列：只有一个值 取走后才会填入新值
        BlockingQueue<String> blockingQueue1 = new SynchronousQueue<>();
        new Thread(() -> {
            try{
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue1.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue1.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue1.put("3");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try{
                try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue1.take());
                try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue1.take());
                try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue1.take());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "BBB").start();

    }
}
