public class Process {
    private String name;
    private int arrival;
    private int burst;
    private int priority;

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