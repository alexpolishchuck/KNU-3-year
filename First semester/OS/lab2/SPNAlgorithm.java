import java.util.Random;

public class SPNAlgorithm
{

    public void run() throws InterruptedException {
        ProcessManager processManager = new ProcessManager();
        int currentTime =0;

        task.typeOfJob type = null;
        Process process= null;
        while(currentTime!=runTime)
        {
            if(type == null || type == task.typeOfJob.DEFAULT) //if prev task was IO, than we consider it as a separate job
            process = processManager.getProcesses().peek();    //thus look for shortest process after this task has been finished

            if(process == null)
            {
                System.out.println("All processes finished its jobs");
                return;
            }
            processManager.getProcesses().remove(process);
            task currentTask = process.getNextTask();
            if(currentTask != null) {
                type = currentTask.run();
                processManager.getProcesses().add(process);
            } else type = null;

            currentTime++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SPNAlgorithm alg = new SPNAlgorithm();
        alg.run();
    }
    private int runTime = 30;

}
