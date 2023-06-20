import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//题目：一个初始值为 0 的变量，两个线程对其交替操作，一个 +1 一个 -1，交替执行5次
/*
    线程        操作          资源类
    判断        干活          通知
*/

public class ProdConsumer_TraditionDemo {

}

class ShareData{
    private int num = 0;
    private Lock lock = new ReentrantLock();
}
