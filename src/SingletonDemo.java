public class SingletonDemo {
    private static volatile SingletonDemo instance = null;
    private SingletonDemo (){
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo ()");
    }
    // DCL (double check lock)双端检锁机制 由于存在指令重排 所以还不是完全安全 需要加入 volatile
    private static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (SingletonDemo.class){
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }

        }
        return instance;
    }
    public static void main(String[] args) {
        // 单线程模式没问题
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        // 多线程 不能维持单例模式
        for(int i = 1; i <= 10; ++i){
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
