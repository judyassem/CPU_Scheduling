import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AG_ProcessResults {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("waitingTime")
    private int waitingTime;
    
    @JsonProperty("turnaroundTime")
    private int turnaroundTime;
    
    @JsonProperty("quantumHistory")
    private List<Integer> quantumHistory;

    public AG_ProcessResults(){}
    public AG_ProcessResults(String name, int waitingTime, int turnaroundTime, List<Integer> quantumHistory) {
        this.name = name;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
        this.quantumHistory = quantumHistory;
    }
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    
    public List<Integer> getQuantumHistory() { return quantumHistory; }
    public void setQuantumHistory(List<Integer> quantumHistory) { this.quantumHistory = quantumHistory; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AG_ProcessResults that = (AG_ProcessResults) obj;
        return waitingTime == that.waitingTime &&
                turnaroundTime == that.turnaroundTime &&
                name.equals(that.name) &&
                quantumHistory.equals(that.quantumHistory);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, waitingTime, turnaroundTime, quantumHistory);
    }
}
