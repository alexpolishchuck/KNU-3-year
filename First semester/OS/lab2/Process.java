import java.util.ArrayList;
import java.util.Random;

public class Process  {


    public Process(int id, int RegularJobsTime, int NumberOfIOJobs, int PredictedIOTime, int maxIOTime)
    {
        if(id <0 || NumberOfIOJobs<0 || PredictedIOTime <0 || RegularJobsTime<0)
            throw new IllegalArgumentException("Illegal arguments were passed to Process creator");
        this.id = id;
        this.NumberOfIOJobs = NumberOfIOJobs;
        this.RegularJobsTime = RegularJobsTime;
        this.PredictedIOTime = PredictedIOTime;
        this.maxIOTime = maxIOTime;
        initTasks();
    }

    public int getId() {
        return id;
    }

    public int getNumberOfIOJobs() {
        return NumberOfIOJobs;
    }

    public int getPredictedIOTime() {
        return PredictedIOTime;
    }

    public int getRegularJobsTime() {
        return RegularJobsTime;
    }
    public void JobDone()
    {
        tasks.remove(0);
    }
    public void calculateNewPredictedIOTime(int time) {
        PredictedIOTime = (alpha - 1) * PredictedIOTime/alpha + time/alpha;
    }

    public task getNextTask() {
        if(tasks.size()>0)
        return tasks.get(0);
        else return null;
    }

    private void initTasks()
    {
        Random random = new Random();
        final int finalNumberOfRegularJobs = random.nextInt(maxNumberOfRegularTasks) + 1;
        int numberOfRegularJobs = finalNumberOfRegularJobs;
        int numberOfIO = NumberOfIOJobs;
        tasks = new ArrayList<>();
        while(numberOfRegularJobs !=0 || numberOfIO !=0)
        {
            int whichNext = random.nextInt(2);
            if(whichNext == 0 && numberOfIO != 0) {
                tasks.add(() -> {
                    int newTime = random.nextInt(maxIOTime) + 1;
                    System.out.println("--------\nProcess " + id + " Is IO blocked\nParameters: PredictedIOTime:" +
                            PredictedIOTime + "; Number of IO jobs: " + NumberOfIOJobs +
                            " RegularJobsTime: " + RegularJobsTime);
                    Thread.sleep(newTime * 1000);
                    calculateNewPredictedIOTime(newTime);
                    NumberOfIOJobs--;
                    JobDone();
                    System.out.println("--------\nProcess " + id + " Is unblocked\nNew parameters: PredictedIOTime:" +
                            PredictedIOTime + "; Number of IO jobs: " + NumberOfIOJobs +
                            " RegularJobsTime: " + RegularJobsTime);
                    return task.typeOfJob.IO;
                });
                numberOfIO--;
            }
            else if(whichNext == 1 && numberOfRegularJobs != 0) {
                tasks.add(() -> {
                    Thread.sleep(RegularJobsTime / finalNumberOfRegularJobs * 1000);
                    JobDone();
                    System.out.println("Process " + id + " finished its regular task");
                    return task.typeOfJob.DEFAULT;
                });
                numberOfRegularJobs--;
            }
        }
    }

    private int id;
    private int RegularJobsTime;
    private int NumberOfIOJobs;
    private int PredictedIOTime;
    private final int alpha = 2;
    private int maxNumberOfRegularTasks = 5;
    private final int maxIOTime;
    ArrayList<task> tasks;

}
