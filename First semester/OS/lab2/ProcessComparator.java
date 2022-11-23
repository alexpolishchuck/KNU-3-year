import java.util.Comparator;

public class ProcessComparator implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {
        int p1Time = p1.getNumberOfIOJobs() * p1.getPredictedIOTime() + p1.getRegularJobsTime();
        int p2Time = p2.getNumberOfIOJobs() * p2.getPredictedIOTime() + p2.getRegularJobsTime();

        if(p1Time > p2Time)
            return 1;
        if (p1Time < p2Time)
            return -1;

        return 0;
    }
}
