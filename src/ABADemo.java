import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
    public static void main(String[] args) {
        System.out.println("=================以下时ABA问题的产生=================");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            //睡 1s 保证t1线程已经执行完
            try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t " + atomicReference.get());
        }, "t2").start();

        //睡 2s 保证上面两条线程已经执行完
        try{ TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("=================以下时ABA问题的解决=================");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 值：" + atomicStampedReference.getReference() + " 版本号：" + atomicStampedReference.getStamp());
            stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(101, 100, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 值：" + atomicStampedReference.getReference() + " 版本号：" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            //睡 1s 保证t1线程已经执行完
            try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            int stamp = atomicStampedReference.getStamp();
            System.out.println(atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1) + "\t 值：" + atomicStampedReference.getReference() + " 版本号：" + stamp);
        }, "t4").start();
    }

    private static void testAtomicReferenceClass() {
        User z = new User("z3", 22);
        User li = new User("li", 24);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z);

        System.out.println(atomicReference.compareAndSet(z, li) + "\t " + atomicReference.get().getUserName());
        System.out.println(atomicReference.compareAndSet(z, li) + "\t " + atomicReference.get().getUserName());
    }
}


class User{
    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    String userName;
    int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
