import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpectedOutput {

    @JsonProperty("SJF")
    private ShortestJobFirst SJF;
    @JsonProperty("RR")
    private AlgorithmResult RR;
    @JsonProperty("Priority")
    private Priority Priority;

    public ExpectedOutput() {}

    public ShortestJobFirst getSJF() {
        return SJF;
    }

    public AlgorithmResult getRR() {
        return RR;
    }

    public void setRR(AlgorithmResult RR) {
        this.RR = RR;
    }

    public Priority getPriority() {
        return Priority;
    }
}
