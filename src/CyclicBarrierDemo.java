import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("pass");
        });
        for(int i = 1; i <= 7; ++i){
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t arrived.");
                try{
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
