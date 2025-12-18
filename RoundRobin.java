import java.util.*;

public class RoundRobin {
    int time = 0; // Start time
    Queue<Process> readyQueue = new LinkedList<Process>();
    Queue<Process> CPU = new LinkedList<Process>();

    AlgorithmResult myResult = new AlgorithmResult();
    public AlgorithmResult roundRobin(Queue<Process> processQueue, int rrQuantum, int contextSwitch) {

        Map<String, Integer> originalBurst = new HashMap<>();
        Map<String, Integer> arrivalTime = new HashMap<>();
        Map<String, Integer> completionTime = new HashMap<>();
        List<ProcessResult> myProcessesResult = new ArrayList<>();

        // Save original data before bursts are modified
        for (Process p : processQueue) {
            originalBurst.put(p.getName(), p.getBurst());
            arrivalTime.put(p.getName(), p.getArrival());
        }

        // initial process
        if (!processQueue.isEmpty()) {
            readyQueue.offer(processQueue.poll());
        }

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll();
            CPU.offer(process);
            int burstTime = process.getBurst();

            if (burstTime > rrQuantum) {
//                System.out.println("Processing " + process.getName() + " for " + rrQuantum + " units.");
                process.setBurst(burstTime - rrQuantum); // Decrease burst time
                time += rrQuantum;

                // Add any new processes that have arrived during this quantum
                while (!processQueue.isEmpty() && processQueue.peek().getArrival() <= time) {
                    readyQueue.offer(processQueue.poll());
                }

                readyQueue.offer(process); // Re-add process
            } else {
//                System.out.println("Processing " + process.getName() + " for " + burstTime + " units.");
                process.setBurst(0);
                time += burstTime;
                completionTime.put(process.getName(), time);
                // Add any new processes that have arrived during this burst
                while (!processQueue.isEmpty() && processQueue.peek().getArrival() <= time) {
                    readyQueue.offer(processQueue.poll());
                }
            }

            time += contextSwitch;

            // Add any processes that arrived during context switch
            while (!processQueue.isEmpty() && processQueue.peek().getArrival() <= time) {
                readyQueue.offer(processQueue.poll());
            }
        }

        Map<String, Integer> waitingTime = new HashMap<>();
        Map<String, Integer> turnaroundTime = new HashMap<>();

        double totalWT = 0;
        double totalTAT = 0;

        for (String name : completionTime.keySet()) {
            int tat = completionTime.get(name) - arrivalTime.get(name);
            int wt = tat - originalBurst.get(name);

            turnaroundTime.put(name, tat);
            waitingTime.put(name, wt);

            totalWT += wt;
            totalTAT += tat;

            ProcessResult pr = new ProcessResult(name, wt, tat);
            myProcessesResult.add(pr);
        }

        myResult.setProcessResults(myProcessesResult);
        myResult.setAverageWaitingTime(totalWT / completionTime.size());
        myResult.setAverageTurnaroundTime(totalTAT / completionTime.size());
        LinkedList<String> order = new LinkedList<String>();
        for (Process process : CPU) {
            order.add(process.getName());
        }
        myResult.setExecutionOrder(order);
    return myResult;
    }
}