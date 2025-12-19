import java.util.*;
public class AG_Scheduling {
    private static void checkAndAddArrivals(int currentTime, ArrayList<AG_Process> AG_Processes, boolean[] isCompleted, Queue<AG_Process> readyQueue, AG_Process currentAG_Process) {
        for (AG_Process other : AG_Processes) {
            int idx = AG_Processes.indexOf(other);
            if (other.getArrival() <= currentTime && 
                !isCompleted[idx] && 
                other != currentAG_Process && 
                !readyQueue.contains(other)) {
                readyQueue.add(other);
            }
        }
    }
    
    static void AgScheduling(ArrayList<AG_Process> AG_Processes) {
        int time = 0;
        int completed = 0;
        int n = AG_Processes.size();
    
        LinkedList<AG_Process> readyQueue = new LinkedList<>();
        boolean[] isCompleted = new boolean[n];
        
        // Initialize quantum history for each AG_Process
        Map<String, List<Integer>> quantumHistory = new HashMap<>();
        for (AG_Process p : AG_Processes) {
            quantumHistory.put(p.getName(), new ArrayList<>());
            quantumHistory.get(p.getName()).add(p.getRemainingQuantum());
        }
        
        // Add initial AG_Process that arrives at time 0
        if (!AG_Processes.isEmpty() && AG_Processes.get(0).getArrival() == 0) {
            readyQueue.add(AG_Processes.get(0));
        }
        
        while (completed < n) {
            // Check for arrivals
            checkAndAddArrivals(time, AG_Processes, isCompleted, readyQueue, null);
            
            if (readyQueue.isEmpty()) {
                time++;
                continue;
            }
            
            // Always use FCFS (first in queue)
            AG_Process p = readyQueue.poll();
            int idx = AG_Processes.indexOf(p);
            int q = p.getRemainingQuantum();
            int usedTime = 0;
            
            System.out.println("\nTime " + time + " -> P" + p.getName() + " starts running (Q = " + q + ")");
            
            /* ================= PHASE 1 : FCFS (25%) ================= */
            int t1 = (int) Math.ceil(0.25 * q);
            int run = Math.min(t1, p.getRemainingBurst());
            boolean AG_ProcessCompleted = false;
            
            // Execute unit-by-unit
            for (int i = 0; i < run; i++) {
                time++;
                p.setRemainingBurst(p.getRemainingBurst() - 1);
                usedTime++;
                
                // Check arrivals each time unit
                checkAndAddArrivals(time, AG_Processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setRemainingQuantum(0);
                    quantumHistory.get(p.getName()).add(0);
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    AG_ProcessCompleted = true;
                    break;
                }
            }
            
            if (AG_ProcessCompleted) continue;
            
            /* ================= PHASE 2 : Priority (25%) ================= */
            // Check for priority preemption at START of Phase 2
            AG_Process highPriority = null;
            for (AG_Process other : AG_Processes) {
                int otherIdx = AG_Processes.indexOf(other);
                if (!isCompleted[otherIdx] && other.getArrival() <= time && 
                    other.getPriority() < p.getPriority() && other != p) {
                    if (highPriority == null || other.getPriority() < highPriority.getPriority()) {
                        highPriority = other;
                    }
                }
            }
            
            if (highPriority != null) {
                // FIXED: Add HALF of remaining quantum (Scenario ii)
                int remaining = q - usedTime;
                int addAmount = (int) Math.ceil(remaining / 2.0); // Ceiling of half
                int newQuantum = p.getRemainingQuantum() + addAmount;
                p.setRemainingQuantum(newQuantum);
                quantumHistory.get(p.getName()).add(newQuantum);
                readyQueue.add(p);
                
                // Move higher priority AG_Process to front
                if (readyQueue.contains(highPriority)) {
                    readyQueue.remove(highPriority);
                }
                readyQueue.addFirst(highPriority);
            
                continue;
            }
            
            int t2 = (int) Math.ceil(0.25 * q);
            run = Math.min(t2, p.getRemainingBurst());
            
            // Execute unit-by-unit
            for (int i = 0; i < run; i++) {
                time++;
                p.setRemainingBurst(p.getRemainingBurst() - 1);
                usedTime++;
                
                // Check arrivals each time unit
                checkAndAddArrivals(time, AG_Processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setRemainingQuantum(0);
                    quantumHistory.get(p.getName()).add(0);
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    AG_ProcessCompleted = true;
                    break;
                }
            }
            
            if (AG_ProcessCompleted) continue;
            
            /* ================= PHASE 3 : SJF (50%) ================= */
            int remaining = q - usedTime;
            boolean preempted = false;
            
            while (remaining > 0 && p.getRemainingBurst() > 0) {
                // Check for SJF preemption at each time unit
                AG_Process shorterJob = null;
                for (AG_Process other : AG_Processes) {
                    int otherIdx = AG_Processes.indexOf(other);
                    if (!isCompleted[otherIdx] && other.getArrival() <= time && 
                        other != p && other.getRemainingBurst() < p.getRemainingBurst()) {
                        if (shorterJob == null || other.getRemainingBurst() < shorterJob.getRemainingBurst()) {
                            shorterJob = other;
                        }
                    }
                }
                
                if (shorterJob != null) {
                    // FIXED: SJF preemption adds full remaining quantum (Scenario iii)
                    int newQuantum = p.getRemainingQuantum() + remaining;
                    p.setRemainingQuantum(newQuantum);
                    quantumHistory.get(p.getName()).add(newQuantum);
                    readyQueue.add(p);
                    
                    // Move shorter job to front
                    if (readyQueue.contains(shorterJob)) {
                        readyQueue.remove(shorterJob);
                    }
                    readyQueue.addFirst(shorterJob);
                    preempted = true;
                    break;
                }
                
                // Execute one time unit
                time++;
                p.setRemainingBurst(p.getRemainingBurst() - 1);
                remaining--;
                usedTime++;
                
                // Check arrivals
                checkAndAddArrivals(time, AG_Processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setRemainingQuantum(0);
                    quantumHistory.get(p.getName()).add(0);
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    AG_ProcessCompleted = true;
                    break;
                }
            }
            
            if (AG_ProcessCompleted) continue;
            
            /* ================= APPLY SCENARIOS ================= */
            if (p.getRemainingBurst() == 0) {
                isCompleted[idx] = true;
                p.setRemainingQuantum(0);
                quantumHistory.get(p.getName()).add(0);
                p.setCompletionTime(time);
                p.setTurnaroundTime(time - p.getArrival());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                completed++;
            }
            // Scenario (i): used full quantum (after ALL phases, not just Phase 2)
            else if (!preempted && usedTime == q) {
                // FIXED: Only add 2 when ALL quantum used exactly
                int newQuantum = p.getRemainingQuantum() + 2;
                p.setRemainingQuantum(newQuantum);
                quantumHistory.get(p.getName()).add(newQuantum);
                readyQueue.add(p);
            }
            // If not preempted and quantum not fully used, add back to queue
            else if (!preempted) {
                readyQueue.add(p);
            }
        }
        
        // Print results
        System.out.println("\n=== Scheduling Results ===");
        System.out.println("AG_Process | Arrival | Burst | Priority | Original Q | Final Q | CT | TAT | WT");
        double totalTAT = 0;
        double totalWT = 0;
        for (AG_Process p : AG_Processes) {
            System.out.printf("P%-6s | %-7d | %-5d | %-8d | %-10d | %-8d | %-2d | %-3d | %-2d\n",
                p.getName(), p.getArrival(), p.getBurst(), p.getPriority(), 
                p.getQuantum(), // Original quantum
                p.getRemainingQuantum(), // Final quantum
                p.getCompletionTime(), p.getTurnaroundTime(), p.getWaitingTime());
            totalTAT += p.getTurnaroundTime();
            totalWT += p.getWaitingTime();
        }
        
        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
        
        // Print quantum history
        System.out.println("\n=== Quantum History ===");
        for (AG_Process p : AG_Processes) {
            List<Integer> history = quantumHistory.get(p.getName());
            System.out.printf("P%s: ", p.getName());
            System.out.print("[");
            for (int i = 0; i < history.size(); i++) {
                System.out.print(history.get(i));
                if (i < history.size() - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    public static void main(String[] args) {
        ArrayList<AG_Process> AG_Processes = new ArrayList<>();
        
        // Example AG_Processes (using name as String)
        AG_Processes.add(new AG_Process("1", 0, 17, 4, 7));
        AG_Processes.add(new AG_Process("2", 2, 6, 7, 9));
        AG_Processes.add(new AG_Process("3", 5, 11, 3, 4));
        AG_Processes.add(new AG_Process("4", 15, 4, 6, 6));

        // AG_Processes.add(new AG_Process("1", 0, 20, 5, 8));
        // AG_Processes.add(new AG_Process("2", 3, 4, 3, 6));
        // AG_Processes.add(new AG_Process("3", 6, 3, 4, 5));
        // AG_Processes.add(new AG_Process("4", 10, 2, 2, 4));
        // AG_Processes.add(new AG_Process("5", 15, 5, 6, 7));
        // AG_Processes.add(new AG_Process("6", 20, 6, 1, 3));
        
        System.out.println("=== AG Scheduling Algorithm ===");
        AgScheduling(AG_Processes);
    }
}