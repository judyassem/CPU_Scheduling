import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {

    public static void roundRobin(Queue<Process> processQueue, int rrQuantum, int contextSwitch) {
        int time = 0; // Start time
        Queue<Process> readyQueue = new LinkedList<Process>();
        Queue<Process> CPU = new LinkedList<Process>();

        // initial process
        if (!processQueue.isEmpty()) {
            readyQueue.offer(processQueue.poll());
        }

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll();
            CPU.offer(process);

            int burstTime = process.getBurst();
            if (burstTime > rrQuantum) {
                System.out.println("Processing " + process.getName() + " for " + rrQuantum + " units.");
                process.setBurst(burstTime - rrQuantum); // Decrease burst time
                time += rrQuantum;

                // Add any new processes that have arrived during this quantum
                while (!processQueue.isEmpty() && processQueue.peek().getArrival() <= time) {
                    readyQueue.offer(processQueue.poll());
                }

                readyQueue.offer(process); // Re-add process
            } else {
                System.out.println("Processing " + process.getName() + " for " + burstTime + " units.");
                process.setBurst(0);
                time += burstTime;
                System.out.println(process.getName() + " has finished execution.");

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
    }
}