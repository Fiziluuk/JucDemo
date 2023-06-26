import java.util.concurrent.*;

public class MyThreadPoolDemo{
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        try{
            for(int i = 1; i <= 10; ++i){
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t coming");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}