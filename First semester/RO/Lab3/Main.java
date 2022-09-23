package Lab3;

import java.util.ArrayList;

class mySemaphore
{
    public mySemaphore(Pot parent)
    {
        currentStatus = status.empty;
    this.parent = parent;
    }

    public synchronized void decrement()throws InterruptedException
    {
        while(currentStatus!=status.full)
        {
            this.notifyAll();
            this.wait();
        }
        Integer sips = parent.getSips();
        sips--;
        System.out.println("Took a sip: " + sips);
        this.parent.setSips(sips);
        if (sips ==0) {
            currentStatus = status.empty;
            this.notifyAll();
        }



    }

    public void increment()throws InterruptedException
    {
        Integer sips =-1;
        synchronized (this) {
            while (currentStatus == status.busy || currentStatus == status.full)
                this.wait();
            currentStatus = status.busy;
            sips = parent.getSips();
            sips++;
            System.out.println("Bee with id= " + Thread.currentThread().getId() + " added a sip: " + sips);
            if (sips == parent.getMax())
                currentStatus = status.full;
            else
            currentStatus = status.empty;
            this.parent.setSips(sips);
            this.notify();

        }

    }

    public enum status{busy,full,empty}

    private status currentStatus;
    private Pot parent;

}
class Pot
{
    public Pot(int max)
    {
        if (max<=0)
            throw new IllegalArgumentException("Maximum number of sips must be more than 0");

        sem = new mySemaphore(this);
        this.max = max;
        this.sips = 0;

    }
     public void takeSip() throws InterruptedException
     {
        sem.decrement();

     }

    public void addSip() throws InterruptedException
    {
        sem.increment();

    }

    public int getMax()
    {
        return this.max;

    }
    public Integer getSips()
    {
        return this.sips;

    }
    public void setSips(Integer sips)
    {
        this.sips = sips;

    }
    private int max;

    private Integer sips;
    private mySemaphore sem;

}


public class Main {
   public static void main(String[]args)
    {
        Pot pot = new Pot(100);
        ArrayList<Thread> bees = new ArrayList<Thread>();
        for (int i=0; i<3; i++)
        {
            bees.add(new Thread(()->{while(true)
            {
                try {
                    pot.addSip();
                }catch (InterruptedException e){
                    System.out.println("Bee stopped working");
                    break;
                }
            }
            }));
        }

        Thread bear = new Thread(()->{
            while(true)
            {
                try {
                    pot.takeSip();
                }catch (InterruptedException e){
                    System.out.println("Bear stopped working");
                    break;
                }
            }
        });

        for(Thread i:bees)
            i.start();
        bear.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bear.interrupt();
        for(Thread i:bees)
            i.interrupt();
    }

}
