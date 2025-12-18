import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Process {
    private String name;
    private int arrival;
    private int burst;
    private int priority;

    public Process() {
    }

    @JsonCreator
    public Process(
            @JsonProperty("name") String name,
            @JsonProperty("arrival") int arrival,
            @JsonProperty("burst") int burst,
            @JsonProperty("priority") int priority) {
        this.name = name;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
    }

    // getters and setters
    public String getName() {
        return name;
    }
    public int getArrival() {
        return arrival;
    }
    public int getBurst() {
        return burst;
    }
    public int getPriority() {
        return priority;
    }
    public void setBurst(int burst) {
        this.burst = burst;
    }
}