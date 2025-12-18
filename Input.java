import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Input {
    @JsonProperty("contextSwitch")
    private int contextSwitch;

    @JsonProperty("rrQuantum")
    private int rrQuantum;

    @JsonProperty("agingInterval")
    private int agingInterval;

    @JsonProperty("processes")
    private List<Process> processes;

    public int getContextSwitch() {
        return contextSwitch;
    }

    public void setContextSwitch(int contextSwitch) {
        this.contextSwitch = contextSwitch;
    }

    public int getRrQuantum() {
        return rrQuantum;
    }

    public void setRrQuantum(int rrQuantum) {
        this.rrQuantum = rrQuantum;
    }

    public int getAgingInterval() {
        return agingInterval;
    }

    public void setAgingInterval(int agingInterval) {
        this.agingInterval = agingInterval;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }
}