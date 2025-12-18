import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.File;

public class CPU_scheduling {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse JSON
            TestCase testCase = objectMapper.readValue(new File("D:\\oneDrive\\OneDrive - Cairo University - Students\\Desktop\\YEAR3\\firstTerm\\OS\\Assigment3\\test_cases\\Other_Schedulers\\test_6.json"), TestCase.class);

            // Extract the input data
            int contextSwitch = testCase.getInput().getContextSwitch();
            int rrQuantum = testCase.getInput().getRrQuantum();
            int agingInterval = testCase.getInput().getAgingInterval();
            Queue<Process> processQueue = new LinkedList<>(testCase.getInput().getProcesses());

                // ===== Run Round Robin =====
                RoundRobin rr = new RoundRobin();
                AlgorithmResult actual = rr.roundRobin(processQueue, rrQuantum, contextSwitch);

                AlgorithmResult expected = testCase.getExpectedOutput().getRR();

                // ===== ASSERT EXECUTION ORDER =====
                if (!actual.getExecutionOrder().equals(expected.getExecutionOrder())) {
                    throw new AssertionError(
                            "Execution order mismatch\nExpected: "
                                    + expected.getExecutionOrder()
                                    + "\nActual: "
                                    + actual.getExecutionOrder()
                    );
                }

                // ===== ASSERT PROCESS RESULTS =====
                List<ProcessResult> actualPR = actual.getProcessResults();
                List<ProcessResult> expectedPR = expected.getProcessResults();

                if (actualPR.size() != expectedPR.size()) {
                    throw new AssertionError("ProcessResults size mismatch");
                }

                for (int i = 0; i < expectedPR.size(); i++) {
                    ProcessResult a = actualPR.get(i);
                    ProcessResult e = expectedPR.get(i);

                    if (!a.getName().equals(e.getName())) {
                        throw new AssertionError("Process name mismatch at index " + i);
                    }
                    if (a.getWaitingTime() != e.getWaitingTime()) {
                        throw new AssertionError(
                                a.getName() + " WT mismatch. Expected "
                                        + e.getWaitingTime() + ", got " + a.getWaitingTime()
                        );
                    }
                    if (a.getTurnaroundTime() != e.getTurnaroundTime()) {
                        throw new AssertionError(
                                a.getName() + " TAT mismatch. Expected "
                                        + e.getTurnaroundTime() + ", got " + a.getTurnaroundTime()
                        );
                    }
                }

                // ===== ASSERT AVERAGES =====
                if (Math.abs(actual.getAverageWaitingTime()
                        - expected.getAverageWaitingTime()) > 0.001) {
                    throw new AssertionError("Average waiting time mismatch");
                }

                if (Math.abs(actual.getAverageTurnaroundTime()
                        - expected.getAverageTurnaroundTime()) > 0.001) {
                    throw new AssertionError("Average turnaround time mismatch");
                }

                System.out.println("Round Robin test PASSED");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
