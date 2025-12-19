import java.util.*;

public class AG_Scheduling {
    private static void checkAndAddArrivals(int currentTime, ArrayList<Process> processes, boolean[] isCompleted, Queue<Process> readyQueue, Process currentProcess) {
        for (Process other : processes) {
            int idx = processes.indexOf(other);
            if (other.getArrival() <= currentTime && 
                !isCompleted[idx] && 
                other != currentProcess && 
                !readyQueue.contains(other)) {
                readyQueue.add(other);
            }
        }
    }
    
    // Change return type to List<String> to return execution order
    static List<String> AgScheduling(ArrayList<Process> processes) {
        int time = 0;
        int completed = 0;
        int n = processes.size();
    
        LinkedList<Process> readyQueue = new LinkedList<>();
        boolean[] isCompleted = new boolean[n];
        List<String> executionOrder = new ArrayList<>();
        
        // Add initial process that arrives at time 0
        if (!processes.isEmpty() && processes.get(0).getArrival() == 0) {
            readyQueue.add(processes.get(0));
        }
        
        while (completed < n) {
            // Check for arrivals
            checkAndAddArrivals(time, processes, isCompleted, readyQueue, null);
            
            if (readyQueue.isEmpty()) {
                time++;
                continue;
            }
            
            // Always use FCFS (first in queue)
            Process p = readyQueue.poll();
            int idx = processes.indexOf(p);
            int q = p.getQuantum();
            int usedTime = 0;
            
            // Add to execution order
            executionOrder.add("P" + p.getName());
            
            // Track initial quantum if not already tracked
            if (p.getQuantumHistory().isEmpty()) {
                p.addToQuantumHistory(q);
            }
            
            /* ================= PHASE 1 : FCFS (25%) ================= */
            int t1 = (int) Math.ceil(0.25 * q);
            int run = Math.min(t1, p.getRemainingBurst());
            boolean processCompleted = false;
            
            // Execute unit-by-unit
            for (int i = 0; i < run; i++) {
                time++;
                p.setRemainingBurst(p.getRemainingBurst() - 1);
                usedTime++;
                
                // Check arrivals each time unit
                checkAndAddArrivals(time, processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setQuantum(0);
                    p.addToQuantumHistory(0); // Add 0 at completion
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    processCompleted = true;
                    break;
                }
            }
            
            if (processCompleted) continue;
            
            /* ================= PHASE 2 : Priority (25%) ================= */
            // Check for priority preemption at START of Phase 2
            Process highPriority = null;
            for (Process other : processes) {
                int otherIdx = processes.indexOf(other);
                if (!isCompleted[otherIdx] && other.getArrival() <= time && 
                    other.getPriority() < p.getPriority() && other != p) {
                    if (highPriority == null || other.getPriority() < highPriority.getPriority()) {
                        highPriority = other;
                    }
                }
            }
            
            if (highPriority != null) {
                // Preempt with full remaining quantum
                int newQuantum = p.getQuantum() + (q - usedTime);
                p.setQuantum(newQuantum);
                p.addToQuantumHistory(newQuantum);
                readyQueue.add(p);
                
                // Move higher priority process to front
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
                checkAndAddArrivals(time, processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setQuantum(0);
                    p.addToQuantumHistory(0);
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    processCompleted = true;
                    break;
                }
            }
            
            if (processCompleted) continue;
            
            // Check if quantum exhausted after Phase 2
            if (usedTime >= q) {
                int newQuantum = p.getQuantum() + 2;
                p.setQuantum(newQuantum);
                p.addToQuantumHistory(newQuantum);
                readyQueue.add(p);
                continue;
            }
            
            /* ================= PHASE 3 : SJF (50%) ================= */
            int remaining = q - usedTime;
            boolean preempted = false;
            
            while (remaining > 0 && p.getRemainingBurst() > 0) {
                // Check for SJF preemption at each time unit
                Process shorterJob = null;
                for (Process other : processes) {
                    int otherIdx = processes.indexOf(other);
                    if (!isCompleted[otherIdx] && other.getArrival() <= time && 
                        other != p && other.getRemainingBurst() < p.getRemainingBurst()) {
                        if (shorterJob == null || other.getRemainingBurst() < shorterJob.getRemainingBurst()) {
                            shorterJob = other;
                        }
                    }
                }
                
                if (shorterJob != null) {
                    // Preempt with full remaining quantum
                    int newQuantum = p.getQuantum() + remaining;
                    p.setQuantum(newQuantum);
                    p.addToQuantumHistory(newQuantum);
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
                checkAndAddArrivals(time, processes, isCompleted, readyQueue, p);
                
                // Check completion
                if (p.getRemainingBurst() == 0) {
                    isCompleted[idx] = true;
                    p.setQuantum(0);
                    p.addToQuantumHistory(0);
                    p.setCompletionTime(time);
                    p.setTurnaroundTime(time - p.getArrival());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                    completed++;
                    processCompleted = true;
                    break;
                }
            }
            
            if (processCompleted) continue;
            
            /* ================= APPLY SCENARIOS ================= */
            if (p.getRemainingBurst() == 0) {
                isCompleted[idx] = true;
                p.setQuantum(0);
                p.addToQuantumHistory(0);
                p.setCompletionTime(time);
                p.setTurnaroundTime(time - p.getArrival());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurst());
                completed++;
            }
            // Scenario (i): used full quantum
            else if (!preempted && usedTime >= q) {
                int newQuantum = p.getQuantum() + 2;
                p.setQuantum(newQuantum);
                p.addToQuantumHistory(newQuantum);
                readyQueue.add(p);
            }
            // If preempted, quantum already updated above
            else if (!preempted) {
                // Process still has burst but quantum not exhausted
                readyQueue.add(p);
            }
        }
        
        return executionOrder;
    }
}