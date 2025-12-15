import java.util.List;

public class AlgorithmResult {

    private List<String> executionOrder;
    private List<ProcessResult> processResults;
    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public List<String> getExecutionOrder() {
        return executionOrder;
    }

    public List<ProcessResult> getProcessResults() {
        return processResults;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }
}
