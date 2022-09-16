package lab2;

import java.util.ArrayList;

class mythread extends Thread
{
    public mythread (bagOfTasks p)
    {
        parent = p;
    }

    @Override
    public void run()
    {
        while (!this.isInterrupted())
        {
            this.getTask();
            if(this.currentRow==null)
                continue;
            try {
                searchForBear();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private void searchForBear() throws InterruptedException {
        int size = currentRow.length;
        for(int i=0; i<size; i++)
        {
            if(this.isInterrupted())
                throw new InterruptedException("Thread was interrupted");
            System.out.println("[ x=" + i + " ; y= " + task +" ]");
            if(currentRow[i]!=0)
            {
                System.out.println("THE BEAR IS PUNISHED AT x=" + i + " ; y= " + task);
                this.parent.bearWasFound();
            }
        }
    }
    private void getTask()
    {
        synchronized (parent)
        {

           try {
               task = this.parent.getCurrentTask();
               currentRow = this.parent.getRow();
           }catch (Exception e)
           {
                System.out.println(e.getMessage());
                this.interrupt();
           }


        }
    }

    private bagOfTasks parent;

    private int []currentRow;

    private int task;
}

class bagOfTasks
{
    public bagOfTasks(int[][]arr)
    {
        if(arr == null)
            throw new IllegalArgumentException("Forest can't be null");

        bees = new ArrayList<Thread>();
        for(int i=0; i<3;i++)
        bees.add(new mythread(this));
        this.forest=arr;
        taskCounter = arr.length-1;
        for(int i=0; i<3;i++)
            bees.get(i).start();
    }





    public synchronized void bearWasFound()
    {
        int size = bees.size();
        for (int i=0 ; i<size;i++)
        {
            bees.get(i).interrupt();
        }
    }
    public synchronized int[] getRow()
    {
        if(taskCounter<0)
            throw new ArrayIndexOutOfBoundsException("No more tasks");
        this.taskCounter--;

        return this.forest[this.taskCounter + 1];
    }
    public synchronized int getCurrentTask()
    {

        return this.taskCounter;
    }
    private int taskCounter;
    private ArrayList<Thread> bees;

    private int[][] forest;

}


public class Main
{
    public static void main(String args[])
    {
        final int n = 100;

        int[][] forest = new int[n][n];

        forest[50][52] =1;
        bagOfTasks bag = new bagOfTasks(forest);
    }
}