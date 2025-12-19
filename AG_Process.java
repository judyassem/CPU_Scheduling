import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AG_Process {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("arrival")
    private int arrival;
    
    @JsonProperty("burst")
    private int burst;
    
    @JsonProperty("priority")
    private int priority;
    
    @JsonProperty("quantum")
    private int quantum;
    
    // Runtime fields (not in JSON)
    @JsonIgnore
    private int completionTime;
    
    @JsonIgnore
    private int turnaroundTime;
    
    @JsonIgnore
    private int remainingBurst;
    
    @JsonIgnore
    private int remainingQuantum;
    
    @JsonIgnore
    private int waitingTime;
    
    @JsonIgnore
    private List<Integer> quantumHistory;

    public AG_Process() {
        this.quantumHistory = new ArrayList<>();
    }

    public AG_Process(String name, int arrival, int burst, int priority, int quantum) {
        this.name = name;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
        this.quantum = quantum;
        this.remainingBurst = burst;
        this.remainingQuantum = quantum;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.quantumHistory = new ArrayList<>();
        this.quantumHistory.add(quantum); // Initial quantum
    }

    // Initialize after JSON deserialization
    public void initializeForScheduling() {
        this.remainingBurst = this.burst;
        this.remainingQuantum = this.quantum;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.quantumHistory = new ArrayList<>();
        this.quantumHistory.add(this.quantum);
    }

    // Getters and setters
    public String getName() { 
        // Remove "P" prefix if present
        if (name != null && name.startsWith("P")) {
            return name.substring(1);
        }
        return name; 
    }
    
    public void setName(String name) { this.name = name; }
    
    public int getArrival() { return arrival; }
    public void setArrival(int arrival) { this.arrival = arrival; }
    
    public int getBurst() { return burst; }
    public void setBurst(int burst) { this.burst = burst; }
    
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    
    public int getQuantum() { return quantum; }
    public void setQuantum(int quantum) { this.quantum = quantum; }
    
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }
    
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    
    public int getRemainingBurst() { return remainingBurst; }
    public void setRemainingBurst(int remainingBurst) { this.remainingBurst = remainingBurst; }
    
    public int getRemainingQuantum() { return remainingQuantum; }
    public void setRemainingQuantum(int remainingQuantum) { this.remainingQuantum = remainingQuantum; }
    
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    
    public List<Integer> getQuantumHistory() { return quantumHistory; }
    public void setQuantumHistory(List<Integer> quantumHistory) { this.quantumHistory = quantumHistory; }
    
    public void addToQuantumHistory(int quantum) { 
        if (quantumHistory == null) {
            quantumHistory = new ArrayList<>();
        }
        quantumHistory.add(quantum); 
    }
    
    public void clearQuantumHistory() {
        if (quantumHistory != null) {
            quantumHistory.clear();
        }
    }
}