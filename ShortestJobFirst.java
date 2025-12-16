public class ShortestJobFirst {

    ArrayList<Process> processes = new ArrayList<>();
    int contextSwitch;

    public ShortestJobFirst(int contextSwitch) {
        this.contextSwitch = contextSwitch;
    }

    void sjfScheduling() {
        int time = 0;
        int completed = 0;
        int n = processes.size();
        int lastProcess = -1;
        ArrayList<Integer> executionOrder = new ArrayList<>();

        while (completed < n) {
            int idx = -1;
            int minRemaining = Integer.MAX_VALUE;

            // Find process with minimum remaining time at current time
            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrivalTime <= time && !p.isCompleted &&
                        p.remainingTime < minRemaining) {
                    minRemaining = p.remainingTime;
                    idx = i;
                }
            }

            if (idx != -1) {
                Process p = processes.get(idx);

                // context switching
                if (lastProcess != -1 && lastProcess != p.processId) {
                    time += contextSwitch;
                }

                p.remainingTime--;
                executionOrder.add(p.processId);
                time++;
                lastProcess = p.processId;

                if (p.remainingTime == 0) {
                    p.isCompleted = true;
                    completed++;

                    p.completionTime = time;
                    p.turnaroundTime = p.completionTime - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.burstTime;
                }

            } else {
                time++; 
            }
        }

        
        System.out.println("Execution Order:");
        for (int pid : executionOrder) {
            System.out.print("P" + pid + " ");
        }
        System.out.println("\n\nPID\tBT\tAT\tWT\tTAT");
        for (Process p : processes) {
            System.out.println("P" + p.processId + "\t" + p.burstTime+"\t"+ p.arrivalTime+"\t" + p.waitingTime + "\t" + p.turnaroundTime);
        }

        double totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }
        System.out.println("\nAverage Waiting Time: " + (totalWT / n));
        System.out.println("Average Turnaround Time: " + (totalTAT / n));
     private AlgorithmResult sjf;
}
