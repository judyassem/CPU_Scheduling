import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessResult {

    private String name;
    private int waitingTime;
    private int turnaroundTime;

    public ProcessResult() {}

    @JsonCreator
    public ProcessResult(
            @JsonProperty("name") String name,
            @JsonProperty("waitingTime") int waitingTime,
            @JsonProperty("turnaroundTime") int turnaroundTime) {
        this.name = name;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }

    public String getName() {
        return name;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProcessResult that = (ProcessResult) obj;
        return waitingTime == that.waitingTime &&
                turnaroundTime == that.turnaroundTime &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, waitingTime, turnaroundTime);
    }
}
