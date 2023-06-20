import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListNotSafe {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();

        //java.util.ConcurrentModificationException 并发修改异常 ArrayList线程不安全
        for(int i = 1; i <= 300; ++i){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
