import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierImpementation {

    public BarrierImpementation(int number, Runnable task){
        if (number <=0)
            throw new IllegalArgumentException("Number of threads should be more than zero");
        number_of_threads = number;
        this.task = task;
        counter = new AtomicInteger(0);
        flag = new AtomicBoolean(false);
        all_left = new AtomicInteger(0);
    }

    public void await() throws InterruptedException {
        synchronized (number_of_threads) {
            counter.incrementAndGet();
            while (counter.get() < number_of_threads || all_left.get() > 0)
            {
                number_of_threads.wait();
                if(counter.get() >= number_of_threads)
                    break;
            }

            all_left.incrementAndGet();
            if (counter.get() >= number_of_threads && all_left.get() == 1) {


                for (int i = 0; i < number_of_threads - 1; i++)
                    number_of_threads.notify();
                flag.set(true);
            }
        }
            try
            {
                task.run();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            counter.decrementAndGet();
            all_left.decrementAndGet();
            if( all_left.get() ==0) {

                    synchronized (number_of_threads)
                    {
                        flag.set(false);
                        number_of_threads.notify();
                    }

            }
    }
    private AtomicInteger counter;
    private AtomicInteger all_left;
    private Integer number_of_threads;
    private Runnable task;
    private AtomicBoolean flag;
}
