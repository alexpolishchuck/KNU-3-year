import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class task implements Runnable
{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().threadId() + " is doing task");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().threadId() + " stopped doing task");
    }
}

public class Main {
    public static void main(String[] args) {
        BarrierImpementation barrier = new BarrierImpementation(2, new task());
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i=0; i<4; i++)
        {
            threads.add(new Thread(()->{
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        for(int i=0; i<4; i++)
        {
            threads.get(i).start();
        }
    }
}