import java.util.Random;

public class SPNAlgorithm
{

    public void run() throws InterruptedException {
        ProcessManager processManager = new ProcessManager();
        int currentTime =0;

        Process process= null;
        while(currentTime!=runTime)
        {
                                                                //we consider each command execution as a separate job
            process = processManager.getProcesses().peek();    //thus look for the shortest process after each task has been finished
            if(process == null)
            {
                System.out.println("All processes finished its jobs");
                return;
            }
            processManager.getProcesses().remove(process);
            task currentTask = process.getNextTask();
            if(currentTask != null) {
                currentTask.run();
                processManager.getProcesses().add(process);
            }
            currentTime++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SPNAlgorithm alg = new SPNAlgorithm();
        alg.run();
    }
    private int runTime = 30;

}
