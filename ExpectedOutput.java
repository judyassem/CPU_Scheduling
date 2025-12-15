import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpectedOutput {

    private ShortestJobFirst SJF;
    private RoundRobin RR;
    private Priority Priority;

    public ShortestJobFirst getSJF() {
        return SJF;
    }

    public RoundRobin getRR() {
        return RR;
    }

    public Priority getPriority() {
        return Priority;
    }
}
