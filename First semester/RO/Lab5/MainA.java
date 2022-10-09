package Lab5;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

enum states{LEFT(0),RIGHT(1);

    private final int value;
    private states(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
};
class runnableTask implements Runnable{

    public runnableTask(ArrayList<states>newbies, int i, int j){
        if(i>=j || i<0 || j>= newbies.size())
            throw new IllegalArgumentException("Wrong arguments in runnableTask constr");
        this.soldiers = newbies;
        strt= i;
        end = j;


    }
    @Override
    public void run() {
        for(int i =strt; i<=end;i++){
            if(i+1 <=end)
            {
                if(soldiers.get(i) == states.RIGHT && soldiers.get(i+1)==states.LEFT)
                {
                    soldiers.set(i, states.LEFT);
                    soldiers.set(i+1,states.RIGHT);
                }
            }
        }
    }

    private ArrayList<states> soldiers;
    private int strt;
    private int end;
}


class Mybarrier{

     public Mybarrier(int N, ArrayList<states>newbies){

         maxNumber = N;

         counter = new AtomicInteger(0);

         soldiers = newbies;

         isRunning = new AtomicBoolean(false);

         currentIndex = new AtomicInteger(0);

         lock = new ReentrantLock();

         cond = lock.newCondition();

     }

    public void await(){

            lock.lock();

            while(isRunning.get())
            {
                try {
                    System.out.println("Thread is waiting-1, id: " + Thread.currentThread().getId());
                    cond.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(counter.incrementAndGet() < maxNumber )
            {
                try {
                    System.out.println("Thread is waiting-2, id: " + Thread.currentThread().getId());
                    cond.signal();
                    cond.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        System.out.println("* Thread STOPPED waiting, id: " + Thread.currentThread().getId());
            if(counter.get()==maxNumber && !isRunning.get())
            {
                isRunning.set(true);
                currentTask = new AtomicInteger(maxNumber);
                currentIndex.set(0);
            }
           if(currentIndex.incrementAndGet()<maxNumber){
               cond.signalAll();
           }

           lock.unlock();

           runnableTask task;
           synchronized (soldiers){
               task = new runnableTask(soldiers,soldiers.size()/maxNumber * (currentTask.get()-1),
                       soldiers.size()/maxNumber * currentTask.get()-1);
               currentTask.decrementAndGet();
           }
        System.out.println("** Thread is running task, id: " + Thread.currentThread().getId());
           task.run();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("*** Thread finished running task, id: " + Thread.currentThread().getId());
            if(currentIndex.decrementAndGet() ==0)
            {
                lock.lock();
                for (states i:soldiers){
                    System.out.print(i.toString() + " ");
                }
                isRunning.set(false);
                counter.set(counter.get()-maxNumber);
                cond.signal();
                lock.unlock();
            }


    }


    private AtomicInteger counter;
     private int maxNumber;
     private AtomicBoolean isRunning;
     private ArrayList<states> soldiers;
     private AtomicInteger currentIndex;
    private AtomicInteger currentTask;
    private ReentrantLock lock;
    private Condition cond;
}

class myThread extends Thread{
    public myThread(Mybarrier barrier)
    {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()){
            barrier.await();
        }
    }

    private Mybarrier barrier;
}
public class MainA {

   public static void main(String[]args)
    {
        Random rand = new Random();
        int size = 150;
        ArrayList<states> soldiers = new ArrayList<states>();
        for(int i=0; i<size; i++)
        {
            soldiers.add(states.values()[rand.nextInt(2)]);
        }
        Mybarrier barrier = new Mybarrier(3,soldiers);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i=0; i<5;i++)
        {
            threads.add(new myThread(barrier));
            threads.get(i).start();
        }
    }

}
