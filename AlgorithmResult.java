import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgorithmResult {

    @JsonProperty("executionOrder")
    private List<String> executionOrder;
     @JsonProperty("processResults")
    private List<ProcessResult> processResults;
     @JsonProperty("averageWaitingTime")
    private double averageWaitingTime;
     @JsonProperty("averageTurnaroundTime")
    private double averageTurnaroundTime;

     public AlgorithmResult() {}

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

    public void setExecutionOrder(List<String> executionOrder) {
        this.executionOrder = executionOrder;
    }

    public void setProcessResults(List<ProcessResult> processResults) {
        this.processResults = processResults;
    }

    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public void setAverageTurnaroundTime(double averageTurnaroundTime) {
        this.averageTurnaroundTime = averageTurnaroundTime;
    }
}
