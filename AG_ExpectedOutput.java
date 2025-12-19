import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AG_ExpectedOutput {
    @JsonProperty("processes")
    private List<AG_ProcessResults> processes;

    public AG_ExpectedOutput() {}

    public AG_ExpectedOutput(List<AG_ProcessResults> processes) {
        this.processes = processes;
    }

    // Getters and setters
    public List<AG_ProcessResults> getProcesses() {
        return processes;
    }

    public void setProcesses(List<AG_ProcessResults> processes) {
        this.processes = processes;
    }
}
