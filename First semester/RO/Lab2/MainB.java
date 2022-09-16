package lab2;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

class Producer extends Thread
{
    public Producer(LinkedBlockingQueue<Integer> from,LinkedBlockingQueue<Integer> to)
    {
        if(from==null || to==null)
            throw  new IllegalArgumentException("Invalid input");
        listFrom = from;
        listTo = to;

    }

    @Override
    public void run() {
        try{
            doTheJob();
        }catch (RuntimeException e)
        {
           // System.out.println(e.getMessage());
        }

    }
    public synchronized void addTask(int value)
    {
        if(value<0)
            return;
        listTo.add(value);
    }
    public synchronized int getTask()
    {
        int value = listFrom.peek();
        listFrom.remove();
        return value;
    }
    protected void doTheJob()
    {
        while (!this.isInterrupted())
        {
            synchronized (listTo)
            {
                while(!listTo.isEmpty())
                    try {
                        if(listFrom.isEmpty())
                        {
                            this.interrupt();
                            throw new InterruptedException("Producer finished it's job");
                        }
                        listTo.wait();

                    } catch (InterruptedException e)
                    {
                        System.out.println(e.getMessage());
                        throw new RuntimeException();
                    }


                int value = listFrom.peek();
                listFrom.remove();
                System.out.println("Producer: " + value);
                listTo.add(value);


                listTo.notify();
            }
        }
    }
 protected LinkedBlockingQueue<Integer> listFrom;
    protected LinkedBlockingQueue<Integer> listTo;

}

class intermediary extends Producer
{
    public intermediary(LinkedBlockingQueue<Integer> from,LinkedBlockingQueue<Integer> to, Thread p)
    {
        super(from,to);
        if(from==null || to==null || p==null)
            throw  new IllegalArgumentException("Invalid input");

        parent = p;
    }

    @Override
    protected void doTheJob()
    {
        while (!this.isInterrupted())
        {
            int value = -1;
            synchronized (listFrom) {

                while (listFrom.isEmpty())
                    try {
                        if (parent.isInterrupted())
                        {
                            this.interrupt();
                            throw new InterruptedException("intermediary finished it's job");
                        }
                        listFrom.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException();
                    }

                value = listFrom.peek();
                listFrom.remove();
                listFrom.notify();
            }
                synchronized (listTo)
                  {
                             while(!listTo.isEmpty()) {
                               try {
                                    listTo.wait();
                                } catch (InterruptedException e) {

                                    System.out.println(e.getMessage());
                                   throw new RuntimeException();
                                }
                            }
            System.out.println("intermediary: " + value);
            listTo.add(value);
                      listTo.notify();
                      }



        }
    }
    private Thread parent;
}

class accountant extends Thread
{
    public accountant(LinkedBlockingQueue<Integer> from, Thread p)
    {
        this.listFrom=from;
        res=0;
        parent = p;
    }
    @Override
    public void run() {
        try {

            while (!this.isInterrupted()) {
                synchronized (listFrom) {
                    while (listFrom.isEmpty()) {
                        if(parent.isInterrupted())
                        {
                            this.interrupt();
                            throw new InterruptedException("accountant finished it's job");
                        }
                        listFrom.wait();
                    }

                    int value = this.listFrom.peek();
                    System.out.println("ACCOUNTANT: " + value);
                    this.listFrom.remove();
                    res += value;

                    listFrom.notify();
                }
            }
            } catch(InterruptedException e){
                System.out.println("FINAL RESULT: " + this.res);
            }


    }
    private LinkedBlockingQueue<Integer> listFrom;
   private int res;
   Thread parent;
}

public class MainB {

    public static void main(String[] args)
    {

        LinkedBlockingQueue<Integer> base = new LinkedBlockingQueue<Integer>();
        for(int i=1; i<=10; i++)
        {
            base.add(i);
        }
        LinkedBlockingQueue<Integer> heap = new LinkedBlockingQueue<Integer>();
        LinkedBlockingQueue<Integer> truck = new LinkedBlockingQueue<Integer>();
        Producer pr = new Producer(base,heap);
        intermediary interm = new intermediary(heap,truck,pr);
        accountant acc = new accountant(truck,interm);

        pr.start();
        interm.start();
        acc.start();



    }



}
