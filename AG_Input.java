import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AG_Input {

    @JsonProperty("processes")
    private List<AG_Process> processes;

    public List<AG_Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<AG_Process> processes) {
        this.processes = processes;
    }

}
