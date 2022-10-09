package Lab5;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class parser extends Thread{

    public parser(manager parent){
        this.parent = parent;
    numberOfBs=0;
    numberOfAs=0;
    generateCharacters();
    }

    @Override
    public void run() {
        try {
        while(!this.isInterrupted()){
            parent.resetCounter();

                System.out.println("Waiting on barrier, id: " + Thread.currentThread().getId());

                parent.waitOnBarrier();


            System.out.println("Stopped waiting on barrier, id: " + Thread.currentThread().getId());
            changeRandom();
            if(numberOfAs==numberOfBs){
                System.out.println("Incremented barrier, id: " + Thread.currentThread().getId());
                parent.incrementCounter();
            }
                parent.waitOnBarrier();

        }
        } catch (BrokenBarrierException e) {
            System.out.println("Thread stopped working, id: "+ Thread.currentThread().getId());
        } catch (InterruptedException e) {
            System.out.println("Thread stopped working, id: "+ Thread.currentThread().getId());
        }
    }
    private void generateCharacters(){
        this.characters = new ArrayList<Character>();
        Random r = new Random();
        for(int i=0; i<size; i++)
        {
            char c =(char)(r.nextInt(4) + 'A');
            if(c == 'A')
                numberOfAs++;
            else
            if (c=='B')
                numberOfBs++;
            characters.add(c);
        }
    }
    private void changeRandom(){
        Random r = new Random();
        int i = r.nextInt(size);
        switch (characters.get(i)){
            case 'A':
                numberOfAs--;
                characters.set(i,'C');
                break;
            case 'B':
                numberOfBs--;
                characters.set(i,'D');
                break;
            case 'C':
                numberOfAs++;
                characters.set(i,'A');
                break;
            case 'D':
                numberOfBs++;
                characters.set(i,'B');
                break;
        }
    }
    private manager parent;
    private ArrayList<Character> characters;
    private final int size = 20;
    private int numberOfAs;
    private int numberOfBs;
}


class manager {

    public manager(int N){

        numberOfThreads = N;
        counter=0;
        barrier = new CyclicBarrier(N);
        threads = new ArrayList<parser>();
        for(int i=0;i<N;i++){
            threads.add(new parser(this));
        }


        for (Thread i:threads)
            i.start();


    }

    public synchronized void incrementCounter()
    {
        counter++;
        if(counter >= numberOfThreads-1)
        {
            for(Thread i : threads){
                i.interrupt();
            }
        }
    }
    public synchronized void resetCounter()
    {
        counter=0;
    }
    public void waitOnBarrier() throws BrokenBarrierException, InterruptedException {
        barrier.await();
    }
   private int counter;
   private final int numberOfThreads;
   private CyclicBarrier barrier;
   ArrayList<parser> threads;
}


public class MainB {
    public static void main(String[] args){
        try {
            manager m = new manager(4);
        } catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
