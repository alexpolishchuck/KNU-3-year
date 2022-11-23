import java.util.ArrayList;
import java.util.Random;

public class Process  {


    public Process(int id, int NumberOfIOJobs, int PredictedIOTime, int maxIOTime)
    {
        if(id <0 || NumberOfIOJobs<0 || PredictedIOTime <0 )
            throw new IllegalArgumentException("Illegal arguments were passed to Process creator");
        this.id = id;
        this.NumberOfIOJobs = NumberOfIOJobs;
        this.PredictedIOTime = PredictedIOTime;
        this.maxIOTime = maxIOTime;
        initTasks();
    }

    public int getNumberOfIOJobs() {
        return NumberOfIOJobs;
    }

    public int getPredictedIOTime() {
        return PredictedIOTime;
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
        int numberOfIO = NumberOfIOJobs;
        tasks = new ArrayList<>();
        while(numberOfIO !=0)
            if( numberOfIO != 0) {
                tasks.add(() -> {
                    int newTime = random.nextInt(maxIOTime) + 1;
                    System.out.println("--------\nProcess " + id + " Is IO blocked\nParameters: PredictedIOTime:" +
                            PredictedIOTime + "; Number of IO jobs: " + NumberOfIOJobs);
                    Thread.sleep(newTime * 1000);
                    calculateNewPredictedIOTime(newTime);
                    NumberOfIOJobs--;
                    JobDone();
                    System.out.println("--------\nProcess " + id + " Is unblocked\nNew parameters: PredictedIOTime:" +
                            PredictedIOTime + "; Number of IO jobs: " + NumberOfIOJobs);
                });
                numberOfIO--;
            }

    }

    private int id;
    private int NumberOfIOJobs;
    private int PredictedIOTime;
    private final int alpha = 2;
    private final int maxIOTime;
    ArrayList<task> tasks;

}
