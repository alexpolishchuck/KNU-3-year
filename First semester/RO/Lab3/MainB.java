package Lab3;
import java.util.ArrayList;
import java.util.concurrent.locks.*;

class barberShop{

    public barberShop()
    {

        isThereClient = false;
        isThereBarber =false;
        counterOfCustomers=0;
        lock = new ReentrantLock();
        barb = new barber(this,lock);
        customers = new ArrayList<customer>();
        for(int i=0; i<50;i++)
            customers.add(new customer(this,lock));
        barb.start();
        for (Thread i:customers)
            i.start();

    }

    public Boolean getIsThereClient()
    {

        return isThereClient;

    }
    public void setIsThereClient(boolean isthere)
    {
        synchronized(isThereClient) {
            isThereClient = isthere;
        }
    }



    private Boolean isThereClient;

    private Integer counterOfCustomers;
    private Boolean isThereBarber;
    private ReentrantLock lock;
    private barber barb;
    public void closeTheShop()
    {
        barb.interrupt();
        for(Thread i:customers)
        {
            i.interrupt();
        }

    }
    ArrayList<customer> customers;

}

class barber extends Thread{
    public barber( barberShop bs, ReentrantLock l)
    {
        parent = bs;
        lock = l;

    }
    @Override
    public void run() {
        while(!this.isInterrupted())
        {
            try{
                synchronized (parent)
                {
                    System.out.println("barber is waiting for a client");
                    if(!parent.getIsThereClient())
                        parent.wait();
                    System.out.println("barber stopped waiting for a client");
                }
                System.out.println("barber started styling hair...");
                Thread.sleep(2000);
                System.out.println("barber finished styling hair!");
                synchronized (parent)
                {

                        parent.notify();

                }
            }catch (InterruptedException e)
            {
                System.out.println("Barber closed the barbershop");
                this.interrupt();
                break;
            }

        }
    }
    private ReentrantLock lock;
    private barberShop parent;


}

class customer extends Thread{
    public customer(barberShop bs, ReentrantLock l)
    {
        parent = bs;
        lock = l;
    }
    @Override
    public void run() {


                lock.lock();
                try {


                parent.setIsThereClient(true);
                synchronized (parent)
                {
                    parent.notify();
                    System.out.println("Client started waiting for a haircut "+ Thread.currentThread().getId());
                    parent.setIsThereClient(false);
                    parent.wait();
                    System.out.println("Client had his/her hair styled! "+ Thread.currentThread().getId());
                }
                }catch (InterruptedException e)
                {
                    System.out.println("CLIENT "+ Thread.currentThread().getId() +" was kicked out of the barbershop");
                    this.interrupt();
                }
                lock.unlock();



    }
    private ReentrantLock lock;
    private barberShop parent;
}

public class MainB {
    public static void main(String []args)
    {

        barberShop bs = new barberShop();
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e)
        {

        }
        bs.closeTheShop();
    }
}
