import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.File;
import java.util.Scanner;

public class CPU_scheduling {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Scanner testFile = new Scanner(System.in);
            Scanner choice = new Scanner(System.in);

            System.out.println("Enter the path to the JSON test file: ");
            String filePath = testFile.nextLine();
            TestCase testCase = objectMapper.readValue(new File(filePath), TestCase.class);

            System.out.println("Is this an AG scheduling test? (Y/N)");
            String answer = choice.nextLine();

            if (answer.equalsIgnoreCase("Y")) {
                int contextSwitch = testCase.getInput().getContextSwitch();
                Queue<AG_Process> processQueue = new LinkedList<>(testCase.getInput().getProcesses());

                AG_Scheduler agScheduler = new AG_Scheduler();
                AG_AlgorithmResult actual = agScheduler.agScheduling(processQueue, contextSwitch);
                AG_AlgorithmResult expected = testCase.getExpectedOutput().getAG();

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
                List<AG_ProcessResults> actualPR = actual.getProcessResults();
                List<AG_ProcessResults> expectedPR = expected.getProcessResults();

                if (actualPR.size() != expectedPR.size()) {
                    throw new AssertionError("ProcessResults size mismatch");
                }

                for (int i = 0; i < expectedPR.size(); i++) {
                    AG_ProcessResults a = actualPR.get(i);
                    AG_ProcessResults e = expectedPR.get(i);

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

                System.out.println("AG Scheduling test PASSED");
            }
            else if (answer.equalsIgnoreCase("N")) {
                int contextSwitch = testCase.getInput().getContextSwitch();
                int rrQuantum = testCase.getInput().getRrQuantum();
                int agingInterval = testCase.getInput().getAgingInterval();
                Queue<Process> processQueue = new LinkedList<>(testCase.getInput().getProcesses());

                testRoundRobin(processQueue, rrQuantum, contextSwitch ,testCase);
            }else {
                System.out.println("wrong Input");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testRoundRobin(Queue<Process> processQueue, int rrQuantum, int contextSwitch, TestCase testCase) {
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
            System.out.println( "actual: " + actual.getAverageWaitingTime());
            System.out.println( "expected: " + expected.getAverageWaitingTime());
            throw new AssertionError("Average waiting time mismatch");
        }

        if (Math.abs(actual.getAverageTurnaroundTime()
                - expected.getAverageTurnaroundTime()) > 0.001) {
            throw new AssertionError("Average turnaround time mismatch");
        }

        System.out.println("Round Robin test PASSED");
    }
}
