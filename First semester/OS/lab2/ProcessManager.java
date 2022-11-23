import java.security.PublicKey;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class ProcessManager {

    public ProcessManager()
    {
        comparator = new ProcessComparator();
        processes =new PriorityBlockingQueue(numberOfProcesses,comparator);
        addProcesses(2);
       processAddition = new Thread(()->{       //imitation of arriving tasks
            try {

                for (int i=0; i<numberOfProcesses - 2; i++)
                {
                    Thread.sleep(2000);
                    System.out.println("Process has been added: id: " + nextId);
                    addProcesses(1);

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        processAddition.start();
    }

    public PriorityBlockingQueue<Process> getProcesses() {
        return processes;
    }

    private void addProcesses(final int n)
    {
        Random random = new Random();
        for(int i=0;i<n;i++)
        {
            processes.add(new Process(nextId,
                    random.nextInt(maxNumberOfIO) + 1,
                    random.nextInt(maxIOJobTime) + 1,
                            maxIOJobTime)
                    );
            nextId++;
        }
    }
    private final int numberOfProcesses = 4;

    private final int maxIOJobTime = 10;
    private final int maxNumberOfIO = 5;
    private  PriorityBlockingQueue<Process> processes;
    private ProcessComparator comparator;
    private Thread processAddition;
    private int nextId = 0;
}
