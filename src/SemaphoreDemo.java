import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    //模拟抢车位
    public static void main(String[] args) {
        // 3个车位
        Semaphore semaphore = new Semaphore(3);
        for(int i = 1; i <= 6; ++i){
            new Thread(() -> {
                try{
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    try{ TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "\t 停车3s后离开");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    // 有占用就有释放
                    semaphore.release();
                }

            }, String.valueOf(i)).start();
        }
    }
}
