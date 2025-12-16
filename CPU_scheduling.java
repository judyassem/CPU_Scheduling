import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.io.File;

public class CPU_scheduling {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse JSON
            TestCase testCase = objectMapper.readValue(new File("D:\\oneDrive\\OneDrive - Cairo University - Students\\Desktop\\YEAR3\\firstTerm\\OS\\Assigment3\\test_cases\\Other_Schedulers\\test_1.json"), TestCase.class);

            // Extract the input data
            int contextSwitch = testCase.getInput().getContextSwitch();
            int rrQuantum = testCase.getInput().getRrQuantum();
            int agingInterval = testCase.getInput().getAgingInterval();
            Queue<Process> processQueue = new LinkedList<>(testCase.getInput().getProcesses());


            RoundRobin.roundRobin(processQueue, rrQuantum, contextSwitch);
            ShortestJobFirst scheduler = new ShortestJobFirst(contextSwitch);
            
//            AlgorithmResult myResult = new AlgorithmResult();
//            ExpectedOutput expectedOutput = new ExpectedOutput();
//            for (int i = 0; i < 3; i++) {
//                if(i == 0){
//                    if(myResult.getProcessResults() == expectedOutput.getSJF().getProcessResults()
//                            && myResult.getAverageTurnaroundTime() == expectedOutput.getSJF().getAverageTurnaroundTime()
//                            && myResult.getExecutionOrder() == expectedOutput.getSJF().getExecutionOrder()
//                            && myResult.getAverageWaitingTime() == expectedOutput.getSJF().getAverageWaitingTime() {
//                        System.out.println("SJF is Executed Successfully");
//                    }
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
